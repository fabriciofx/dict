/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link Contains}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.UnnecessaryLocalRule", "PMD.UnitTestShouldIncludeAssert"})
final class ContainsTest {
    @Test
    void checkIfADictContainsOther() throws Exception {
        final Dict first = new Dict()
            .with("id", "e576ddbb-d2f1-4bed-88ee-8eb5b6b57ecf")
            .with("client", "Samuel Rosa")
            .with(
                "address",
                new Dict()
                    .with("city", "São Paulo")
                    .with("state", "SP")
                    .with("complement", "Apto 201")
                    .with("number", "123")
                    .with("street", "Rua do Benfica")
                    .with("neighborhood", "Moema")
                    .with("zipcode", "12345678")
            );
        final Dict second = new Dict()
            .with("client", "Samuel Rosa")
            .with(
                "address",
                new Dict()
                    .with("city", "São Paulo")
                    .with("state", "SP")
                    .with("complement", "Apto 201")
                    .with("number", "123")
                    .with("street", "Rua do Benfica")
                    .with("neighborhood", "Moema")
                    .with("zipcode", "12345678")
            );
        new Assertion<>(
            "must contain the second dict in the first one",
            new Contains(first, second).value(),
            new IsTrue()
        ).affirm();
    }
}
