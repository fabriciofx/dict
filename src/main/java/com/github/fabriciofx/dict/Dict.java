/*
 * SPDX-FileCopyrightText: Copyright (C) 2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Unchecked;

/**
 * Dict.
 *
 * A Python-like dictionary.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
@JsonSerialize(using = DictToJson.class)
@JsonDeserialize(using = JsonToDict.class)
public final class Dict implements Text, Serializable {
    /**
     * Serial for serialization.
     */
    private static final long serialVersionUID = 2_300_063_633_010_907_762L;

    /**
     * Map.
     */
    private final Unchecked<Map<String, Object>> scalar;

    /**
     * Ctor.
     */
    public Dict() {
        this(new HashMap<>());
    }

    /**
     * Ctor.
     * @param json JSON string to convert in dict.
     */
    public Dict(final String json) {
        this(
            () -> {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                try {
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
                } catch (final JsonProcessingException ex) {
                    throw new Exception(ex);
                }
            }
        );
    }

    /**
     * Ctor.
     * @param data Map to convert in dict.
     */
    public Dict(final Map<String, Object> data) {
        this(() -> data);
    }

    /**
     * Ctor.
     * @param scalar Map to convert in dict.
     */
    public Dict(final Scalar<Map<String, Object>> scalar) {
        this(new Unchecked<>(scalar));
    }

    /**
     * Ctor.
     * @param scalar Map to convert in dict.
     */
    public Dict(final Unchecked<Map<String, Object>> scalar) {
        this.scalar = scalar;
    }

    /**
     * Dict keys.
     *
     * @return A set of dict keys.
     */
    public Set<String> keys() {
        return this.scalar.value().keySet();
    }

    /**
     * Dict values.
     *
     * @return A collection of dict values.
     */
    public Collection<Object> values() {
        return this.scalar.value().values();
    }

    /**
     * Count.
     *
     * @return Amount of dict entries.
     */
    public int count() {
        return this.scalar.value().size();
    }

    /**
     * Value.
     * @param key The key of value
     * @param type The type of value
     * @param <T> Value type
     * @return The value
     */
    public <T> T value(final String key, final Class<T> type) {
        return type.cast(this.scalar.value().get(key));
    }

    /**
     * Build a dict.
     *
     * @param key The key of value.
     * @param value The value to store in dict.
     * @return A new dict with the key and value stored.
     */
    public Dict with(final String key, final Object value) {
        final Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        map.putAll(this.scalar.value());
        return new Dict(map);
    }

    @Override
    public boolean equals(final Object dict) {
        return dict instanceof Dict
            && Dict.class.cast(dict).scalar.value().equals(this.scalar.value());
    }

    @Override
    public int hashCode() {
        return this.scalar.value().hashCode();
    }

    @Override
    public String asString() throws Exception {
        try {
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
        } catch (final JsonProcessingException ex) {
            throw new Exception(ex);
        }
    }
}
