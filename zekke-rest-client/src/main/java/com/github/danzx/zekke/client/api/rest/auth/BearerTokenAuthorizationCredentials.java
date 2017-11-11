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

import static com.github.danzx.zekke.client.api.rest.util.Strings.isBlank;

import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.rest.util.Strings;

/**
 * The credentials use the bearer authorization scheme.
 * @see <a href="https://tools.ietf.org/html/rfc6750">Bearer Token Usage</a>
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class BearerTokenAuthorizationCredentials extends AuthorizationHeaderCredentials {

    private static final String AUTHORIZATION_HEADER_FORMAT = "Bearer %s";

    /**
     * Inits credentials (header value).
     *
     * @param token the bearer token
     */
    public BearerTokenAuthorizationCredentials(@Nullable String token) {
        if(isBlank(token)) setValue(Strings.EMPTY);
        else setValue(String.format(AUTHORIZATION_HEADER_FORMAT, token));
    }
}
