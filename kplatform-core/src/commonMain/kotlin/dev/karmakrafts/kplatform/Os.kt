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

/**
 * Provides information about the operating system.
 */
interface Os {
    /**
     * The family which the current operating system belongs to.
     */
    val family: OsFamily

    /**
     * The full name of the current operating system.
     */
    val name: String?

    /**
     * The version of the current operating system (or its kernel).
     */
    val version: String?

    /**
     * The vendor name of the current operating system.
     */
    val vendor: String?
}