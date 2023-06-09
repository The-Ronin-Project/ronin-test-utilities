package com.projectronin.aidbox.client

/**
 * Request structure used to query Aidbox.
 */
data class AidboxFhirSearchRequest(
    /**
     * TenantId to search for  (blank will search all tenants)
     */
    val tenantId: String = "",

    /**
     * Comma-delimited list of fields when want to only return sparse objects (better performance)
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_elements">elements</a>
     */
    val fields: String = "",

    /**
     * Total number of records to return
     *   (or 'batch size' if using 'getAllResources')
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_count-and-_page">count</a>
     */
    val count: Int = DEFAULT_COUNT,

    /**
     * flag to fetch total
     *   Should _only_ be used for 'queryResourceCount' requests,
     *     otherwise fetching the total does an 'extra' aidbox request that is slow
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_total-or-_countmethod">total</a>
     */
    val includeTotal: Boolean = false,

    /**
     * search for records by patientId in the subject reference
     *   NOTE: request only works with endpoints that support it!
     */
    val subjectPatientId: String  = "",

    /**
     * When you want to query for a collection of specific ids
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_id">ids</a>
     */
    val ids : List<String> = emptyList(),

    /**
     * search lastUpdated
     *   NOTE: must be in expected format (only year - 2019 year & month - 2019-03 date - 2019-03-05)
     * )
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_lastupdated">lastUpdated</a>
     */
    val lastUpdated: String = "",

    /**
     * search createdAt
     *   NOTE: must be in expected format
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_lastupdated">createdAt</a>
     */
    val createdAt: String = "",

    /**
     * sort results by the given field name (ascending)
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_sort">sort</a>
     */
    val sortAsc: String = "",

    /**
     * sort results by the given field name (descending)
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_sort">sort</a>
     */
    val sortDesc: String = "",

    /**
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_summary">summary</a>
     */
    val summary: String = "",

    /**
     * Request timeout a request (in seconds)
     * @see <a href="https://docs.aidbox.app/api-1/fhir-api/search-1/search-parameters-list/_timeout">Timeout</a>
     */
    val timeout: Int = DEFAULT_TIMEOUT_SECONDS,

    /**
     * Map to place any extra key/value query pairs
     */
    val extraParams: Map<String,String> = emptyMap()
) {
    companion object {
        const val DEFAULT_COUNT = 100
        const val DEFAULT_TIMEOUT_SECONDS = 30
    }
}
