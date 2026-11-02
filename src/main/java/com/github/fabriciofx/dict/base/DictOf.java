/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.base;

import com.github.fabriciofx.dict.Dict;
import com.github.fabriciofx.dict.DictException;
import com.github.fabriciofx.dict.json.DictToJson;
import com.github.fabriciofx.dict.json.JsonToDict;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

/**
 * Dict.
 *
 * <p>A Python-like dictionary for Java.</p>
 *
 * @since 0.0.1
 */
@JsonSerialize(using = DictToJson.class)
@JsonDeserialize(using = JsonToDict.class)
public final class DictOf implements Dict {

    /**
     * Map.
     */
    private final Unchecked<Map<String, Object>> scalar;

    /**
     * Ctor.
     */
    public DictOf() {
        this(new HashMap<>());
    }

    /**
     * Ctor.
     *
     * @param json JSON string to convert in dict
     */
    public DictOf(final String json) {
        this(
            () -> {
                final String content;
                if (json.isEmpty()) {
                    content = "{}";
                } else {
                    content = json;
                }
                return JsonMapper.builder().build().readValue(
                    content,
                    new TypeReference<>() {
                    }
                );
            }
        );
    }

    /**
     * Ctor.
     *
     * @param data Map to convert in dict
     */
    public DictOf(final Map<String, Object> data) {
        this(() -> data);
    }

    /**
     * Ctor.
     *
     * @param scalar Map to convert in dict
     */
    public DictOf(final Scalar<Map<String, Object>> scalar) {
        this(new Unchecked<>(scalar));
    }

    /**
     * Ctor.
     *
     * @param scalar Map to convert in dict
     */
    public DictOf(final Unchecked<Map<String, Object>> scalar) {
        this.scalar = scalar;
    }

    @Override
    public Set<String> keys() throws DictException {
        return this.entries().keySet();
    }

    @Override
    public Collection<Object> values() throws DictException {
        return this.entries().values();
    }

    @Override
    public int count() throws DictException {
        return this.entries().size();
    }

    @Override
    public <T> T value(final String key, final Class<T> type)
        throws DictException {
        try {
            return type.cast(this.entries().get(key));
        } catch (final ClassCastException ex) {
            throw new DictException(
                String.format(
                    "Cannot read value of key '%s' as %s",
                    key,
                    type.getName()
                ),
                ex
            );
        }
    }

    @Override
    public Dict with(final String key, final Object value)
        throws DictException {
        final Map<String, Object> map = new HashMap<>(this.entries());
        map.put(key, value);
        return new DictOf(map);
    }

    @Override
    public boolean equals(final Object dict) {
        return this == dict
            || dict instanceof DictOf
            && DictOf.class.cast(dict).scalar.value().equals(
                this.scalar.value()
        );
    }

    @Override
    public int hashCode() {
        return this.scalar.value().hashCode();
    }

    @Override
    public String asString() throws DictException {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(
            LocalDate.class,
            new LocalDateSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        );
        module.addSerializer(
            LocalDateTime.class,
            new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )
        );
        try {
            return JsonMapper.builder().addModule(module).build()
                .writeValueAsString(this.entries());
        } catch (final JacksonException ex) {
            throw new DictException("Cannot write dict as JSON", ex);
        }
    }

    private Map<String, Object> entries() throws DictException {
        try {
            return this.scalar.value();
        } catch (final JacksonException ex) {
            throw new DictException("Cannot parse dict from JSON", ex);
        }
    }
}
