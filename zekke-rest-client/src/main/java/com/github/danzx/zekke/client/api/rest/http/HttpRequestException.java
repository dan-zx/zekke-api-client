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
package com.github.danzx.zekke.client.api.rest.http;

import javax.annotation.Nullable;

/**
 * Exception thrown when an HTTP request fail.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class HttpRequestException extends RuntimeException {

    private static final long serialVersionUID = 726834298745937691L;

    /** {@inheritDoc} */
    public HttpRequestException(@Nullable String message) {
        super(message);
    }

    /** {@inheritDoc} */
    public HttpRequestException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
