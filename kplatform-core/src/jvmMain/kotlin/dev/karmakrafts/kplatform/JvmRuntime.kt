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

internal object JvmRuntime : Runtime {
    override val type: RuntimeType = RuntimeType.JVM

    override val name: String? by lazy {
        System.getProperty("java.runtime.name") ?: System.getProperty("java.vm.name")
    }

    override val vendor: String? by lazy {
        System.getProperty("java.vendor") ?: System.getProperty("java.vm.vendor")
    }

    override val version: String? by lazy {
        System.getProperty("java.runtime.version") ?: System.getProperty("java.vm.version")
    }

    override val memory: Memory get() = OshiRuntimeMemory
    override val environment: Environment = Environment(System.getenv())
}