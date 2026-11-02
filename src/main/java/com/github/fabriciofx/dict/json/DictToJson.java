/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.json;

import com.github.fabriciofx.dict.Dict;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * DictToJson.
 *
 * <p>Convert a dict in JSON.</p>
 *
 * @since 0.0.1
 */
public final class DictToJson extends ValueSerializer<Dict> {

    /**
     * Ctor.
     */
    public DictToJson() {
        super();
    }

    @Override
    public void serialize(
        final Dict dict,
        final JsonGenerator generator,
        final SerializationContext context
    ) {
        generator.writeStartObject();
        for (final String key : dict.keys()) {
            generator.writePOJOProperty(key, dict.value(key, Object.class));
        }
        generator.writeEndObject();
    }
}
