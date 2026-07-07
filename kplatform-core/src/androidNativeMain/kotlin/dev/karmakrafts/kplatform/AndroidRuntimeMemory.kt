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
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.posix._SC_PAGESIZE
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fscanf
import platform.posix.size_tVar
import platform.posix.sysconf

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
internal object AndroidRuntimeMemory : Memory by AndroidGlobalMemory {
    private val pageSize: Long by lazy { sysconf(_SC_PAGESIZE).toLong() }

    override val used: Long
        get() = memScoped {
            val file = fopen("/proc/self/statm", "r")
            val sizePages = alloc<size_tVar>()
            val residentPages = alloc<size_tVar>()
            if (fscanf(file, "%lu %lu", sizePages.ptr, residentPages.ptr) != 2) return@memScoped Memory.UNKNOWN
            fclose(file)
            residentPages.value.toLong() * pageSize
        }
}