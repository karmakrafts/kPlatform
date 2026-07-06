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

import dev.karmakrafts.kplatform.Memory.Companion.UNKNOWN


/**
 * Represents information about a pool of memory, either physical or virtual.
 */
interface Memory {
    companion object {
        const val UNKNOWN: Long = -1
    }

    object Unknown : Memory {
        override val size: Long = UNKNOWN
        override val available: Long = UNKNOWN
        override val used: Long = UNKNOWN
    }

    /**
     * The total size of the memory in bytes.
     * [UNKNOWN] if not supported.
     */
    val size: Long

    /**
     * The amount of memory available to be allocated in bytes.
     * [UNKNOWN] if not supported.
     */
    val available: Long

    /**
     * The amount of memory currently in use in bytes.
     * [UNKNOWN] if not supported.
     */
    val used: Long
}