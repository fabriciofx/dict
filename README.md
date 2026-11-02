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

## How to use

Add the dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.github.fabriciofx</groupId>
  <artifactId>dict</artifactId>
  <version>0.0.43</version>
</dependency>
```

It requires Java 21 or later.

## Usage

- Creating a new `Dict` using keys and values

```java
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
```

- Creating a new `Dict` from JSON

```java
final Dict person = new DictOf("{\"name\": \"Maria\", \"age\": 31}");
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

- Getting keys, values and size of a `Dict`

```java
final Set<String> keys = dict.keys();
final Collection<Object> values = dict.values();
final int count = dict.count();
```

- Converting a `Dict` to JSON

```java
System.out.println(dict.asString());
```

Output

```json
{"six":"2025-05-06 14:23:52","four":4,"one":"One","seven":{"nine":9.0,"eight":"Eight"},"two":1.0,"three":3.14,"five":"2025-05-06"}
```

- Converting a `Dict` to XML

```java
final String xml = new DictAsXml(dict).asString();
```

Output

```xml
<six>2025-05-06 14:23:52</six><four>4</four><one>One</one><seven>
<nine>9.0</nine><eight>Eight</eight></seven><two>1.0</two>
<three>3.14</three><five>2025-05-06</five>
```

## Errors

All `Dict` methods (`keys()`, `values()`, `count()`, `value()`, `with()` and
`asString()`) and `DictAsXml.asString()` throw `DictException`, a checked
exception. It wraps the original failure as its cause, such as a
`JacksonException`. It is thrown when:

- the JSON given to `new DictOf(String)` is invalid; it is parsed lazily, so
  the exception comes from the first method called on the `Dict`
- `value()` is called with a key that does not exist
- `value()` is called with a type that does not match the stored value
- the `Dict` cannot be converted to JSON or XML

```java
try {
    final Integer age = new DictOf("{\"name\": \"Maria\"}")
        .value("age", Integer.class);
} catch (final DictException ex) {
    System.out.println(ex.getMessage());
}
```

Output

```text
Cannot find key 'age' in dict
```

## License

The MIT License (MIT)

Copyright (C) 2025-2026 Fabrício Barros Cabral

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
