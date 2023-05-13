package com.projectronin.aidbox.client

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.api.Constants
import ca.uhn.fhir.rest.api.EncodingEnum
import ca.uhn.fhir.rest.api.RequestTypeEnum
import ca.uhn.fhir.rest.client.apache.ApacheRestfulClientFactory
import ca.uhn.fhir.rest.client.api.IClientInterceptor
import ca.uhn.fhir.rest.client.api.IGenericClient
import ca.uhn.fhir.rest.client.api.IHttpRequest
import ca.uhn.fhir.rest.client.api.IHttpResponse
import ca.uhn.fhir.rest.gclient.ICriterion
import ca.uhn.fhir.rest.gclient.UriClientParam
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.apache.commons.io.IOUtils
import org.hl7.fhir.instance.model.api.IBaseBundle
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.DomainResource
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties


class AidboxFhirClient(hostUrl: String, clientId: String, clientSecret: String) {

    @PublishedApi
    internal val client: IGenericClient

    private companion object {
        // inside IRestfulClientFactory the default timeouts are all 10s  (10000)
        //   we want to allow 'a little' more time to avoid some timeouts
        private const val DEFAULT_TIMEOUT = 30000
    }

    init {
        // don't have caller worry about 'fhir' or ending w/ slash.
        val baseUrl = hostUrl.replace("/fhir", "").trim().trimEnd('/')
        val r4FhirContext: FhirContext = FhirContext.forR4()
        r4FhirContext.restfulClientFactory = ApacheRestfulClientFactory(r4FhirContext).apply {
            connectTimeout = DEFAULT_TIMEOUT
            socketTimeout= DEFAULT_TIMEOUT
            connectionRequestTimeout = DEFAULT_TIMEOUT
        }
        //client = FhirContext.forR4().newRestfulGenericClient("$baseUrl/fhir")
        client = r4FhirContext.newRestfulGenericClient("$baseUrl/fhir")
        client.registerInterceptor(CustomBearerTokenAuthInterceptor(baseUrl, clientId, clientSecret))
    }

    inline fun <reified T: DomainResource> getResource(id: String) : T {
        return client.read()
            .resource(T::class.java)
            .withId(id)
            .execute()
    }

    /**
     * Perform query for resources
     *   Will only perform a single request, and the number of results returned is limited
     *   by the request 'count' value.
     */
    inline fun <reified T: DomainResource> queryResources(request: AidboxFhirSearchRequest) : List<T> {
        val criterionList = generateCriterionList(request)
        val bundle = client
            .search<IBaseBundle>()
            .forResource(T::class.java)
            .apply {
                criterionList.forEach { this.and(it) }
            }
            .returnBundle(Bundle::class.java)
            .execute()
        return extractResourceList(bundle)
    }

    /**
     * Execute a query to only get the count of resources (using the request criteria)
     */
    fun <T: DomainResource> queryResourceCount(request: AidboxFhirSearchRequest, clazz: KClass<T>) : Int {
        val totalRequest = request.copy(includeTotal = true, count = 0, sortAsc = "", sortDesc = "", summary = "count")
        val criterionList = generateCriterionList(totalRequest)
        val bundle = client
            .search<IBaseBundle>()
            .forResource(clazz.java)
            .apply {
                criterionList.forEach { this.and(it) }
            }
            .returnBundle(Bundle::class.java)
            .execute()
        return bundle.total
    }

    /**
     * Calls 'queryResources' but returns results in a map with the ID as the map key
     */
    inline fun <reified T : DomainResource> queryResourcesMap(request: AidboxFhirSearchRequest): Map<String, T> {
        return convertToIdMap( queryResources(request) )
    }

    /**
     * Query for ALL elements for the request (does multiple requests)
     *   ** CAN BE SLOW **  (depending on the request definition)
     */
    inline fun <reified T: DomainResource> queryAllResources(request: AidboxFhirSearchRequest) : List<T> {
        val totalResults = mutableListOf<T>()
        var currentPage = 0
        val batchSize = request.count

        // NOTE: comments at bottom of class why batch/page fetching was implemented in this manner.
        while (true) {
            val batchRequest = request.copy(extraParams = request.extraParams + mapOf("_page" to (++currentPage).toString()))
            val batchResults: List<T> = queryResources(batchRequest)
            totalResults.addAll(batchResults)
            if (batchResults.size < batchSize) {
                break
            }
        }
        return totalResults
    }

    /**
     * Calls 'queryAllResources' but returns results in a map with the ID as the map key
     */
    inline fun <reified T : DomainResource> queryAllResourcesMap(request: AidboxFhirSearchRequest): Map<String, T> {
        return convertToIdMap( queryAllResources(request) )
    }

    /**
     * Examines a class and returns 'root' field names
     *   (to figure out possible options for 'fields' on a request)
     */
    fun getFieldNames(clazz: KClass<*>): List<String> {
        return clazz.declaredMemberProperties.map { it.name }.sorted()
    }

    @PublishedApi
    internal inline fun <reified T : DomainResource> convertToIdMap(recordList: List<T>): Map<String, T> {
        return recordList.associateBy { it.idPart }
    }

