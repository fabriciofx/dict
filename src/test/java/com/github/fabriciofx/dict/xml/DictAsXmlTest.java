/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict.xml;

import com.github.fabriciofx.dict.Dict;
import com.github.fabriciofx.dict.base.DictOf;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link DictAsXml}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.UnnecessaryLocalRule", "PMD.UnitTestShouldIncludeAssert"})
final class DictAsXmlTest {
    @Test
    void convertDictToXml() throws Exception {
        final Dict dict = new DictOf()
            .with("one", "One")
            .with("two", 1.0)
            .with("three", new BigDecimal("3.14"))
            .with("four", 4)
            .with("five", LocalDate.of(2025, 5, 6))
            .with("six", LocalDateTime.of(2025, 5, 6, 14, 23, 52))
            .with(
                "seven",
                new DictOf()
                    .with("eight", "Eight")
                    .with("nine", 9.0)
            );
        new Assertion<>(
            "must convert Dict to XML correctly",
            new DictAsXml(dict),
            new IsText(
                """
                <six>2025-05-06 14:23:52</six><four>4</four><one>One</one>\
                <seven><nine>9.0</nine><eight>Eight</eight></seven>\
                <two>1.0</two><three>3.14</three><five>2025-05-06</five>\
                """
            )
        ).affirm();
    }

    @Test
    void convertDictClientToXml() throws Exception {
        final Dict dict = new DictOf()
            .with("name", "Branco Mello")
            .with(
                "address",
                new DictOf()
                    .with("street", "Av Cruz das Armas")
                    .with("number", "349")
                    .with("complement", "")
                    .with("neighborhood", "Jaguaribe")
                    .with("city", "João Pessoa")
                    .with("state", "PB")
                    .with("zipcode", "58015805")
            )
            .with("cpf", "58613518052")
            .with("email", "brancomello@gmail.com")
            .with("birth", LocalDate.of(1962, 3, 16));
        new Assertion<>(
            "must convert Dict client to XML correctly",
            new DictAsXml(dict),
            new IsText(
                """
                <name>Branco Mello</name><cpf>58613518052</cpf>\
                <birth>1962-03-16</birth><address><zipcode>58015805</zipcode>\
                <number>349</number><city>João Pessoa</city>\
                <street>Av Cruz das Armas</street>\
                <neighborhood>Jaguaribe</neighborhood><state>PB</state>\
                <complement></complement></address>\
                <email>brancomello@gmail.com</email>\
                """
            )
        ).affirm();
    }
}
