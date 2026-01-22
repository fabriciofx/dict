/*
 * SPDX-FileCopyrightText: Copyright (C) 2025 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
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
                if (!valuea.equals(this.second.value(key, Object.class))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
