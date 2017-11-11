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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.rest.util.Strings;

import okhttp3.Credentials;

/**
 * The credentials use the basic authorization scheme.
 * @see <a href="https://tools.ietf.org/html/rfc7617">The 'Basic' HTTP Authentication Scheme</a>
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class BasicAuthorizationCredentials extends AuthorizationHeaderCredentials {

    /**
     * Inits credentials (header value).
     *
     * @param userId the user id.
     * @param password the password.
     */
    public BasicAuthorizationCredentials(@Nullable String userId, @Nullable String password) {
        if(isBlank(userId)) userId = Strings.EMPTY;
        if(isBlank(password)) password = Strings.EMPTY;
        setValue(Credentials.basic(userId, password));
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String header() {
        return super.header();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String value() {
        return super.value();
    }
}
