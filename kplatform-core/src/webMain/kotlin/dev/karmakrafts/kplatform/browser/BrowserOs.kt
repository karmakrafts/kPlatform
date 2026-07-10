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

package dev.karmakrafts.kplatform.browser

import dev.karmakrafts.kplatform.Os
import dev.karmakrafts.kplatform.OsFamily
import web.navigator.navigator

internal object BrowserOs : Os {
    override val family: OsFamily by lazy {
        val platform = (if (navigator.isExt) navigator.ext.userAgentData.platform else navigator.platform).lowercase()
        when {
            "win32" in platform || "windows" in platform -> OsFamily.WINDOWS
            "linux" in platform -> OsFamily.LINUX
            "tvos" in platform -> OsFamily.TVOS
            "watchos" in platform -> OsFamily.WATCHOS
            "ios" in platform || "iphone" in platform -> OsFamily.IOS
            "darwin" in platform || "mac" in platform || "osx" in platform -> OsFamily.MACOS
            "freebsd" in platform || "openbsd" in platform -> OsFamily.BSD
            else -> OsFamily.UNKNOWN
        }
    }

    override val name: String? by lazy {
        when (family) {
            OsFamily.UNKNOWN -> null
            else -> family.displayName
        }
    }

    override val version: String? = null

    override val vendor: String? by lazy {
        when {
            family == OsFamily.WINDOWS -> "Microsoft"
            family == OsFamily.ANDROID -> "Google"
            family == OsFamily.LINUX -> "GNU/Linux"
            family.isApple -> "Apple"
            else -> null
        }
    }
}