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

val kotlinVersion = project.properties["kotlinVersion"] ?: "1.6.10"
val reactVersion = project.properties["reactVersion"] ?: "17.0.2"
val wrapperVersion = project.properties["kotlinWrapperVersion"] ?: "pre.292"
val kWrapVersion = "$wrapperVersion-kotlin-$kotlinVersion"

val envTarget = when (val envTargetArg = (project.findProperty("envTarget") as String?)?.toUpperCase()) {
    "DEV", "PROD" -> envTargetArg
    null -> "DEV"
    else -> throw Exception("Invalid envTarget: '$envTargetArg'. Expected 'DEV' or 'PROD'.")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            val (compileMode, sourceMaps) = if (System.getProperty("mode", "DEVELOPMENT") == "PRODUCTION") {
                (
                    org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.PRODUCTION to
                    org.jetbrains.kotlin.gradle.targets.js.webpack.WebpackDevtool.EVAL_SOURCE_MAP
                )
            } else {
                org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.DEVELOPMENT to
                null
            }

            commonWebpackConfig {
                mode = compileMode
//                devtool = sourceMaps
                cssSupport.enabled = true
                cssSupport.mode = "extract"
//                devServer?.`open` = false
//                devServer?.port = 3000
//                outputFileName = "app.js"
//                export = false
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }

            // set extra webpack args to pass env
            val envTargetWebpackArgs = listOf("--env", "envTarget=$envTarget")
            webpackTask { args.plusAssign(envTargetWebpackArgs) }
            runTask { args.plusAssign(envTargetWebpackArgs) }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-js:1.6.0")
}
