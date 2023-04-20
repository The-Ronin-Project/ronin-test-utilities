import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.allegro.tech.build.axion.release.domain.properties.TagProperties

plugins {
    kotlin("jvm")

    id("org.jlleitschuh.gradle.ktlint")
    id("com.dipien.releaseshub.gradle.plugin")
    id("pl.allegro.tech.build.axion-release")

    jacoco
    `maven-publish`
}

scmVersion {
    tag {
        initialVersion(TagProperties.InitialVersionSupplier { _, _ -> "1.0.0" })
        prefix.set("")
    }
    versionCreator { versionFromTag, position ->
        val supportedHeads = setOf("HEAD", "master", "main")
        if (!supportedHeads.contains(position.branch)) {
            val jiraBranchRegex = Regex("(\\w+)-(\\d+)-(.+)")
            val match = jiraBranchRegex.matchEntire(position.branch)
            val branchExtension = match?.let {
                val (project, number, _) = it.destructured
                "$project$number"
            } ?: position.branch

            "$versionFromTag-$branchExtension"
        } else {
            versionFromTag
        }
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // Java
    java {
        sourceCompatibility = JavaVersion.VERSION_11

        // Generate sources and javadoc JARs. These will automatically be published in publishing is active.
        withSourcesJar()
        withJavadocJar()
    }

    // Kotlin
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    // Repositories
    repositories {
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-releases/")
            mavenContent {
                releasesOnly()
            }
        }

        // Only include the snapshot repo if this is not a release task
        if (System.getProperty("ronin.release", "false") == "false") {
            maven {
                url = uri("https://repo.devops.projectronin.io/repository/maven-snapshots/")
                mavenContent {
                    snapshotsOnly()
                }
            }
        }

        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-public/")
            mavenContent {
                releasesOnly()
            }
        }
        mavenLocal()
    }

    // Set version
    project.version = project.rootProject.scmVersion.version
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "maven-publish")

    // General dependencies
    dependencies {
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")

        // Logging
        implementation("io.github.microutils:kotlin-logging:3.0.4")
        implementation("org.slf4j:slf4j-api:2.0.7")
        runtimeOnly("ch.qos.logback:logback-classic:1.4.5")

        // JUnit
        testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    }

    // JUnit
    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }

    // Jacoco
    jacoco {
        toolVersion = "0.8.8"
        // Custom reports directory can be specfied like this:
        reportsDirectory.set(file("./codecov"))
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(true)
        }
    }

    tasks {
        test {
            testLogging.showStandardStreams = true
            testLogging.showExceptions = true
        }
    }

    tasks.test {
        finalizedBy(tasks.jacocoTestReport)
    }

    // Publishing
    publishing {
        repositories {
            maven {
                name = "nexus"
                credentials {
                    username = System.getenv("NEXUS_USER")
                    password = System.getenv("NEXUS_TOKEN")
                }
                url = if (project.version.toString().endsWith("SNAPSHOT")) {
                    uri("https://repo.devops.projectronin.io/repository/maven-snapshots/")
                } else {
                    uri("https://repo.devops.projectronin.io/repository/maven-releases/")
                }
            }
        }
        publications {
            create<MavenPublication>("library") {
                from(components["java"])
            }
        }
    }
}
