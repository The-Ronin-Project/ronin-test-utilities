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

        val conditionList : List<Condition> = client.queryResources(req)

        val categoryCountMap = mutableMapOf<String,Int>()
        for (condition in conditionList) {
            // note: the use of 'firstOrNull' technically not correct, but not important atm.
            val catText: String = condition.category.mapNotNull { it.coding.firstOrNull()?.code }.sorted().joinToString(",")
            categoryCountMap[catText] = categoryCountMap.getOrDefault(catText,0) + 1
        }

        val reportString = buildString {
            for (entry in categoryCountMap) {
                append("${entry.key} -- ${entry.value}\n")
            }
        }
        println(reportString)
    }
}
