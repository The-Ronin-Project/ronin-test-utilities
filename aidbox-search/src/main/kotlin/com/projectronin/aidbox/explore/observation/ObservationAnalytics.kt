package com.projectronin.aidbox.explore.observation

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

        // Group all records by code type  (i.e. "Temp", "BP", "Pulse", "Resp", etc)
        val vitalsMap: Map<String, List<Observation>> = observationResultsList.groupBy { it.code!!.text }
    }
}
