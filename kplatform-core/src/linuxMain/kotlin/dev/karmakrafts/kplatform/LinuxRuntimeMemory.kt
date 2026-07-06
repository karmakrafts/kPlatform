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
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.linux.RUSAGE_SELF
import platform.linux.getrusage
import platform.linux.rusage

@OptIn(ExperimentalForeignApi::class)
internal object LinuxRuntimeMemory : Memory by LinuxGlobalMemory {
    override val used: Long
        get() = memScoped {
            val usage = alloc<rusage>()
            getrusage(RUSAGE_SELF, usage.ptr)
            usage.ru_maxrss * 1000L // This is reported is kB
        }
}