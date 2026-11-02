/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.base;

import com.github.fabriciofx.dict.Dict;
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
        return JsonMapper.builder().addModule(module).build()
            .writeValueAsString(this.scalar.value());
    }
}
