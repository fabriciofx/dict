/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * DictToJson.
 *
 * Convert a dict in JSON.
 *
 * @since 0.0.1
 */
public final class DictToJson extends JsonSerializer<Dict> {
    @Override
    public void serialize(
        final Dict dict,
        final JsonGenerator generator,
        final SerializerProvider provider
    ) throws IOException {
        generator.writeStartObject();
        for (final String key : dict.keys()) {
            generator.writeObjectField(key, dict.value(key, Object.class));
        }
        generator.writeEndObject();
    }
}
