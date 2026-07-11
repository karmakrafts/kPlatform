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

import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import platform.posix.atexit
import platform.windows.CloseHandle
import platform.windows.DWORDVar
import platform.windows.GetProcAddress
import platform.windows.HMODULE
import platform.windows.LoadLibraryW

internal object NTDLL {
    // See https://www.geoffchappell.com/studies/windows/win32/ntdll/api/ldrinit/getntversionnumbers.htm?tx=45,52,56,71,76,84;50
    private typealias _RtlGetNtVersionNumbers = (major: CPointer<DWORDVar>, minor: CPointer<DWORDVar>, build: CPointer<DWORDVar>) -> Unit

    private val ntdllModule: HMODULE? = LoadLibraryW("NTDLL.dll")

    val RtlGetNtVersionNumbers: CPointer<CFunction<_RtlGetNtVersionNumbers>>? = ntdllModule?.let { module ->
        GetProcAddress(module, "RtlGetNtVersionNumbers")?.reinterpret()
    }

    init {
        atexit(staticCFunction<Unit> {
            val self = NTDLL
            self.ntdllModule?.let(::CloseHandle)
        })
    }
}