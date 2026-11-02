/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.json;

import com.github.fabriciofx.dict.base.DictOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import tools.jackson.databind.json.JsonMapper;

/**
 * Test case for {@link JsonToDict}.
 *
 * @since 0.0.39
 */
final class JsonToDictTest {

    @Test
    void readsDictFromJsonString() throws Exception {
        new Assertion<>(
            "must not read dict other than from its JSON string",
            JsonMapper.builder().build().readValue(
                "\"{\\\"w3x\\\":\\\"ômega\\\"}\"",
                DictOf.class
            ),
            new IsEqual<>(new DictOf().with("w3x", "ômega"))
        ).affirm();
    }
}
