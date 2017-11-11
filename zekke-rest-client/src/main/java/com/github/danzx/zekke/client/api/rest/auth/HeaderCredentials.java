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
package com.github.danzx.zekke.client.api.rest.auth;

import javax.annotation.Nullable;

/**
 * The HTTP Authorization request header
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class HeaderCredentials {

    private final String header;
    private String value;

    /**
     * Constructor.
     *
     * @param header the header of the authorization request.
     */
    protected HeaderCredentials(@Nullable String header) {
        this.header = header;
    }

    public @Nullable String header() {
        return header;
    }

    public @Nullable String value() {
        return value;
    }

    protected void setValue(@Nullable String value) {
        this.value = value;
    }
}
