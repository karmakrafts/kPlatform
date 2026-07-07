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
 * Provides information and state about the current runtime.
 */
interface Runtime {
    /**
     * The type of the runtime of the current process.
     */
    val type: RuntimeType

    /**
     * The name of the runtime of the current process.
     */
    val name: String?

    /**
     * The vendor name of the runtime of the current process.
     */
    val vendor: String?

    /**
     * The version of the runtime of the current process.
     */
    val version: String?

    /**
     * Memory information about the current process.
     */
    val memory: Memory

    /**
     * All system environment variables visible to the current process.
     * For platforms that do not have direct access to the environment, this will be empty.
     */
    val environment: Environment
}