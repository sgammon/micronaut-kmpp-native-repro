plugins {
    id("com.google.cloud.artifactregistry.gradle-plugin")
    id("com.github.ben-manes.versions")
    id("org.sonarqube")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.serialization")
    java
    idea
    jacoco
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
            kotlinOptions.apiVersion = "1.6"
            kotlinOptions.languageVersion = "1.6"
        }

        withJava()

        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
