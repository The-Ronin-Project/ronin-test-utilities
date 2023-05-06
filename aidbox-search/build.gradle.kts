group = "com.projectronin.test"
version = "0.0.3-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ca.uhn.hapi.fhir:hapi-fhir-structures-r4:6.4.4")
    implementation("ca.uhn.hapi.fhir:hapi-fhir-client:6.4.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
