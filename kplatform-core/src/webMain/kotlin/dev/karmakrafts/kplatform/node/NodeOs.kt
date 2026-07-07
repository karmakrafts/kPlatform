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

package dev.karmakrafts.kplatform.node

import dev.karmakrafts.kplatform.Os
import dev.karmakrafts.kplatform.OsFamily

internal object NodeOs : Os {
    override val family: OsFamily by lazy {
        val platform = os.platform()?.lowercase() ?: return@lazy OsFamily.UNKNOWN
        when {
            "win32" in platform || "windows" in platform -> OsFamily.WINDOWS
            "linux" in platform -> OsFamily.LINUX
            "darwin" in platform || "macos" in platform -> OsFamily.MACOS
            "bsd" in platform -> OsFamily.BSD
            "android" in platform -> OsFamily.ANDROID
            else -> OsFamily.UNKNOWN
        }
    }

    override val name: String? get() = os.type()
    override val version: String? get() = os.release()

    override val vendor: String? by lazy {
        when {
            family == OsFamily.WINDOWS -> "Microsoft"
            family == OsFamily.ANDROID -> "Google"
            family.isApple -> "Apple"
            else -> null
        }
    }
}