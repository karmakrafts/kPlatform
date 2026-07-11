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
import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.NativePtr
import kotlinx.cinterop.alignOf
import kotlinx.cinterop.memberAt
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.value
import platform.posix.atexit
import platform.windows.BOOL
import platform.windows.CloseHandle
import platform.windows.DWORD
import platform.windows.DWORDVar
import platform.windows.GetProcAddress
import platform.windows.HANDLE
import platform.windows.HMODULE
import platform.windows.LoadLibraryW
import platform.windows.SIZE_T
import platform.windows.SIZE_TVar

internal object PsApi {
    // See https://learn.microsoft.com/en-us/windows/win32/api/psapi/ns-psapi-process_memory_counters_ex
    class ProcessMemoryCountersEx(rawPtr: NativePtr) : CStructVar(rawPtr) {
        @Suppress("DEPRECATION")
        companion object : Type(sizeOf<DWORDVar>() * 2 + sizeOf<SIZE_TVar>() * 9, alignOf<SIZE_TVar>())

        var privateUsage: SIZE_T
            get() = memberAt<SIZE_TVar>(sizeOf<DWORDVar>() * 2 + sizeOf<SIZE_TVar>() * 8).value
            set(value) {
                memberAt<SIZE_TVar>(sizeOf<DWORDVar>() * 2 + sizeOf<SIZE_TVar>() * 8).value = value
            }
    }

    // See https://learn.microsoft.com/en-us/windows/win32/api/psapi/nf-psapi-getprocessmemoryinfo
    private typealias _GetProcessMemoryInfo = (process: HANDLE, memCounters: CPointer<ProcessMemoryCountersEx>, cb: DWORD) -> BOOL

    private val psApiModule: HMODULE? = LoadLibraryW("PsApi.dll") ?: LoadLibraryW("Kernel32.dll")

    val GetProcessMemoryInfo: CPointer<CFunction<_GetProcessMemoryInfo>>? = psApiModule?.let { module ->
        GetProcAddress(module, "GetProcessMemoryInfo")?.reinterpret()
    }

    init {
        atexit(staticCFunction<Unit> {
            val self = PsApi
            self.psApiModule?.let(::CloseHandle)
        })
    }
}