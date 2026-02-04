/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import java.util.Collection;
import java.util.Set;
import org.cactoos.Text;

/**
 * Dict.
 * <p> A Python-like dictionary for Java.
 * @since 0.0.1
 */
public interface Dict extends Text {
    /**
     * Dict keys.
     * @return A set of dict keys.
     */
    Set<String> keys();

    /**
     * Dict values.
     * @return A collection of dict values.
     */
    Collection<Object> values();

    /**
     * Count.
     * @return Amount of dict entries.
     */
    int count();

    /**
     * Value.
     * @param key The key of value
     * @param type The type of value
     * @param <T> Value type
     * @return The value
     */
    <T> T value(String key, Class<T> type);

    /**
     * Build a dict.
     * @param key The key of value.
     * @param value The value to store in dict.
     * @return A new dict with the key and value stored.
     */
    Dict with(String key, Object value);
}
