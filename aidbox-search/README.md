# aidbox-search
A super-simple Aidbox client used to do ad-hoc Aidbox queries.  Speicially to view & and explore what the data in Aidbox looks like.

NOTE: this is _NOT_ using the interops Aidbox Client.

## Usage Examples
```kotlin
val client = AidboxFhirClient("https://staging.project-ronin.aidbox.app", "__client_id__", "__client_secret__")

// query single patient by id
val patient: Patient = client.getResource("1xrekpx5-eQ6bfRoF2gnLFGdZ3Gu-cyg3")

// query single appointment by id
val appointment: Appointment  = client.getResource("1xrekpx5-50000613960")

// query 200 patients in tenant 'apposnd'
val req = AidboxFhirSearchRequest(tenantId = "apposnd", count = 200)
val patientList : List<Patient> = client.queryResources(req)

// return ALL Conditions for tenant '1xrekpx5' but only return the 'category' field.
// (will perform multiple requests in batches of 1000)
val req = AidboxFhirSearchRequest(
    tenantId = "1xrekpx5",
    fields =  "category",
    count = 1000
)
val conditionList : List<Condition> = client.queryAllResources(req)
```

More examples available in [ExamplesTest](src/test/kotlin/com/projectronin/aidbox/demo/ExamplesTest.kt).

## Implementation Details

* Simulates requests that can be normally made on the [Rest WebPage](https://staging.project-ronin.aidbox.app/ui/console#/rest)
* For simplicity, this project has _**NO RONIN LIBRARY DEPENDENCIES**_
* Uses publicly avaialbe [hapifhir](https://github.com/hapifhir/org.hl7.fhir.core) client (and not the interops client)
* exeample usage the the internal hapifhir client is available [HERE](https://hapifhir.io/hapi-fhir/docs/client/generic_client.html).
* using the hapifhir built-in paging diddn't work (and would have been insanely slow it if did), so batch querying handled bespokely.
* currently doesn't have much as far as unittests (shame on me :-) )
