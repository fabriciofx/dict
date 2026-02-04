/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.cactoos.list.ListOf;
import org.cactoos.set.SetOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Matches;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link DictOf}.
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "unchecked",
    "PMD.TooManyMethods",
    "PMD.AvoidDuplicateLiterals",
    "PMD.UnnecessaryLocalRule",
    "PMD.UnitTestShouldIncludeAssert"
})
final class DictOfTest {
    @Test
    void checksKeys() {
        final Dict dict = new DictOf(
            """
            {"1": "one", "2": "two", "3": "three"}
            """
        );
        new Assertion<>(
            "must have the correct keys",
            dict.keys(),
            new IsEqual<>(new SetOf<>("1", "2", "3"))
        ).affirm();
    }

    @Test
    void checksValues() {
        final Dict dict = new DictOf(
            """
            {"1": "one", "2": "two", "3": "three"}
            """
        );
        new Assertion<>(
            "must have the correct values",
            new ListOf<>(dict.values()),
            new IsEqual<>(new ListOf<>("one", "two", "three"))
        ).affirm();
    }

    @Test
    void retrievesString() {
        final Dict dict = new DictOf().with("1", "One");
        new Assertion<>(
            "must retrieve the correct string",
            dict.value("1", String.class),
            new IsEqual<>("One")
        ).affirm();
    }

    @Test
    void retrievesDouble() {
        final Dict dict = new DictOf().with("two", 3.1415);
        new Assertion<>(
            "must retrieve the correct double",
            dict.value("two", Double.class),
            new IsEqual<>(3.1415)
        ).affirm();
    }

    @Test
    void retrievesBigDecimal() {
        final Dict dict = new DictOf().with("three", new BigDecimal("3.14"));
        new Assertion<>(
            "must retrieve the correct BigDecimal",
            dict.value("three", BigDecimal.class),
            new IsEqual<>(new BigDecimal("3.14"))
        ).affirm();
    }

    @Test
    void retrievesInteger() {
        final Dict dict = new DictOf().with("four", 4);
        new Assertion<>(
            "must retrieve the correct Integer",
            dict.value("four", Integer.class),
            new IsEqual<>(4)
        ).affirm();
    }

    @Test
    void retrievesLocalDate() {
        final Dict dict = new DictOf().with("five", LocalDate.of(2025, 5, 6));
        new Assertion<>(
            "must retrieve the correct LocalDate",
            dict.value("five", LocalDate.class),
            new IsEqual<>(LocalDate.of(2025, 5, 6))
        ).affirm();
    }

    @Test
    void retrievesLocalDateTime() {
        final Dict dict = new DictOf().with(
            "six",
            LocalDateTime.of(2025, 5, 6, 14, 23, 52)
        );
        new Assertion<>(
            "must retrieve the correct LocalDateTime",
            dict.value("six", LocalDateTime.class),
            new IsEqual<>(LocalDateTime.of(2025, 5, 6, 14, 23, 52))
        ).affirm();
    }

    @Test
    void retrievesDict() {
        final Dict dict = new DictOf().with(
            "seven",
            new DictOf().with("8", "eight").with("nine", 9.0)
        );
        new Assertion<>(
            "must retrieve the correct Dict",
            dict.value("seven", DictOf.class),
            new IsEqual<>(new DictOf().with("8", "eight").with("nine", 9.0))
        ).affirm();
    }

    @Test
    void retrievesList() {
        final Dict dict = new DictOf().with("ten", new ListOf<>(1, 2, 3));
        new Assertion<>(
            "must retrieve the correct List",
            dict.value("ten", List.class),
            new IsEqual<>(new ListOf<>(1, 2, 3))
        ).affirm();
    }

