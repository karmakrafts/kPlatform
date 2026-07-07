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
import kotlin.test.assertNotNull

class RuntimeTest {
    @Test
    fun `Name is not empty`() {
        val name = Platform.runtime.name
        assertNotNull(name)
        println("RT name: $name")
    }

    @Test
    fun `Vendor is not empty`() {
        val vendor = Platform.runtime.vendor
        assertNotNull(vendor)
        println("RT vendor: $vendor")
    }

    @Test
    fun `Version is not empty`() {
        val version = Platform.runtime.version
        assertNotNull(version)
        println("RT version: $version")
    }

    @Test
    fun `Memory size is not unknown`() {
        val size = Platform.runtime.memory.size
        assertNotEquals(Memory.UNKNOWN, size)
        println("RT memory size: ${size / 1024 / 1024}MiB")
    }

    @Test
    fun `Available memory is not unknown`() {
        val size = Platform.runtime.memory.available
        assertNotEquals(Memory.UNKNOWN, size)
        println("RT memory available: ${size / 1024 / 1024}MiB")
    }

    @Test
    fun `Used memory is not unknown`() {
        val size = Platform.runtime.memory.used
        assertNotEquals(Memory.UNKNOWN, size)
        println("RT memory used: ${size / 1024 / 1024}MiB")
    }
}