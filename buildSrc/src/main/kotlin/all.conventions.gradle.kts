plugins {
    id("com.github.ben-manes.versions")
    id("com.google.cloud.artifactregistry.gradle-plugin")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.sonarqube")
    idea
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}
