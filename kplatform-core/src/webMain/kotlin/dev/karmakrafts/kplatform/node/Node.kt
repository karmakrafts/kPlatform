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

@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.karmakrafts.kplatform.node

import js.numbers.UInt53

internal external interface ProcessVersions : JsAny {
    val node: String
}

internal external interface ProcessMemoryUsage : JsAny {
    val rss: UInt53
    val heapTotal: UInt53
    val heapUsed: UInt53
}

internal external interface MainModule : JsAny {
    fun <T : JsAny> require(name: String): T
}

internal external interface Process : JsAny {
    val versions: ProcessVersions
    val env: JsAny
    val mainModule: MainModule

    fun memoryUsage(): ProcessMemoryUsage
}

private fun getProcess(): Process = js("""process""")

internal val process: Process by lazy(::getProcess)

internal external interface Os : JsAny {
    fun platform(): String?
    fun type(): String?
    fun release(): String?
}

internal expect val os: Os