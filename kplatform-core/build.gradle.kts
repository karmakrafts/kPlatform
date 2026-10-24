/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import dev.karmakrafts.conventions.asAAR
import dev.karmakrafts.conventions.configureJava
import dev.karmakrafts.conventions.dokka.configureDokka
import dev.karmakrafts.conventions.dokka.withKotlin
import dev.karmakrafts.conventions.dokka.withKotlinxIo
import dev.karmakrafts.conventions.kotlin.defaultCompilerOptions
import dev.karmakrafts.conventions.kotlin.withAndroidLibrary
import dev.karmakrafts.conventions.kotlin.withBrowser
import dev.karmakrafts.conventions.kotlin.withDefaultDeviceTests
import dev.karmakrafts.conventions.kotlin.withDefaultHostTests
import dev.karmakrafts.conventions.kotlin.withJvm
import dev.karmakrafts.conventions.kotlin.withNative
import dev.karmakrafts.conventions.kotlin.withNodeJs
import dev.karmakrafts.conventions.kotlin.withWasmWasi
import dev.karmakrafts.conventions.kotlin.withWasmtime
import dev.karmakrafts.conventions.kotlin.withWeb
import dev.karmakrafts.conventions.setProjectInfo
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    signing
    `maven-publish`
}

configureJava(libs.versions.java)

configureDokka {
    withKotlin()
    withKotlinxIo()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    defaultCompilerOptions()
    withSourcesJar()
    withAndroidLibrary("$group.core") {
        withDefaultHostTests()
        withDefaultDeviceTests()
    }
    withNative {
        if (konanTarget.family.isAppleFamily) {
            compilations {
                named("main") {
                    cinterops {
                        create("platform")
                    }
                }
            }
        }
    }
    withJvm()
    withWeb {
        compilerOptions {
            target = "es2020"
        }
        useEsModules()
        withBrowser()
        withNodeJs()
    }
    withWasmWasi {
        @OptIn(ExperimentalWasmDsl::class) //
        withWasmtime()
    }
    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndAndroid") {
                withJvm()
                withAndroidLibrary()
            }
        }
    }
    sourceSets {
        webMain {
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
            }
        }
        named("jvmAndAndroidMain") {
            dependencies {
                val oshiCore = libs.oshi.core.get()
                implementation("${oshiCore.module}:${oshiCore.version}") {
                    exclude(group = "net.java.dev.jna", module = "jna")
                    exclude(group = "net.java.dev.jna", module = "jna-jpms")
                    exclude(group = "net.java.dev.jna", module = "jna-platform")
                    exclude(group = "net.java.dev.jna", module = "jna-platform-jpms")
                    exclude(group = "net.java.dev.jna", module = "jna-runtime")
                    exclude(group = "net.java.dev.jna", module = "jna-runtime-jpms")
                }
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.jna)
                implementation(libs.jna.platform)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.jna.asProvider().asAAR())
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        named("androidHostTest") {
            dependencies {
                implementation(libs.jna)
                implementation(libs.jna.platform)
            }
        }
    }
}

tasks {
    withType<KotlinJvmTest>().configureEach {
        jvmArgs("-Xms2G", "-Xmx2G")
    }
}

publishing {
    setProjectInfo(
        name = "kPlatform Core",
        description = "Platform information for Kotlin Multiplatform",
        url = "https://git.karmakrafts.dev/kk/kplatform"
    )
}