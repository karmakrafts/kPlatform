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

class OsTest {
    @Test
    fun `Family is not unknown`() {
        val family = Platform.os.family
        assertNotEquals(OsFamily.UNKNOWN, family)
        println("OS Family: $family")
    }

    @Test
    fun `Name is not empty`() {
        val name = Platform.os.name
        assertNotNull(name)
        println("OS Name: $name")
    }

    @Test
    fun `Vendor is not empty`() {
        val vendor = Platform.os.vendor
        assertNotNull(vendor)
        println("OS Vendor: $vendor")
    }

    @Test
    fun `Version is not empty`() {
        val version = Platform.os.version
        assertNotNull(version)
        println("OS Version: $version")
    }
}