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

/**
 * Represents a process environment as a map of variable names to values.
 *
 * @param vars The backing map of environment variable names and values.
 */
@JvmInline
value class Environment internal constructor(val vars: Map<String, String?>) {
    companion object {
        /**
         * An empty environment with no variables.
         */
        val empty: Environment = Environment(emptyMap())

        internal inline fun build(builder: HashMap<String, String?>.() -> Unit): Environment {
            val vars = HashMap<String, String?>()
            vars.builder()
            return Environment(vars)
        }
    }

    /**
     * Returns the value of the environment variable with the given key.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun get(key: String): String? = vars[key]

    /**
     * Returns the value of the environment variable with the given key or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is not present.
     */
    inline operator fun get(key: String, default: () -> String): String = vars[key] ?: default()

    /**
     * Checks whether an environment variable with the given key exists.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun contains(key: String): Boolean = key in vars

    /**
     * Returns the value of the given environment variable as a boolean, or `null` when absent or invalid.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun getBooleanOrNull(key: String): Boolean? = vars[key]?.toBooleanStrictOrNull()

    /**
     * Returns the value of the given environment variable as an integer, or `null` when absent or invalid.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun getIntOrNull(key: String): Int? = vars[key]?.toIntOrNull()

    /**
     * Returns the value of the given environment variable as a long integer, or `null` when absent or invalid.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun getLongOrNull(key: String): Long? = vars[key]?.toLongOrNull()

    /**
     * Returns the value of the given environment variable as a float, or `null` when absent or invalid.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun getFloatOrNull(key: String): Float? = vars[key]?.toFloatOrNull()

    /**
     * Returns the value of the given environment variable as a double, or `null` when absent or invalid.
     *
     * @param key The variable name.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun getDoubleOrNull(key: String): Double? = vars[key]?.toDoubleOrNull()

    /**
     * Returns the enum entry whose name matches the value of the given environment variable.
     *
     * @param E The enum type.
     * @param key The variable name.
     * @param entries The enum entries to search.
     */
    fun <E : Enum<E>> getEnumOrNull(key: String, entries: EnumEntries<E>): E? {
        val entryName = vars[key] ?: return null
        return entries.find { entry -> entry.name == entryName }
    }

    /**
     * Returns the value of the given environment variable as a boolean, or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is absent or invalid.
     */
    inline fun getBoolean(key: String, default: () -> Boolean): Boolean = getBooleanOrNull(key) ?: default()

    /**
     * Returns the value of the given environment variable as an integer, or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is absent or invalid.
     */
    inline fun getInt(key: String, default: () -> Int): Int = getIntOrNull(key) ?: default()

    /**
     * Returns the value of the given environment variable as a long integer, or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is absent or invalid.
     */
    inline fun getLong(key: String, default: () -> Long): Long = getLongOrNull(key) ?: default()

    /**
     * Returns the value of the given environment variable as a float, or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is absent or invalid.
     */
    inline fun getFloat(key: String, default: () -> Float): Float = getFloatOrNull(key) ?: default()

    /**
     * Returns the value of the given environment variable as a double, or a fallback value.
     *
     * @param key The variable name.
     * @param default Supplies the fallback value when the variable is absent or invalid.
     */
    inline fun getDouble(key: String, default: () -> Double): Double = getDoubleOrNull(key) ?: default()

    /**
     * Returns the enum entry matching the value of the given environment variable, or a fallback value.
     *
     * @param E The enum type.
     * @param key The variable name.
     * @param entries The enum entries to search.
     * @param default Supplies the fallback entry when no matching value exists.
     */
    inline fun <E : Enum<E>> getEnum(key: String, entries: EnumEntries<E>, default: () -> E): E =
        getEnumOrNull(key, entries) ?: default()
}