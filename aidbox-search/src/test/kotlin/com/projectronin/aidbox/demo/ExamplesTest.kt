package com.projectronin.aidbox.demo

import com.projectronin.aidbox.client.AidboxFhirClient
import com.projectronin.aidbox.client.AidboxFhirSearchRequest
import org.hl7.fhir.r4.model.Appointment
import org.hl7.fhir.r4.model.Condition
import org.hl7.fhir.r4.model.Patient
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Will actually execute the examples in the STaging environment
 *
 * This test is disabled by default (on purpsoe)
 *   because not going to put the creds in the code checkin
 */
@Disabled  // <---- DISABLED
class ExamplesTest {

    private companion object {
        private const val AIDBOX_URL = "https://staging.project-ronin.aidbox.app"
        private const val CLIENT_ID = "__FILL_ME_IN__"
        private const val CLIENT_SECRET = "__FILL_ME_IN__"
        private val client = AidboxFhirClient(AIDBOX_URL, CLIENT_ID, CLIENT_SECRET)
    }

    /**
     * how to do a simple get object by id
     */
    @Test
    fun fetchPatient() {

        val client = AidboxFhirClient("https://staging.project-ronin.aidbox.app", "__client_id__", "__client_secret__")

        val patient: Patient = client.getResource("1xrekpx5-eQ6bfRoF2gnLFGdZ3Gu-cyg3")
        println("Successfull fetched patient with ID: ${patient.id}")
    }

    /**
     * fetch an appointment by id.
     * ___NOTE___ call is virtually identical to 'fetchPatient' above
     *   the specified return type here auto determines what resource you are querying!
     */
    @Test
    fun fetchAppointment() {
        val appointment: Appointment  = client.getResource("1xrekpx5-50000613960")
        println("Successfully fetched appointment with ID: ${appointment.id}")
    }

    /**
     * Search patients for tenant 'apposnd'
     * Results limited to 200 (default is 100)
     */
    @Test
    fun queryPatients() {
        val req = AidboxFhirSearchRequest(
            tenantId = "apposnd",
            count = 200
        )
        val patientList : List<Patient> = client.queryResources(req)
        println("Successfully fetched ${patientList.size} patients.")
    }

    /**
     * Query Appointments
     *   NOTE: it's the same as the patient query with a very subtle difference of declaring the type
     */
    @Test
    fun queryAppointments() {
        val req = AidboxFhirSearchRequest(
            tenantId = "apposnd",
            count = 200
        )
        val appointmentList : List<Appointment> = client.queryResources(req)
        println("Successfully fetched ${appointmentList.size} patients.")
    }

    /**
     * query patients, but ONLY POPULATE CERTAIN FIELDS ON RESPONSE
     * This is _VERY_ useful thing to do when you want to query a LARGE amount of data
     * NOTE: the field names are CASE-SENSITIVE  (i.e. 'birthdate' w/ lowercase 'd' would not work)
     */
    @Test
    fun searchPatientsLimitedResponseFields() {
        val patientReq = AidboxFhirSearchRequest(
            fields =  "id,birthDate,name.given",
            count = 1000
        )
        val patients : List<Patient> = client.queryResources(patientReq)
        println("Fetched ${patients.size} patients with sparsely populated results")
    }

    /**
     * Search Conditions where category=problem-list-item
     *
     * **IMPORTANT** you can only do searches on fields that are indexed for Aidbox instance
     *    i.e. on the REST UI webpage, if did a request like: "GET /fhir/Condition?recordedDate=2018"
     *        then would get an error response  "error - No search parameter for Condition.recordedDate"
     *        same thing applies here!
     */
    @Test
    fun searchTenantProblemListConditions() {
        val conditionReq = AidboxFhirSearchRequest(
            tenantId = "v7r1eczk",
            extraParams = mapOf("category" to "problem-list-item")
        )
        val conditions : List<Condition> = client.queryResources(conditionReq)
        println("Found ${conditions.size} Conditions.")
    }

    /**
     * Search for __ALL__ Appointments
     *
     * The reqquest below is calling 'getAllResources' wwith will do multiple requests
     * to fetch all the data in batches.
     *
     * NOTE: it is possible to abuse the system with this.
     *   Thefore this method should be used ___VERY CAREFULLY__
     */
    @Test
    fun fetchAllTenantAppointments() {
        val req = AidboxFhirSearchRequest(
            tenantId = "apposnd", // search for this tenant
            count = 1000,         // each request will fetch 1000 records at a time.
            fields = "id,status,participant,start",  // ALMOST ALWAYS should limit fields returned (else it's too slow)
        )
        val appointments : List<Appointment> = client.queryAllResources(req)
        println("Found ${appointments.size} Appointments.")
    }

    /**
     * Get total count of Appointments for tenant
     *
     * NOTE: have seens this be SLOW on Condistions/Observations
     */
    @Test
    fun fetchTenantAppointmentCount() {
        val req = AidboxFhirSearchRequest(
            tenantId = "apposnd", // search for this tenant
        )

        // NOTE: have to pass in 2nd param to indicate with resource type to count
        val totalAppointments : Int = client.queryTotal(req, Appointment::class)
        println("Total Number of Appointments: ${totalAppointments}.")
    }

}
