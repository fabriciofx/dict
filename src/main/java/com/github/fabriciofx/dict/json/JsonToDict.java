/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.json;

import com.github.fabriciofx.dict.Dict;
import com.github.fabriciofx.dict.base.DictOf;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * JsonToDict.
 *
 * <p>Convert a JSON in dict.</p>
 *
 * @since 0.0.1
 */
public final class JsonToDict extends ValueDeserializer<Dict> {

    /**
     * Ctor.
     */
    public JsonToDict() {
        super();
    }

    @Override
    public Dict deserialize(
        final JsonParser parser,
        final DeserializationContext context
    ) {
        return new DictOf(parser.getString());
    }
}
