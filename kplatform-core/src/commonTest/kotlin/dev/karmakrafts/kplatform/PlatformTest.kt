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
import kotlin.test.assertNotEquals

class PlatformTest {
    @Test
    fun `Memory size is not unknown`() {
        val size = Platform.memory.size
        assertNotEquals(Memory.UNKNOWN, size)
        println("Memory size: ${size / 1024 / 1024}MiB")
    }

    @Test
    fun `Available memory is not unknown`() {
        val size = Platform.memory.available
        assertNotEquals(Memory.UNKNOWN, size)
        println("Memory available: ${size / 1024 / 1024}MiB")
    }

    @Test
    fun `Used memory is not unknown`() {
        val size = Platform.memory.used
        assertNotEquals(Memory.UNKNOWN, size)
        println("Memory used: ${size / 1024 / 1024}MiB")
    }
}