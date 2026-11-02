/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.xml;

import com.github.fabriciofx.dict.Dict;
import org.cactoos.Text;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * DictAsXml.
 *
 * <p>Convert a dict in XML.</p>
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public final class DictAsXml implements Text {

    /**
     * Dict.
     */
    private final Dict origin;

    /**
     * Ctor.
     *
     * @param dict Dict to be converted in XML
     */
    public DictAsXml(final Dict dict) {
        this.origin = dict;
    }

    @Override
    public String asString() throws Exception {
        try {
            return XmlMapper.builder().build().writeValueAsString(
                JsonMapper.builder().build().readTree(this.origin.asString())
            ).replaceAll("^<ObjectNode>|</ObjectNode>$", "");
        } catch (final JacksonException ex) {
            throw new Exception(ex);
        }
    }
}
