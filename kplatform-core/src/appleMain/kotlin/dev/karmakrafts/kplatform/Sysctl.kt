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

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKStringFromUtf8
import kotlinx.cinterop.value
import platform.darwin.sysctlbyname
import platform.posix.size_tVar

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal object Sysctl {
    const val KERN_OSPRODUCTVERSION: String = "kern.osproductversion"
    const val HW_MEMSIZE: String = "hw.memsize"

    private inline fun <R> MemScope.get(key: String, callback: (buffer: CArrayPointer<ByteVar>) -> R): R? {
        val size = alloc<size_tVar>()
        if (sysctlbyname(key, null, size.ptr, null, 0U) != 0) return null
        val buffer = allocArray<ByteVar>(size.value.toInt())
        if (sysctlbyname(key, buffer, size.ptr, null, 0U) != 0) return null
        return callback(buffer)
    }

    fun string(key: String): String? = memScoped { get(key) { buffer -> buffer.toKStringFromUtf8() } }

    fun uLong(key: String): ULong? = memScoped { get(key) { buffer -> buffer.reinterpret<ULongVar>().pointed.value } }
}