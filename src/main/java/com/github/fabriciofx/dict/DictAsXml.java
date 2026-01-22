/*
 * SPDX-FileCopyrightText: Copyright (C) 2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.cactoos.Text;

/**
 * DictAsXml.
 *
 * Convert a dict in XML.
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
     * @param dict Dict to be converted in XML
     */
    public DictAsXml(final Dict dict) {
        this.origin = dict;
    }

    @Override
    public String asString() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        final XmlMapper xml = new XmlMapper();
        xml.registerModule(new JavaTimeModule());
        try {
            return xml.writeValueAsString(
                mapper.readTree(this.origin.asString())
            ).replaceAll("^<ObjectNode>|</ObjectNode>$", "");
        } catch (final JsonProcessingException ex) {
            throw new Exception(ex);
        }
    }
}
