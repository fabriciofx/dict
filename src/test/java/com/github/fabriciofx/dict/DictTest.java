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
import java.util.List;
import java.util.Set;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.text.Concatenated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Dict}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class DictTest {
    @Test
    void checksKeys() {
        final Dict dict = new Dict(
            "{\"1\": \"one\", \"2\": \"two\", \"3\": \"three\"}"
        );
        Assertions.assertEquals(Set.of("1", "2", "3"), dict.keys());
    }

    @Test
    void checksValues() {
        final Dict dict = new Dict(
            "{\"1\": \"one\", \"2\": \"two\", \"3\": \"three\"}"
        );
        Assertions.assertIterableEquals(
            List.of("one", "two", "three"),
            dict.values()
        );
    }

    @Test
    void retrievesString() {
        final String one = "One";
        final Dict dict = new Dict().with("one", one);
        Assertions.assertEquals(one, dict.value("one", String.class));
    }

    @Test
    void retrievesDouble() {
        final double num = 1.0;
        final Dict dict = new Dict().with("two", num);
        Assertions.assertEquals(num, dict.value("two", Double.class));
    }

    @Test
    void retrievesBigDecimal() {
        final BigDecimal num = new BigDecimal("3.14");
        final Dict dict = new Dict().with("three", num);
        Assertions.assertEquals(num, dict.value("three", BigDecimal.class));
    }

    @Test
    void retrievesInteger() {
        final int num = 4;
        final Dict dict = new Dict().with("four", num);
        Assertions.assertEquals(num, dict.value("four", Integer.class));
    }

    @Test
    void retrievesLocalDate() {
        final LocalDate date = LocalDate.of(2025, 5, 6);
        final Dict dict = new Dict().with("five", date);
        Assertions.assertEquals(date, dict.value("five", LocalDate.class));
    }

    @Test
    void retrievesLocalDateTime() {
        final LocalDateTime time = LocalDateTime.of(2025, 5, 6, 14, 23, 52);
        final Dict dict = new Dict().with("six", time);
        Assertions.assertEquals(time, dict.value("six", LocalDateTime.class));
    }

    @Test
    void retrievesDict() {
        final Dict seven = new Dict().with("eight", "Eight").with("nine", 9.0);
        final Dict dict = new Dict().with("seven", seven);
        Assertions.assertEquals(seven, dict.value("seven", Dict.class));
    }

    @Test
    void retrievesList() {
        final List<Integer> ten = new ListOf<>(1, 2, 3);
        final Dict dict = new Dict().with("ten", ten);
        Assertions.assertEquals(ten, dict.value("ten", List.class));
    }

    @Test
    void checksJson() throws Exception {
        final Text json = new Concatenated(
            "{\"six\":\"2025-05-06 14:23:52\",\"four\":4,\"one\":\"One\",",
            "\"seven\":{\"nine\":9.0,\"eight\":\"Eight\"},\"ten\":[1,2,3],",
            "\"five\":\"2025-05-06\",\"three\":3.14,\"two\":1.0}"
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
            )
            .with("ten", new ListOf<>(1, 2, 3));
        Assertions.assertEquals(json.asString(), dict.asString());
    }

    @Test
    void countsEmptyStringAsZero() {
        final Dict dict = new Dict("");
        Assertions.assertEquals(0, dict.count());
    }

    @Test
    void countsEmptyDictAsZero() {
        final Dict dict = new Dict("{}");
        Assertions.assertEquals(0, dict.count());
    }

    @Test
    void countsOne() {
        final Dict dict = new Dict("{\"1\": \"one\"}");
        Assertions.assertEquals(1, dict.count());
    }

    @Test
    void countsThree() {
        final Dict dict = new Dict(
            "{\"1\": \"one\", \"2\": \"two\", \"3\": \"three\"}"
        );
        Assertions.assertEquals(3, dict.count());
    }

    @Test
    void createsDictUsingFluent() throws Exception {
        final Dict dict = new Dict()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        Assertions.assertEquals(
            "{\"1\":\"one\",\"2\":\"two\",\"3\":\"three\"}",
            dict.asString()
        );
    }

    @Test
    void checkEquals() {
        final Dict first = new Dict()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        final Dict second = new Dict()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        Assertions.assertEquals(first, second);
    }

    @Test
    void checkNotEquals() {
        final Dict first = new Dict()
            .with("1", "one")
            .with("2", "two");
        final Dict second = new Dict()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        Assertions.assertNotEquals(first, second);
    }

    @Test
    void checksUnorderedAndComposed() {
        final Dict first = new Dict()
            .with("1", "one")
            .with("2", "two")
            .with(
                "3",
                new Dict()
                    .with("4", "four")
                    .with("5", "five")
                    .with("6", "six")
            );
        final Dict second = new Dict()
            .with(
                "3",
                new Dict()
                    .with("4", "four")
                    .with("5", "five")
                    .with("6", "six")
            )
            .with("1", "one")
            .with("2", "two");
        Assertions.assertEquals(first, second);
    }

    @Test
    void checksUnorderedAndDeeplyComposed() {
        final Dict end = new Dict()
            .with("street", "Rua do Benfica")
            .with("number", "123")
            .with("complement", "Apto 201")
            .with("neighborhood", "Moema")
            .with("city", "São Paulo")
            .with("state", "SP")
            .with("zipcode", "12345678");
        final Dict items = new Dict()
            .with(
                "1", new Dict()
                    .with("product", "Meia Lua Pandeirola Liverpool 16")
                    .with("amount", 5.0)
                    .with("price", 58.95)
            ).with(
                "2", new Dict()
                    .with("product", "Guitarra Fender")
                    .with("amount", 3.0)
                    .with("price", 5010.99)
            ).with(
                "3", new Dict()
                    .with("product", "Bateria DX-722")
                    .with("amount", 1.0)
                    .with("price", 3003.60)
            );
        final Dict first = new Dict()
            .with("cpf", "25066158065")
            .with("client", "Samuel Rosa")
            .with("id", "07e2c7f2-6c61-4f20-a4bc-6022337950b4")
            .with("address", end)
            .with("items", items)
            .with("total", new BigDecimal("18331.32"));
        final Dict second = new Dict()
            .with("id", "07e2c7f2-6c61-4f20-a4bc-6022337950b4")
            .with("client", "Samuel Rosa")
            .with("cpf", "25066158065")
            .with(
                "address",
                new Dict()
                    .with("street", "Rua do Benfica")
                    .with("number", "123")
                    .with("complement", "Apto 201")
                    .with("neighborhood", "Moema")
                    .with("city", "São Paulo")
                    .with("state", "SP")
                    .with("zipcode", "12345678")
            )
            .with(
                "items",
                new Dict()
                    .with(
                        "1", new Dict()
                            .with("product", "Meia Lua Pandeirola Liverpool 16")
                            .with("amount", 5.0)
                            .with("price", 58.95)
                    ).with(
                        "2", new Dict()
                            .with("product", "Guitarra Fender")
                            .with("amount", 3.0)
                            .with("price", 5010.99)
                    ).with(
                        "3", new Dict()
                            .with("product", "Bateria DX-722")
                            .with("amount", 1.0)
                            .with("price", 3003.60)
                    )
            )
            .with("total", new BigDecimal("18331.32"));
        Assertions.assertEquals(first, second);
    }

    @Test
    void checksDictWithLocalDate() {
        final LocalDate birth = LocalDate.of(1962, 3, 16);
        final Dict dict = new Dict().with("birth", birth);
        Assertions.assertEquals(
            dict.value("birth", LocalDate.class),
            birth
        );
    }
}