    @PublishedApi
    internal fun generateCriterionList(request: AidboxFhirSearchRequest): List<ICriterion<UriClientParam>> {
        val totalParamMap = mutableMapOf<String,String>().apply {
            with(request) {
                if (tenantId != "") { put("identifier", tenantId) }
                if (fields != "") { put("_elements", fields) }
                if (ids.isNotEmpty()) { put("_id", ids.joinToString(",")) }
                if (subjectPatientId != "") { put("_subject", "Patient/${subjectPatientId}") }
                put("_count", count.toString()) // the API has count() but setting here for convenience
                if (!includeTotal) { put("_total", "none") }
                if (createdAt != "") { put("_createdAt", createdAt) }
                if (lastUpdated != "") { put("_lastUpdated", lastUpdated) }
                if (sortAsc != "") { put("_sort", sortAsc) }
                else if (sortDesc != "") { put("_sort", "-${sortDesc}") }
                put("_timeout", timeout.toString())
                putAll(extraParams)
            }
        }

        return totalParamMap.map {
            // note: this list handling was needed for ids, but not for elements (unsure why)
            if (it.value.contains(",")) {
                UriClientParam(it.key).matches().values(it.value.split(","))
            }
            else {
                UriClientParam(it.key).matches().value(it.value)
            }
        }
    }

    @PublishedApi
    internal inline fun <reified T: DomainResource> extractResourceList(bundle: Bundle): List<T> {
        return bundle.entry.map { it.resource as T }
    }

    /////////////////////////////////////////////////////////
    // AUTH
    private class CustomBearerTokenAuthInterceptor(hostUrl: String, clientId: String, clientSecret: String) : IClientInterceptor {

        private companion object {
            private val TOKEN_RESP_TYPE_REF: TypeReference<AidBoxTokenResponse> = object: TypeReference<AidBoxTokenResponse>() {}
        }

        private var currentToken: AidBoxTokenResponse = AidBoxTokenResponse(accessToken = "", expiresIn = 0)
        private var currentTokenExpiresTimestamp: Instant = Instant.EPOCH
        private val authRequest: IHttpRequest
        init {
            val authClient = ApacheRestfulClientFactory().getHttpClient(StringBuilder("$hostUrl/auth/token"), null, null, RequestTypeEnum.POST, null)
            val paramMap = mapOf(
                "client_id" to listOf(clientId),
                "client_secret" to listOf(clientSecret),
                "grant_type" to listOf("client_credentials")
            )
            authRequest = authClient.createParamRequest(FhirContext.forR4(), paramMap, EncodingEnum.JSON )
        }

        override fun interceptRequest(theRequest: IHttpRequest) {
            val token = getCurrentTokenString()
            theRequest.addHeader(Constants.HEADER_AUTHORIZATION, "Bearer $token")
        }
        override fun interceptResponse(theResponse: IHttpResponse) { /* do nothing */ }

        private fun getCurrentTokenString(): String {
            val timeNow = Instant.now()
            if (currentTokenExpiresTimestamp.isBefore(timeNow)) {
                currentToken = fetchTokenObject()
                currentTokenExpiresTimestamp = timeNow.plusSeconds(currentToken.expiresIn.toLong()).minusSeconds(10L)
            }
            return currentToken.accessToken
        }

        private fun fetchTokenObject(): AidBoxTokenResponse {
            val response: IHttpResponse = authRequest.execute()
            val respBody: String = extractResponseBody(response)
            if (response.status >= 400) {
                throw RuntimeException("Unable to acquire AidBox token: $respBody")
            }
            // note: using the ObjectMapper that comes w/ the hapi.fhir library for convenience.
            return ObjectMapper().readValue(respBody, TOKEN_RESP_TYPE_REF)
        }

        // grab the response string _and_ close both the entity & response
        private fun extractResponseBody(response: IHttpResponse) : String {
            try {
                response.readEntity().use { inputStream ->
                    return IOUtils.toString(inputStream, "UTF-8")
                }
            }
            finally {
                response.close()
            }
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    private data class AidBoxTokenResponse(
        val accessToken: String = "",
        val refreshToken: String = "",  // todo: when should use this token?
        val expiresIn: Int = 0,
        val tokenType: String = "",
        val needPatientBanner: Boolean = true
    )
}

/*
 NOTES
   1. Originally looked promising to use "_result" instead of 'Bundle'.
      but the sparse fields didn't work in that case
      https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_result
   2. Aidbox hapi client already had paging support
        as shown https://hapifhir.io/hapi-fhir/docs/client/examples.html
      BUT... it's MUCH SLOWER  (and also doesn't work quite write with the '_page=x' vs 'page=x' parameter)

  MISC LINKS
    https://hapifhir.io/hapi-fhir/docs/client/generic_client.html
    https://docs.aidbox.app/api-1/fhir-api/search-1
    https://projectronin.atlassian.net/wiki/spaces/ENG/pages/1123876935/Testing+with+FHIR+as+a+Service
    https://github.com/hapifhir/org.hl7.fhir.core
 */
