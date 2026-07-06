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

import kotlin.enums.EnumEntries
import kotlin.jvm.JvmInline

@JvmInline
value class Environment internal constructor(val vars: Map<String, String?>) {
    companion object {
        val empty: Environment = Environment(emptyMap())

        internal inline fun build(builder: HashMap<String, String?>.() -> Unit): Environment {
            val vars = HashMap<String, String?>()
            vars.builder()
            return Environment(vars)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun get(key: String): String? = vars[key]

    inline operator fun get(key: String, default: () -> String): String = vars[key] ?: default()

    @Suppress("NOTHING_TO_INLINE")
    inline fun getBooleanOrNull(key: String): Boolean? = vars[key]?.toBooleanStrictOrNull()

    @Suppress("NOTHING_TO_INLINE")
    inline fun getIntOrNull(key: String): Int? = vars[key]?.toIntOrNull()

    @Suppress("NOTHING_TO_INLINE")
    inline fun getLongOrNull(key: String): Long? = vars[key]?.toLongOrNull()

    @Suppress("NOTHING_TO_INLINE")
    inline fun getFloatOrNull(key: String): Float? = vars[key]?.toFloatOrNull()

    @Suppress("NOTHING_TO_INLINE")
    inline fun getDoubleOrNull(key: String): Double? = vars[key]?.toDoubleOrNull()

    fun <E : Enum<E>> getEnumOrNull(key: String, entries: EnumEntries<E>): E? {
        val entryName = vars[key] ?: return null
        return entries.find { entry -> entry.name == entryName }
    }

    inline fun getBoolean(key: String, default: () -> Boolean): Boolean = getBooleanOrNull(key) ?: default()
    inline fun getInt(key: String, default: () -> Int): Int = getIntOrNull(key) ?: default()
    inline fun getLong(key: String, default: () -> Long): Long = getLongOrNull(key) ?: default()
    inline fun getFloat(key: String, default: () -> Float): Float = getFloatOrNull(key) ?: default()
    inline fun getDouble(key: String, default: () -> Double): Double = getDoubleOrNull(key) ?: default()

    inline fun <E : Enum<E>> getEnum(key: String, entries: EnumEntries<E>, default: () -> E): E =
        getEnumOrNull(key, entries) ?: default()
}