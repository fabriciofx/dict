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
 * Test case for {@link DictToJson}.
 *
 * @since 0.0.43
 */
final class DictToJsonTest {

    @Test
    void writesDictAsJsonObject() throws Exception {
        new Assertion<>(
            "must not write dict other than as a JSON object",
            JsonMapper.builder().build().writeValueAsString(
                new DictOf().with("kq7", "zéta")
            ),
            new IsEqual<>("{\"kq7\":\"zéta\"}")
        ).affirm();
    }
}
