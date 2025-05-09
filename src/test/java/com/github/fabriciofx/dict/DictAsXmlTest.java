/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.dict;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.cactoos.Text;
import org.cactoos.text.Concatenated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link DictAsXml}.
 *
 * @since 0.0.1
 */
final class DictAsXmlTest {
    @Test
    void asXml() throws Exception {
        final Text xml = new Concatenated(
            "<six>2025-05-06 14:23:52</six><four>4</four><one>One</one>",
            "<seven><nine>9.0</nine><eight>Eight</eight></seven>",
            "<five>2025-05-06</five><three>3.14</three><two>1.0</two>"
        );
        final Dict dict = new Dict()
            .with("one", "One")
            .with("two", 1.0)
            .with("three", new BigDecimal("3.14"))
            .with("four", 4)
            .with("five", LocalDate.of(2025, 5, 6))
            .with("six", LocalDateTime.of(2025, 5, 6, 14, 23, 52))
            .with(
                "seven",
                new Dict()
                    .with("eight", "Eight")
                    .with("nine", 9.0)
            );
        Assertions.assertEquals(xml.asString(), new DictAsXml(dict).asString());
    }

    @Test
    void clientAsXml() throws Exception {
        final Text xml = new Concatenated(
            "<address><zipcode>58015805</zipcode><number>349</number>",
            "<city>João Pessoa</city><street>Av Cruz das Armas</street>",
            "<state>PB</state><neighborhood>Jaguaribe</neighborhood>",
            "<complement></complement></address><cpf>58613518052</cpf>",
            "<name>Branco Mello</name><birth>1962-03-16</birth>",
            "<email>brancomello@gmail.com</email>"
        );
        final Dict dict = new Dict()
            .with("name", "Branco Mello")
            .with(
                "address",
                new Dict()
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
        Assertions.assertEquals(xml.asString(), new DictAsXml(dict).asString());
    }
}
