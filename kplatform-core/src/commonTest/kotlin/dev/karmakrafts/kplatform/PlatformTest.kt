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

import kotlin.test.Test
import kotlin.test.assertTrue

class PlatformTest {
    @Test
    fun `Memory size is not 0 when known`() {
        val size = Platform.memory.size
        if (size == Memory.UNKNOWN) return
        assertTrue(size > 0)
        println("Memory size: ${size / 1024 / 1024}MiB")
    }

    @Test
    fun `Available memory is not 0 when known`() {
        val available = Platform.memory.available
        if (available == Memory.UNKNOWN) return
        assertTrue(available > 0)
        println("Memory available: ${available / 1024 / 1024}MiB")
    }

    @Test
    fun `Used memory is not 0 when known`() {
        val used = Platform.memory.used
        if (used == Memory.UNKNOWN) return
        assertTrue(used > 0)
        println("Memory used: ${used / 1024 / 1024}MiB")
    }
}