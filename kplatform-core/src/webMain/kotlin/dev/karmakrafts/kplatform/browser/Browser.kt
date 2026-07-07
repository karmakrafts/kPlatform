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

package dev.karmakrafts.kplatform.browser

import js.numbers.UInt53
import web.navigator.Navigator
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.js
import kotlin.js.unsafeCast

private fun hasPerformance(): Boolean = js("""'performance' in window""")
private fun getPerformance(): LegacyPerformance = js("""window.performance""")
private fun hasExtendedNavigator(): Boolean = js("""'userAgentData' in navigator""")

internal external interface UserAgentData : JsAny {
    val platform: String
}

internal external interface ExtNavigator : JsAny {
    val userAgentData: UserAgentData
}

internal external interface LegacyMemoryInfo : JsAny {
    val jsHeapSizeLimit: UInt53
    val usedJSHeapSize: UInt53
}

internal external interface LegacyPerformance : JsAny {
    val memory: LegacyMemoryInfo?
}

internal val Navigator.isExt: Boolean by lazy(::hasExtendedNavigator)
internal inline val Navigator.ext: ExtNavigator get() = unsafeCast<ExtNavigator>()

internal val browserPerformance: LegacyPerformance? by lazy {
    if (!hasPerformance()) null
    else getPerformance()
}