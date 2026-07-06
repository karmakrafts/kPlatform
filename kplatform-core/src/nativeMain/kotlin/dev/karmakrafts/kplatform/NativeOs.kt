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

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.OsFamily as KOsFamily
import kotlin.native.Platform as KPlatform

internal expect fun getKernelVersion(): String?
internal expect fun getKernelVendor(): String?

@OptIn(ExperimentalNativeApi::class)
internal object NativeOs : Os {
    override val family: OsFamily by lazy {
        when (KPlatform.osFamily) {
            KOsFamily.WINDOWS -> OsFamily.WINDOWS
            KOsFamily.LINUX -> OsFamily.LINUX
            KOsFamily.MACOSX -> OsFamily.MACOS
            KOsFamily.IOS -> OsFamily.IOS
            KOsFamily.WATCHOS -> OsFamily.WATCHOS
            KOsFamily.TVOS -> OsFamily.TVOS
            KOsFamily.ANDROID -> OsFamily.ANDROID
            else -> OsFamily.UNKNOWN
        }
    }

    override val name: String by lazy(family::displayName)
    override val version: String by lazy { getKernelVersion() ?: "Unknown" }
    override val vendor: String by lazy { getKernelVendor() ?: "Unknown" }
}