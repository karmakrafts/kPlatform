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

import platform.posix.android_get_device_api_level

internal object AndroidOs : Os {
    override val family: OsFamily get() = OsFamily.ANDROID
    override val name: String = "Android"

    override val version: String? by lazy {
        when (android_get_device_api_level()) {
            24 -> "7.0" // Lowest we support
            25 -> "7.1"
            26 -> "8.0"
            27 -> "8.1"
            28 -> "9.0"
            29 -> "10.0"
            30 -> "11.0"
            31, 32 -> "12.0"
            33 -> "13.0"
            34 -> "14.0"
            35 -> "15.0"
            36 -> "16.0"
            37 -> "17.0"
            38 -> "18.0"
            else -> null
        }
    }

    override val vendor: String = "Google"
}