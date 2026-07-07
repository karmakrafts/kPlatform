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

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKStringFromUtf8
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread
import platform.posix.fseek
import platform.posix.ftell

@OptIn(ExperimentalForeignApi::class)
internal object LinuxOs : Os {
    private val osRelease: Map<String, String?> by lazy {
        memScoped {
            val file = fopen("/etc/os-release", "rb")
            fseek(file, 0, SEEK_END)
            val size = ftell(file)
            fseek(file, 0, SEEK_SET)
            val buffer = allocArray<ByteVar>(size + 1) // Ensure trailing null byte so string conversion works
            if (fread(buffer, 1U, size.toULong(), file).toLong() != size) return@memScoped emptyMap()
            fclose(file)
            buffer.toKStringFromUtf8().split("\n").associate { line ->
                val splitLine = line.split("=", limit = 2)
                if (splitLine.size == 1) splitLine[0] to null
                else {
                    var value = splitLine[1]
                    if (value.startsWith('"') && value.endsWith('"')) value = value.substring(1..<value.lastIndex)
                    splitLine[0] to value
                }
            }
        }
    }

    override val family: OsFamily get() = OsFamily.LINUX
    override val name: String? by lazy { osRelease["NAME"] }
    override val version: String? by lazy { osRelease["VERSION"] }
    override val vendor: String = "Linux/GNU"
}