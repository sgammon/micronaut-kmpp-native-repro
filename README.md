# `demo`

Starter app for full-stack Kotlin. Supported features:

- [x] Multi-module build for easy internal dependency management
- [x] Multi-platform support (Web, Server) via Kotlin MPP
- [x] Build convention templates for clean and DRY Gradle build files
- [x] Kotlin build DSL
- [x] Kotlin/JS starter with support for React
- [x] Micronaut/Kotlin server with Handlebars/view support
- [x] Support for Jib
- [x] Support for GraalVM Native
- [x] gRPC support
- [x] Protobuf/gRPC Kotlin support
- [x] Kotlin Coroutines support
- [x] PGV support (`protoc-gen-validate`)
- [ ] Code-sharing with Kotlin JSON services
- [ ] Code sharing with Protobuf and gRPC-Web

## Micronaut 3.3.0 Documentation

- [User Guide](https://docs.micronaut.io/3.3.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.3.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.3.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

## Google Cloud Run GraalVM GitHub Workflow

Workflow file: [`.github/workflows/google-cloud-run-graalvm.yml`](.github/workflows/google-cloud-run-graalvm.yml)

### Workflow description

For pushes to the `master` branch, the workflow will:

1. Setup the build environment with respect to the selected java/graalvm version.
2. Setup of [Google Cloud CLI](https://cloud.google.com/sdk).
3. Authenticate docker to use [Google Container Registry (GCR)](https://cloud.google.com/container-registry/docs).
4. Build, tag and push Docker image with Micronaut application to GCR.
6. Deploy [Google Cloud Run](https://cloud.google.com/run) application.

### Dependencies on other GitHub Actions

- [Setup GraalVM `DeLaGuardo/setup-graalvm`](https://github.com/DeLaGuardo/setup-graalvm)
- [Setup Google Cloud CLI `google-github-actions/setup-gcloud`](https://github.com/google-github-actions/setup-gcloud)

### Setup

Add the following GitHub secrets:

| Name | Description |
| ---- | ----------- |
| GCLOUD_PROJECT_ID | Project id. |
| GCLOUD_SA_KEY | Service account key file. See more on [Creating and managing service accounts](https://cloud.google.com/iam/docs/creating-managing-service-accounts#iam-service-accounts-create-gcloud) and [Deployment permissions for CloudRun](https://cloud.google.com/run/docs/reference/iam/roles#additional-configuration) |
| GCLOUD_IMAGE_REPOSITORY | (Optional) Docker image repository in GCR. For image `[GCLOUD_REGION]/[GCLOUD_PROJECT_ID]/foo/bar:0.1`, the `foo` is an _
image repository_. |

The workflow file also contains additional configuration options that are now configured to:

| Name | Description | Default value |
| ---- | ----------- | ------------- |
| GCLOUD_REGION | Region where the Cloud Run application will be created. See [Cloud Run Release Notes](https://cloud.google.com/run/docs/release-notes) to find out what regions are supported. | `europe-west3` |
| GCLOUD_GCR | Google Container Registry url. See [Overview of Container Registry](https://cloud.google.com/container-registry/docs/overview) to find out valid GCR endpoints. | `eu.gcr.io` |

### Verification

From the workflow step `Deploy Cloud Run` copy out url `https://demo-__________run.app` of the invoke endpoint:

```
Invoke endpoint:
https://demo-__________run.app
```

Call the api endpoint:

```
curl https://demo-__________run.app/demo
```

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)- [Jib Gradle Plugin](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)##
  Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature github-workflow-google-cloud-run-graalvm documentation

- [https://docs.github.com/en/free-pro-team@latest/actions](https://docs.github.com/en/free-pro-team@latest/actions)

## Feature views-handlebars documentation

- [Micronaut Handlebars Views documentation](https://micronaut-projects.github.io/micronaut-views/latest/guide/index.html#handlebars)

- [https://jknack.github.io/handlebars.java/](https://jknack.github.io/handlebars.java/)

## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)

## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature security-jwt documentation

- [Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature email-sendgrid documentation

- [Micronaut Sendgrid Email documentation](https://micronaut-projects.github.io/micronaut-email/latest/guide/index.html#sendgrid)

- [https://docs.sendgrid.com/for-developers](https://docs.sendgrid.com/for-developers)

