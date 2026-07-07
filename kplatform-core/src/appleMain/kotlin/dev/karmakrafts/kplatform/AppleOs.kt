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
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKStringFromUtf8
import kotlinx.cinterop.value
import platform.darwin.ByteVar
import platform.darwin.sysctlbyname
import platform.posix.size_tVar

internal expect fun getAppleOsFamily(): OsFamily

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
object AppleOs : Os {
    private const val CTL_PRODUCTVERSION: String = "kern.osproductversion"

    override val family: OsFamily get() = getAppleOsFamily()

    override val name: String?
        get() = TODO("Not yet implemented")

    override val version: String? by lazy {
        memScoped {
            val length = alloc<size_tVar>()
            sysctlbyname(CTL_PRODUCTVERSION, null, length.ptr, null, 0U)
            val versionBuffer = allocArray<ByteVar>(length.value.toInt() + 1)
            sysctlbyname(CTL_PRODUCTVERSION, versionBuffer, length.ptr, null, 0U)
            versionBuffer.toKStringFromUtf8()
        }
    }

    override val vendor: String?
        get() = TODO("Not yet implemented")
}