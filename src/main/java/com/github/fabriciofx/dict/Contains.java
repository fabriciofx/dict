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

import org.cactoos.Scalar;

/**
 * Contains.
 *
 * Check if a Dict contains another Dict.
 *
 * @since 0.0.1
 */
public final class Contains implements Scalar<Boolean> {
    /**
     * First dict.
     */
    private final Dict first;

    /**
     * Second dict.
     */
    private final Dict second;

    /**
     * Ctor.
     * @param first First dict to compare
     * @param second Second dict to compare
     */
    public Contains(final Dict first, final Dict second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = true;
        for (final String key : this.first.keys()) {
            if (this.second.keys().contains(key)) {
                final Object valuea = this.first.value(key, Object.class);
                final Object valueb = this.second.value(key, Object.class);
                if (!valuea.equals(valueb)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
