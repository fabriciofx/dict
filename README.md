# Dict

A Python-like dictionary implementation for Java.

## Introduction

In Java we often need transfer data among objects. We can use a `Map` for it,
but a `dict` offer a better way to do it, including an input from JSON and an
output to JSON or XML.

## Usage

1. Creating a new `Dict` using keys and values
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

2. Converting a `Dict` to JSON

```java
System.out.println(dict.asString());
```
```json
{"six":"2025-05-06 14:23:52","four":4,"one":"One","seven":{"nine":9.0,"eight":"Eight"},"five":"2025-05-06","three":3.14,"two":1.0}
```

3. Getting a specific value from a `Dict`

```java
final String one = dict.value("one", String.class);
final double two = dict.value("two", Double.class);
final BigDecimal three = dict.value("three", BigDecimal.class);
final LocalDate five = dict.value("five", LocalDate.class);
final LocalDateTime six = dict.value("six", LocalDateTime.class);
final Dict seven = dict.value("seven", Dict.class);
```

4. Converting a `Dict` to XML

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

The MIT License (MIT)

Copyright (C) 2025 Fabrício Barros Cabral

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
