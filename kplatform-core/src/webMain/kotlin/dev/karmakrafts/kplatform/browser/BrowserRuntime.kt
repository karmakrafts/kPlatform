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

package dev.karmakrafts.kplatform.browser

import dev.karmakrafts.kplatform.Environment
import dev.karmakrafts.kplatform.Memory
import dev.karmakrafts.kplatform.Runtime
import dev.karmakrafts.kplatform.RuntimeType
import web.navigator.navigator

internal object BrowserRuntime : Runtime {
    private val versionPattern: Regex by lazy { Regex("""([\d.]+)""") }

    override val type: RuntimeType get() = RuntimeType.BROWSER

    override val name: String by lazy {
        val agent = navigator.userAgent.lowercase()
        when {
            "opr" in agent -> "Opera"
            "edge" in agent -> "Edge"
            "chrome" in agent -> "Chrome"
            "firefox" in agent -> "Firefox"
            "ladybird" in agent -> "Ladybird"
            "safari" in agent && "chrome" !in agent -> "Safari"
            else -> "Unknown"
        }
    }

    override val version: String by lazy {
        val agent = navigator.userAgent.lowercase()
        val result = versionPattern.find(agent)?.groupValues ?: return@lazy "Unknown"
        if (result.isEmpty()) return@lazy "Unknown"
        result[1]
    }

    override val vendor: String by lazy {
        val agent = navigator.userAgent.lowercase()
        when {
            "opr" in agent -> "Opera"
            "edge" in agent -> "Microsoft"
            "chrome" in agent -> "Google"
            "firefox" in agent -> "Mozilla"
            "ladybird" in agent -> "Ladybird"
            "safari" in agent && "chrome" !in agent -> "Apple"
            else -> "Unknown"
        }
    }

    override val memory: Memory get() = BrowserMemory
    override val environment: Environment get() = Environment.empty
}