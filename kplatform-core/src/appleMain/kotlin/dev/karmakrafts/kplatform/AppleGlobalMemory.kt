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
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.darwin.HOST_VM_INFO64
import platform.darwin.HOST_VM_INFO64_COUNT
import platform.darwin.KERN_SUCCESS
import platform.darwin.host_page_size
import platform.darwin.host_statistics64
import platform.darwin.mach_host_self
import platform.darwin.mach_msg_type_number_tVar
import platform.darwin.vm_size_tVar
import platform.darwin.vm_statistics64_data_t

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal object AppleGlobalMemory : Memory {
    private val pageSize: Long by lazy {
        memScoped {
            val host = mach_host_self()
            val size = alloc<vm_size_tVar>()
            host_page_size(host, size.ptr)
            size.value.toLong()
        }
    }

    override val size: Long by lazy { Sysctl.uLong(Sysctl.HW_MEMSIZE)?.toLong() ?: Memory.UNKNOWN }

    private inline fun <R> withHostStats(block: (vm_statistics64_data_t) -> R): R? = memScoped {
        val host = mach_host_self()
        val stats = alloc<vm_statistics64_data_t>()
        val count = alloc<mach_msg_type_number_tVar> { value = HOST_VM_INFO64_COUNT }
        if (host_statistics64(host, HOST_VM_INFO64, interpretCPointer(stats.rawPtr), count.ptr) != KERN_SUCCESS) {
            return@memScoped null
        }
        return@memScoped block(stats)
    }

    override val available: Long
        get() = withHostStats { stats -> stats.free_count.toLong() * pageSize } ?: Memory.UNKNOWN

    override val used: Long
        get() = withHostStats { stats ->
            (stats.active_count + stats.inactive_count + stats.wire_count + stats.compressor_page_count).toLong() * pageSize
        } ?: Memory.UNKNOWN
}