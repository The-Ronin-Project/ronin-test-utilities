package com.projectronin.aidbox.explore.observation

import com.fasterxml.jackson.databind.ObjectMapper
import com.projectronin.aidbox.client.AidboxFhirClient
import com.projectronin.aidbox.client.AidboxFhirSearchRequest
import org.hl7.fhir.r4.model.Observation

class ObservationAnalytics {

    fun observationVitalsCodes(client: AidboxFhirClient) {
        // grab the first 500 Observations for tenant (only care about eh 'code' field)
        val req = AidboxFhirSearchRequest(
            tenantId = "mdaoc",
            fields =  "code",
            count = 500,
            timeout = 60,
           extraParams = mapOf("category" to "vital-signs")
        )

        val observationResultsList : List<Observation> = client.queryResources(req)

        // convert into a map  (where key is the code.text value)
        //   multiple observations will have the same code.text, so this map will be much smaller size than the results list
        val customCodableMap = observationResultsList
            .map { it.code!! }
            .associate { it.text to it.coding.orEmpty().map { PrintalbeCoding(it.display.orEmpty(), it.code.orEmpty(), it.system.orEmpty()) } }
            .toSortedMap()

        val textKeysOnly = customCodableMap.keys.sorted()
        println("MAP KEYS...")
        println(textKeysOnly)

        println("")

        println("FULL MAP...")
        val prettyJson = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(customCodableMap)
        println(prettyJson)
    }

    private data class PrintalbeCoding(val display: String, val code: String, val system: String)
}
