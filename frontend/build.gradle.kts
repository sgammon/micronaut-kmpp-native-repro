plugins {
    id("all.conventions")
    id("browser.conventions")
}

val kotlinVersion = project.properties["kotlinVersion"] ?: "1.6.10"
val reactVersion = project.properties["reactVersion"] ?: "17.0.2"
val wrapperVersion = project.properties["kotlinWrapperVersion"] ?: "pre.292"
val kWrapVersion = "$wrapperVersion-kotlin-$kotlinVersion"

kotlin {
    sourceSets {
        val jsMain by getting {
            dependencies {
//                implementation(npm("grpc-web", "1.3.1", generateExternals = false))
//                implementation(devNpm("webpack-inject-plugin", "1.5.5"))
//                implementation(devNpm("html-webpack-plugin", "5.5.0"))
//                implementation(devNpm("script-loader", "0.7.2"))
//                implementation(devNpm("imports-loader", "3.1.1"))
//                implementation(devNpm("esbuild-loader", "2.18.0"))

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.3")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-legacy:$reactVersion-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:$reactVersion-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactVersion-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom-legacy:$reactVersion-$kWrapVersion")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-history:5.2.0-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.3.1-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.3.1-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-$kWrapVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-typescript:4.5.5-$kWrapVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(npm("mocha", "9.1.2"))
            }
        }
    }
}

idea {
    module {
        sourceDirs.add(file("externals"))
        sourceDirs.add(file("package.json.d"))
        sourceDirs.add(file("webpack.config.d"))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile> {
    dependsOn(
        ":base:build"
    )
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.toMutableList().plus(listOf(
            "-Xir-property-lazy-initialization",
            "-opt-in=kotlin.RequiresOptIn",
        ))
    }
}