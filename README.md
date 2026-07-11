# kPlatform

[![](https://git.karmakrafts.dev/kk/kplatform/badges/master/pipeline.svg)](https://git.karmakrafts.dev/kk/kplatform/-/pipelines)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.maven.apache.org%2Fmaven2%2Fdev%2Fkarmakrafts%2Fkplatform%2Fkplatform-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kplatform/-/packages)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fdev%2Fkarmakrafts%2Fkplatform%2Fkplatform-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/kplatform/-/packages)
[![](https://img.shields.io/badge/2.4.0-blue?logo=kotlin&label=kotlin)](https://kotlinlang.org/)
[![](https://img.shields.io/badge/documentation-black?logo=kotlin)](https://docs.karmakrafts.dev/kplatform-core)

![](https://img.shields.io/badge/-JVM-blue?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-Android-green?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-Native-lightgray?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-JS-gold?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-WASM/JS-orange?logo=kotlin&labelColor=black)
![](https://img.shields.io/badge/-WASM/WASI-purple?logo=kotlin&labelColor=black)

This library allows easily accessing system- and runtime-information.

Currently, the API exposes the following information:

* System memory
  * Size
  * Available
  * Used
* Operating system
  * System family (Windows, Linux, macOS etc.)
  * Name
  * Vendor
  * Version
* Runtime
  * Type (ART, JVM, Native etc.)
  * Name
  * Vendor 
  * Version
  * Environment
  * Memory
    * Size
    * Available
    * Used

All of this information is available through the global `Platform` singleton.

### How to use it

First, add the official Maven Central repository to your settings.gradle.kts:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://central.sonatype.com/repository/maven-snapshots")
        mavenCentral()
    }
}
```

Then add a dependency on the library in your root buildscript:

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("dev.karmakrafts.kplatform:kplatform-core:<version>")
            }
        }
    }
}
```

Or, if you are only using Kotlin/JVM, add it to your top-level dependencies block instead.