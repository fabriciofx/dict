/*
 * SPDX-FileCopyrightText: Copyright (C) 2025-2026 Fabrício Barros Cabral
 * SPDX-License-Identifier: MIT
 */
package com.github.fabriciofx.dict;

/**
 * DictException.
 *
 * <p>A failure while building, reading or converting a dict.</p>
 *
 * @since 0.0.39
 */
public final class DictException extends Exception {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 4_017_355_829_746_091_212L;

    /**
     * Ctor.
     *
     * @param message Message explaining the failure
     * @param cause Failure that caused it, such as a Jackson one
     */
    public DictException(final String message, final Exception cause) {
        super(message, cause);
    }
}
