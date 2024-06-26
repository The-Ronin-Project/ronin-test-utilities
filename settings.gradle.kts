rootProject.name = "ronin-test-utilities"

include("ronin-test-data-generator")

for (project in rootProject.children) {
    project.buildFileName = "${project.name}.gradle.kts"
}

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.20"
        id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
        id("com.dipien.releaseshub.gradle.plugin") version "4.0.0"
        id("pl.allegro.tech.build.axion-release") version "1.15.0"
    }

    repositories {
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-snapshots/")
            mavenContent {
                snapshotsOnly()
            }
        }
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-releases/")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            url = uri("https://repo.devops.projectronin.io/repository/maven-public/")
            mavenContent {
                releasesOnly()
            }
        }
        mavenLocal()
        gradlePluginPortal()
    }
}
