/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

/**
 * Dict.
 * <p> A Python-like dictionary for Java.
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
     * @param json JSON string to convert in dict.
     */
    public DictOf(final String json) {
        this(
            () -> {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                final String content;
                if (json.isEmpty()) {
                    content = "{}";
                } else {
                    content = json;
                }
                return mapper.readValue(
                    content,
                    new TypeReference<>() {
                    }
                );
            }
        );
    }

    /**
     * Ctor.
     * @param data Map to convert in dict.
     */
    public DictOf(final Map<String, Object> data) {
        this(() -> data);
    }

    /**
     * Ctor.
     * @param scalar Map to convert in dict.
     */
    public DictOf(final Scalar<Map<String, Object>> scalar) {
        this(new Unchecked<>(scalar));
    }

    /**
     * Ctor.
     * @param scalar Map to convert in dict.
     */
    public DictOf(final Unchecked<Map<String, Object>> scalar) {
        this.scalar = scalar;
    }

    @Override
    public Set<String> keys() {
        return this.scalar.value().keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.scalar.value().values();
    }

    @Override
    public int count() {
        return this.scalar.value().size();
    }

    @Override
    public <T> T value(final String key, final Class<T> type) {
        return type.cast(this.scalar.value().get(key));
    }

    @Override
    public Dict with(final String key, final Object value) {
        final Map<String, Object> map = new HashMap<>(
            this.scalar.value()
        );
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
    public String asString() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule module = new JavaTimeModule();
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
        mapper.registerModule(module);
        return mapper.writeValueAsString(this.scalar.value());
    }
}
