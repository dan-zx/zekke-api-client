/*
 * Copyright 2017 Daniel Pedraza-Arcega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danzx.zekke.client.api.rest.util;

/**
 * Constant definitions for the standard charsets.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public enum Charset {

    ISO_8859_1("ISO-8859-1"),
    US_ASCII("US-ASCII"),
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    UTF_16BE("UTF-16BE"),
    UTF_16LE("UTF-16LE");

    private final String value;

    Charset(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
