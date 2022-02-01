plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

val kotlinVersion = project.properties["kotlinVersion"] ?: "1.6.10"

dependencies {
    implementation("com.bmuschko:gradle-docker-plugin:7.2.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.41.0")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.7.5")
    implementation("gradle.plugin.com.google.protobuf:protobuf-gradle-plugin:0.8.18")
    implementation("gradle.plugin.com.google.cloud.artifactregistry:artifactregistry-gradle-plugin:2.1.4")
    implementation("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:3.2.0")
    implementation("io.micronaut.gradle:micronaut-gradle-plugin:3.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
}

tasks {
    compileKotlin {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
