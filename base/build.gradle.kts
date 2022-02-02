import com.google.protobuf.gradle.*

plugins {
    id("all.conventions")
    id("common.conventions")
    id("com.google.protobuf")
    kotlin("multiplatform")
    idea
    java
    `java-library`
}

val protobufVersion = project.properties["protobufVersion"] ?: "3.19.4"
val protocVersion = project.properties["protocVersion"] ?: protobufVersion
val protoValidateVersion = project.properties["protoValidateVersion"] ?: "0.6.3"
val grpcVersion = project.properties["grpcVersion"] ?: "1.21.0"
val grpccVersion = project.properties["grpccVersion"] ?: grpcVersion
val grpcKotlinVersion = project.properties["grpcKotlinVersion"] ?: "1.2.1"
val protocValidateVersion = project.properties["protocValidateVersion"] ?: "0.6.3"
val jacksonVersion = project.properties["jacksonVersion"] ?: "2.13.1"

sourceSets {
    main {
        proto {
            srcDir("src/commonMain/proto")
        }

        java {
            srcDirs(
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/grpckt",
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/javapgv",
                "build/generated/source/proto/main/kotlin",
            )
        }
    }
}

kotlin {
    sourceSets {
        val sourceSet = sourceSets.getByName("jsMain")
        sourceSet.kotlin.srcDir("build/generated/sources/proto/main/js")
        sourceSet.kotlin.srcDir("build/generated/sources/proto/main/grpc-web")

        val commonMain by getting {
            dependencies {
                implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
                implementation("jakarta.inject:jakarta.inject-api:2.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            }
        }
    }
}

dependencies {
    protobuf(files("externals"))
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("io.envoyproxy.protoc-gen-validate:pgv-java-stub:$protocValidateVersion")
    implementation("io.grpc:grpc-netty:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protocVersion"
    }

    plugins {
        id("kotlin")
        id("grpc-web") {
            path = "/usr/local/bin/protoc-gen-grpc-web"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpccVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
        id("javapgv") {
            artifact = "io.envoyproxy.protoc-gen-validate:protoc-gen-validate:$protocValidateVersion"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("kotlin")
                id("grpc")
                id("grpckt")
                id("grpc-web") {
                    options.add("mode=grpcweb")
                    options.add("import_style=commonjs+dts")
                }
                id("javapgv") {
                    options.add("lang=java")
                }
                id("js") {
                    options.add("import_style=commonjs")
                    options.add("binary")
                }
            }
            task.generateDescriptorSet = true
        }
        ofSourceSet("main")
    }
}

idea {
    module {
        sourceDirs.add(file("src/commonMain/proto"))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("generateProto")
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.toMutableList().plus(
            "-opt-in=kotlin.RequiresOptIn",
        )
    }
}
