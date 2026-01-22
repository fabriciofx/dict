<!--
SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
SPDX-License-Identifier: MIT
-->

# Dict

A Python-like dictionary implementation for Java.

## Introduction

In Java we often need transfer data among objects. We can use a `Map` for it,
but a `dict` offer a better way to do it, including an input from JSON and an
output to JSON or XML.

## Usage

- Creating a new `Dict` using keys and values

```java
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
```

- Converting a `Dict` to JSON

```java
System.out.println(dict.asString());
```

```json
{"six":"2025-05-06 14:23:52","four":4,"one":"One","seven":{"nine":9.0,"eight":"Eight"},"five":"2025-05-06","three":3.14,"two":1.0}
```

- Getting a specific value from a `Dict`

```java
final String one = dict.value("one", String.class);
final double two = dict.value("two", Double.class);
final BigDecimal three = dict.value("three", BigDecimal.class);
final LocalDate five = dict.value("five", LocalDate.class);
final LocalDateTime six = dict.value("six", LocalDateTime.class);
final Dict seven = dict.value("seven", Dict.class);
```

- Converting a `Dict` to XML

```java
final String xml = new DictAsXml(dict).asString();
```

Output

```xml
<six>2025-05-06 14:23:52</six><four>4</four><one>One</one><seven>
<nine>9.0</nine><eight>Eight</eight></seven><five>2025-05-06</five>
<three>3.14</three><two>1.0</two>
```

## License

MIT.
