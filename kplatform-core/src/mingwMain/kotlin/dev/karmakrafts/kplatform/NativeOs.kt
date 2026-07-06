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

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.windows.DWORDVar

@OptIn(ExperimentalForeignApi::class)
internal actual fun getKernelVersion(): String? {
    return if (NTDLL.RtlGetNtVersionNumbers == null) null
    else memScoped {
        val major = alloc<DWORDVar>()
        val minor = alloc<DWORDVar>()
        val build = alloc<DWORDVar>()
        NTDLL.RtlGetNtVersionNumbers(major.ptr, minor.ptr, build.ptr)
        "${major.value}.${minor.value}.${build.value and 0xFFFFU}"
    }
}

internal actual fun getKernelVendor(): String? = "Microsoft"