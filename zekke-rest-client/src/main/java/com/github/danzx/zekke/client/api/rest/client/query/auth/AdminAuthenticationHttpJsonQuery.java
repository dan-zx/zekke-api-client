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
package com.github.danzx.zekke.client.api.rest.client.query.auth;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.rest.auth.BasicAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Admin Authentication HTTP query. Authenticates a user via user/password combo.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class AdminAuthenticationHttpJsonQuery extends BaseAuthenticationHttpJsonQuery {

    private final String userId;
    private final String password;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param userId the user id.
     * @param password the password.
     */
    public AdminAuthenticationHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nullable String userId, @Nullable String password) {
        super(httpJsonClient);
        this.userId = userId;
        this.password = password;
    }

    /** @return a GET request built with {@link #urlBuilder()}.build() result and setting the Authorization header.*/
    @Nonnull
    @Override
    protected Request request() {
        BasicAuthorizationCredentials credentials = new BasicAuthorizationCredentials(userId, password);
        return new Request.Builder()
                .url(urlBuilder().build())
                .header(credentials.header(), credentials.value())
                .get()
                .build();
    }

    /** @return the JWT Authorization for admins URL endpoint. */
    @Nonnull
    @Override
    protected HttpUrl.Builder urlBuilder() {
        return super.urlBuilder().addPathSegment(ApiUrlParts.PathSegments.ADMIN);
    }
}
