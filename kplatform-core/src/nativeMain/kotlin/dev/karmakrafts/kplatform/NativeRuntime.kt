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

@file:OptIn(ExperimentalForeignApi::class)

package dev.karmakrafts.kplatform

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.toKStringFromUtf8

internal abstract class NativeRuntime : Runtime {
    override val type: RuntimeType get() = RuntimeType.NATIVE
    override val name: String = "Kotlin/Native"
    override val version: String = "2.4.0"
    override val vendor: String = "JetBrains"

    abstract fun getEnvironmentAddress(): CPointer<CPointerVar<ByteVar>>?

    override val environment: Environment by lazy {
        var envAddr = getEnvironmentAddress() ?: return@lazy Environment.empty
        Environment.build {
            // Environment array is null-terminated itself
            var index = 0
            var envVar = envAddr[index++]
            while (envVar != null) {
                val varString = envVar.toKStringFromUtf8()
                val splitVar = varString.split("=")
                if (splitVar.size == 1) this[splitVar[0]] = null
                else this[splitVar[0]] = splitVar[1]
                envVar = envAddr[index++]
            }
        }
    }
}