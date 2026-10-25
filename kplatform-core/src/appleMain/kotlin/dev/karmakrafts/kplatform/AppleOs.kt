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

internal expect val osFamily: OsFamily

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
public object AppleOs : Os {
    override val family: OsFamily get() = osFamily

    override val name: String? by lazy {
        when (family) {
            OsFamily.UNKNOWN -> null
            else -> family.displayName
        }
    }

    override val version: String? by lazy { Sysctl.string(Sysctl.KERN_OSPRODUCTVERSION) }
    override val vendor: String = "Apple"
}