package com.projectronin.aidbox.explore.condition

import com.projectronin.aidbox.client.AidboxFhirClient
import com.projectronin.aidbox.client.AidboxFhirSearchRequest
import org.hl7.fhir.r4.model.Condition

class ConditionAnalytics {

    // print out the unique category combinations for first batch of categories for the tenant
    fun analyzeCategories(client: AidboxFhirClient) {
        val req = AidboxFhirSearchRequest(
            tenantId = "1xrekpx5",
            fields =  "category",
            count = 1000
        )
        val conditionList : List<Condition> = client.queryAllResources(req)

        // todo: i'm sure there's a cleaner way to do below
        val categoryMap = mutableMapOf<String,Int>()
        for (condition in conditionList) {
            var catText = ""
            val categoroyList = condition.category
            for (codeableConcept in categoroyList) {
                if (catText.isNotEmpty()) {
                    catText += ","
                }
                catText += codeableConcept.text ?: ""
            }
            if (catText.isEmpty()) {
                catText = "(empty)"
            }

            val currentCount = categoryMap.getOrDefault(catText, 0)
            val newCount = currentCount + 1
            categoryMap[catText] = newCount
        }

        val reportString = buildString {
            for (entry in categoryMap) {
                append("${entry.key} -- ${entry.value}\n")
            }
        }
        println(reportString)
    }
}