    @Test
    void checksJson() {
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
            )
            .with("ten", new ListOf<>(1, 2, 3));
        new Assertion<>(
            "must convert Dict to JSON correctly",
            dict,
            new IsText(
                """
                {"six":"2025-05-06 14:23:52","four":4,"one":"One",\
                "seven":{"nine":9.0,"eight":"Eight"},"ten":[1,2,3],\
                "five":"2025-05-06","three":3.14,"two":1.0}\
                """
            )
        ).affirm();
    }

    @Test
    void jsonToList() {
        final Dict dict = new DictOf("{\"numbers\":[1,2,3]}");
        final List<Integer> numbers = dict.value(
            "numbers",
            List.class
        );
        new Assertion<>(
            "must convert JSON array to List correctly",
            numbers,
            new IsEqual<>(new ListOf<>(1, 2, 3))
        ).affirm();
    }

    @Test
    void countsEmptyStringAsZero() {
        new Assertion<>(
            "must count empty string as zero",
            new DictOf("").count(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void countsEmptyDictAsZero() {
        new Assertion<>(
            "must count empty dict as zero",
            new DictOf("{}").count(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void countsOne() {
        final Dict dict = new DictOf(
            """
            {"1": "one"}
            """
        );
        new Assertion<>(
            "must count one item",
            dict.count(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void countsThree() {
        final Dict dict = new DictOf(
            """
            {"1": "one", "2": "two", "3": "three"}
            """
        );
        new Assertion<>(
            "must count three items",
            dict.count(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    void createsDictUsingFluent() {
        final Dict dict = new DictOf()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        new Assertion<>(
            "must create Dict using fluent interface",
            dict,
            new IsText(
                """
                {"1":"one","2":"two","3":"three"}\
                """
            )
        ).affirm();
    }

    @Test
    void checkEquals() {
        final Dict first = new DictOf()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        final Dict second = new DictOf()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        new Assertion<>(
            "must be equal dicts",
            first,
            new IsEqual<>(second)
        ).affirm();
    }

    @Test
    void checkNotEquals() {
        final Dict first = new DictOf()
            .with("1", "one")
            .with("2", "two");
        final Dict second = new DictOf()
            .with("1", "one")
            .with("2", "two")
            .with("3", "three");
        new Assertion<>(
            "must be not equal dicts",
            first,
            new IsNot<>(new IsEqual<>(second))
        ).affirm();
    }

    @Test
    void checksUnorderedAndComposed() {
        final Dict first = new DictOf()
            .with("1", "one")
            .with("2", "two")
            .with(
                "3",
                new DictOf()
                    .with("4", "four")
                    .with("5", "five")
                    .with("6", "six")
            );
        final Dict second = new DictOf()
            .with(
                "3",
                new DictOf()
                    .with("4", "four")
                    .with("5", "five")
                    .with("6", "six")
            )
            .with("1", "one")
            .with("2", "two");
        new Assertion<>(
            "must be equal unordered and composed dicts",
            first,
            new IsEqual<>(second)
        ).affirm();
    }

    @Test
    void checksUnorderedAndDeeplyComposed() {
        final Dict address = new DictOf()
            .with("street", "Rua do Benfica")
            .with("number", "123")
            .with("complement", "Apto 201")
            .with("neighborhood", "Moema")
            .with("city", "São Paulo")
            .with("state", "SP")
            .with("zipcode", "12345678");
        final Dict items = new DictOf()
            .with(
                "1", new DictOf()
                    .with("product", "Meia Lua Pandeirola Liverpool 16")
                    .with("amount", 5.0)
                    .with("price", 58.95)
            ).with(
                "2", new DictOf()
                    .with("product", "Guitarra Fender")
                    .with("amount", 3.0)
                    .with("price", 5010.99)
            ).with(
                "3", new DictOf()
                    .with("product", "Bateria DX-722")
                    .with("amount", 1.0)
                    .with("price", 3003.60)
            );
        final Dict first = new DictOf()
            .with("cpf", "25066158065")
            .with("client", "Samuel Rosa")
            .with("id", "07e2c7f2-6c61-4f20-a4bc-6022337950b4")
            .with("address", address)
            .with("items", items)
            .with("total", new BigDecimal("18331.32"));
        final Dict second = new DictOf()
            .with("id", "07e2c7f2-6c61-4f20-a4bc-6022337950b4")
            .with("client", "Samuel Rosa")
            .with("cpf", "25066158065")
            .with(
                "address",
                new DictOf()
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
                new DictOf()
                    .with(
                        "3", new DictOf()
                            .with("product", "Bateria DX-722")
                            .with("amount", 1.0)
                            .with("price", 3003.60)
                    )
                    .with(
                        "2", new DictOf()
                            .with("product", "Guitarra Fender")
                            .with("amount", 3.0)
                            .with("price", 5010.99)
                    )
                    .with(
                        "1", new DictOf()
                            .with("product", "Meia Lua Pandeirola Liverpool 16")
                            .with("amount", 5.0)
                            .with("price", 58.95)
                    )
            )
            .with("total", new BigDecimal("18331.32"));
        new Assertion<>(
            "must be equal unordered and deeply composed dicts",
            first,
            new IsEqual<>(second)
        ).affirm();
    }

    @Test
    void checksDictWithLocalDate() {
        final Dict dict = new DictOf().with("birth", LocalDate.of(1962, 3, 16));
        new Assertion<>(
            "must be equal dicts with LocalDate",
            dict,
            new IsEqual<>(new DictOf().with("birth", LocalDate.of(1962, 3, 16)))
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasBadJson() {
        new Assertion<>(
            "must throw an exception when Dict has a bad JSON",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf("{bad json}").asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonWithLastComma() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON with last comma",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf(
                    "{\"name\": \"John\", \"age\": 42,}"
                ).asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonWithSingleQuote() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON with single quote",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf("{'name': 'John', 'active': true}").asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonKeysWithoutQuotes() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON keys without quotes",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf("{name: \"Maria\", age: 31}").asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonWithComments() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON with comments",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf(
                    """
                    { name: "Maria", // comment
                    age: 31 }
                    """
                ).asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonUnbalanced() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON unbalanced",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf(
                    """
                    {
                        "itens": [
                            { "id": 1 },
                            { "id": 2 }
                    }
                    """
                ).asString()
            )
        ).affirm();
    }

    @Test
    void throwsExceptionIfDictHasJsonWithInvalidTrue() {
        new Assertion<>(
            "must throw an exception when Dict has a JSON with invalid true",
            new Throws<>(Exception.class),
            new Matches<>(
                () -> new DictOf("{ \"active\": True}").asString()
            )
        ).affirm();
    }
}
