/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.fabriciofx.dict.Dict;
import com.github.fabriciofx.dict.base.DictOf;
import java.io.IOException;

/**
 * JsonToDict.
 *
 * Convert a JSON in dict.
 *
 * @since 0.0.1
 */
public final class JsonToDict extends JsonDeserializer<Dict> {
    @Override
    public Dict deserialize(
        final JsonParser parser,
        final DeserializationContext context
    ) throws IOException {
        return new DictOf(parser.getText());
    }
}
