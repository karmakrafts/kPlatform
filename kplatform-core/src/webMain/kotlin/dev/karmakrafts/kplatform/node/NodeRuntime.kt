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

import dev.karmakrafts.kplatform.Environment
import dev.karmakrafts.kplatform.Memory
import dev.karmakrafts.kplatform.Runtime
import dev.karmakrafts.kplatform.RuntimeType
import js.array.component1
import js.array.component2
import js.array.iterator
import js.objects.Object
import js.string.JsStrings.toKotlinString

internal object NodeRuntime : Runtime {
    override val type: RuntimeType get() = RuntimeType.NODEJS
    override val name: String = "NodeJS"
    override val vendor: String = "NodeJS"
    override val version: String get() = process.versions.node
    override val memory: Memory get() = NodeMemory

    override val environment: Environment by lazy {
        Environment.build {
            for ((key, value) in Object.entries(process.env)) {
                this[key.toKotlinString()] = value?.toString()
            }
        }
    }
}