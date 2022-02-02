plugins {
    id("all.conventions")
    id("server.conventions")
    id("io.micronaut.application")
    java
    jacoco
}

version = "0.1"
group = "com.example"

val kotlinVersion = project.properties["kotlinVersion"] as String
val gcloudVersion = project.properties["gcloudVersion"] as String
val grpcVersion = project.properties["grpcVersion"] as String
val grpcKotlinVersion = project.properties["grpcKotlinVersion"] as String
val protobufVersion = project.properties["protobufVersion"] as String
val protocValidateVersion = project.properties["protocValidateVersion"] as String
val graalVersion = project.properties["graalVersion"] as String
val micronautVersion = project.properties["micronautVersion"] as String
val jacksonVersion = project.properties["jacksonVersion"] as String
val dockerBase = (
    "${project.properties["dockerBase"] ?: "ghcr.io/graalvm/jdk:java11"}${project.properties["dockerBasePin"] ?: ""}"
)

dependencies {
    api(project(":base"))
    api(enforcedPlatform("com.google.cloud:libraries-bom:$gcloudVersion"))
    api("org.slf4j:slf4j-api:1.7.33")

    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.security:micronaut-security-annotations")

    implementation("com.google.cloud:native-image-support:0.11.0")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    implementation("io.envoyproxy.protoc-gen-validate:pgv-java-stub:$protocValidateVersion")
    implementation("io.grpc:grpc-netty:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-context")
    implementation("io.micronaut.grpc:micronaut-grpc-client-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime")
    implementation("io.micronaut.email:micronaut-email-sendgrid")
    implementation("io.micronaut.email:micronaut-email-template")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.views:micronaut-views-handlebars")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:testcontainers")

    compileOnly("org.graalvm.nativeimage:svm")

    runtimeOnly("ch.qos.logback:logback-classic:1.2.10")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    if (project.properties["engine"] == "jvm") {
        implementation("com.fasterxml.jackson.module:jackson-module-blackbird:2.13.1")
    }
    if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_MAC)) {
        runtimeOnly("io.netty:netty-transport-native-kqueue:osx-x86_64")
        runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.47.Final:osx-x86_64")
    } else {
        runtimeOnly("io.netty:netty-transport-native-epoll:linux-x86_64")
        runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.47.Final:linux-x86_64")
    }
    runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.47.Final")
}

application {
    mainClass.set("com.demo.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
    targetCompatibility = JavaVersion.toVersion("11")
}

graalvmNative.toolchainDetection.set(false)

idea {
    module {
        sourceDirs.add(file("src/main/graal"))
    }
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.demo.*")
    }

    aot {
        version.set("1.0.0-M7")
        optimizeServiceLoading.set(true)
        convertYamlToJava.set(true)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
    }
}

graalvmNative {
    binaries {
        named("main") {
            debug.set(false)
            verbose.set(false)
            fallback.set(false)

            jvmArgs.add("-Duser.country=US")
            jvmArgs.add("-Duser.language=en")
            jvmArgs.add("-Duser.timezone=Pacific/Los_Angeles")
            jvmArgs.add("-Dfile.encoding=UTF-8")

            buildArgs.add("--language:regex")
            buildArgs.add("--enable-url-protocols=https,http")
            buildArgs.add("--report-unsupported-elements-at-runtime")
        }
    }
}

tasks.named<Copy>("processResources") {
    dependsOn(":frontend:build", ":frontend:metadataJar")
    from(project(":frontend").tasks.named("jsBrowserDistribution")) {
        include(
            "**/*.js",
            "**/*.css",
            "**/*.html",
            "**/*.hbs",
            "**/*.txt",
            "**/*.svg",
        )
        into("static")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(":frontend:build", ":frontend:metadataJar")
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
    kotlinOptions {
        jvmTarget = "11"
        apiVersion = "1.6"
        languageVersion = "1.6"
        javaParameters = true
        freeCompilerArgs = listOf(
            "-Xjvm-default=all",
            "-Xjsr305=strict",
            "-opt-in=kotlin.RequiresOptIn",
        )
    }
}

tasks.named<io.micronaut.gradle.docker.MicronautDockerfile>("dockerfile") {
    baseImage.set(dockerBase)
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    baseImage.set(dockerBase)
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showStackTraces = true
            showStandardStreams = project.properties["testlogs"] == "true"
        }
    }

    jacocoTestReport {
        enabled = true

        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
        }
    }

    dockerBuild {
        images.add("${project.properties["dockerRegistry"]}/${project.properties["dockerPath"]}/jvm:${project.version}")
    }

    dockerBuildNative {
        images.add("${project.properties["dockerRegistry"]}/${project.properties["dockerPath"]}/native:${project.version}")
    }

    jib {
        from {
            image = dockerBase
        }
        to {
            image = "${project.properties["dockerRegistry"]}/${project.properties["dockerPath"]}/jvm:${project.version}"
        }
    }
}
