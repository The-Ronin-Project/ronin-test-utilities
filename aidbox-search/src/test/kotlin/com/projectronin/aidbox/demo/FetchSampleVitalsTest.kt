package com.projectronin.aidbox.demo

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.projectronin.aidbox.client.AidboxFhirClient
import com.projectronin.aidbox.client.AidboxFhirSearchRequest
import org.hl7.fhir.r4.model.Observation
import org.hl7.fhir.r4.model.Quantity
import org.hl7.fhir.r4.model.StringType
import org.hl7.fhir.r4.model.Type
import org.junit.jupiter.api.Test

class FetchSampleLabsTest {

    private companion object {
        const val host = "https://staging.project-ronin.aidbox.app"
        const val clientId = "____TO_FILL_IN____"
        const val clientSecret = "____TO_FILL_IN____"
        val client = AidboxFhirClient(host, clientId, clientSecret)
    }

    @Test
    fun `fetch sample vital data`() {
        val req = AidboxFhirSearchRequest(
            tenantId = "mdaoc",
            //fields =  "code",
            count = 500,  // limit to first 500  (too big will make request timeout)
            timeout = 60,
            extraParams = mapOf("category" to "laboratory")
        )

        val observationResultList : List<Observation> = client.queryResources(req)
        val filteredResultList = observationResultList.filter { it.code != null && it.code!!.text != null}

        // Group all records by code type
        //   For Labs ... ("Potassium Lvl", "Glucose", etc)
        //   For Vitals ... ("Temp", "BP", "Pulse", "Resp", etc)
        val vitalsMap: Map<String, List<Observation>> = filteredResultList.groupBy { it.code!!.text }

        val resultList = mutableListOf<LabPojo>()
        val keys = vitalsMap.keys

        // for each key (type), we'll grab a single observation example
        for (key in keys) {
            val observationList = vitalsMap.getValue(key)
            val firstObservation: Observation = observationList.first()
            if (firstObservation.value == null) {
                continue
            }
            val observationValue: Type = firstObservation.value

            val labResult = LabPojo(name = key)

            if (observationValue is StringType) {
                val strValue : StringType = observationValue
                labResult.labValue = strValue.value
                labResult.type = "string"
            }
            else if (observationValue is Quantity) {
                val qValue : Quantity = observationValue
                labResult.labValue = qValue.value?.toString()
                labResult.units = qValue.unit
                labResult.type = "quantity"
            }

            if (labResult.labValue == null) {
                continue
            }

            val refRange = firstObservation.referenceRange
            if (refRange != null && refRange.isNotEmpty()) {
                val firstRefRange = refRange.first()
                labResult.refRangeLow = firstRefRange.low?.value?.toString()
                labResult.refRangeHigh = firstRefRange.high?.value?.toString()
            }

            val interpretation = firstObservation.interpretation
            if (interpretation != null && interpretation.isNotEmpty()) {
                labResult.interpretation = interpretation.first().text
            }

            resultList.add(labResult)
        }

        // convert our results to JSON and print them out
        val prettyJson = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultList)
        println(prettyJson)
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class LabPojo(
        var name : String? = null,
        var labValue : String?= null,
        var units: String? = null,
        var refRangeLow: String? = null,
        var refRangeHigh: String? = null,
        var type: String? = null,
        var interpretation: String? = null,
    )
}
