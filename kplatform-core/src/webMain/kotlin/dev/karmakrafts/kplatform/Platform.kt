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

package dev.karmakrafts.kplatform

import dev.karmakrafts.kplatform.browser.BrowserMemory
import dev.karmakrafts.kplatform.browser.BrowserOs
import dev.karmakrafts.kplatform.browser.BrowserRuntime
import dev.karmakrafts.kplatform.node.NodeMemory
import dev.karmakrafts.kplatform.node.NodeOs
import dev.karmakrafts.kplatform.node.NodeRuntime
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js

@OptIn(ExperimentalWasmJsInterop::class)
private fun checkIsNode(): Boolean = js("""typeof process !== 'undefined' && process.release.name === 'node'""")

actual object Platform {
    private val isNode: Boolean by lazy(::checkIsNode)

    actual val runtime: Runtime = if (isNode) NodeRuntime else BrowserRuntime
    actual val os: Os = if (isNode) NodeOs else BrowserOs
    actual val memory: Memory = if (isNode) NodeMemory else BrowserMemory
}