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
 *
 * <p>A Python-like dictionary for Java.</p>
 *
 * @since 0.0.1
 */
public interface Dict extends Text {

    /**
     * Dict keys.
     *
     * @return A set of dict keys
     * @throws DictException If dict content cannot be read
     */
    Set<String> keys() throws DictException;

    /**
     * Dict values.
     *
     * @return A collection of dict values
     * @throws DictException If dict content cannot be read
     */
    Collection<Object> values() throws DictException;

    /**
     * Count.
     *
     * @return Amount of dict entries
     * @throws DictException If dict content cannot be read
     */
    int count() throws DictException;

    /**
     * Value.
     *
     * @param key The key of value
     * @param type The type of value
     * @param <T> Value type
     * @return The value
     * @throws DictException If dict content cannot be read or value has
     *  another type
     */
    <T> T value(String key, Class<T> type) throws DictException;

    /**
     * Build a dict.
     *
     * @param key The key of value
     * @param value The value to store in dict
     * @return A new dict with the key and value stored
     * @throws DictException If dict content cannot be read
     */
    Dict with(String key, Object value) throws DictException;

    @Override
    String asString() throws DictException;
}
