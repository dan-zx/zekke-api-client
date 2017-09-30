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
package com.github.danzx.zekke.client.query.impl.http;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;

import com.google.gson.reflect.TypeToken;

import okhttp3.Request;

abstract class JwtAuthorizedHttpQuery<R> extends HttpQuery<R> {

    private static final String AUTHORIZATION_HEADER_FORMAT = "Bearer %s";

    private final AccessTokenHolder accessTokenHolder;

    protected JwtAuthorizedHttpQuery(HttpClient httpClient, TypeToken<R> typeToken, AccessTokenHolder accessTokenHolder) {
        super(httpClient, typeToken);
        this.accessTokenHolder = requireNonNull(accessTokenHolder);
    }

    protected String getAuthorizationHeader() {
        return String.format(AUTHORIZATION_HEADER_FORMAT, accessTokenHolder.getAccessToken());
    }

    @Override
    protected Request.Builder buildRequestForJsonResponse() {
        return super.buildRequestForJsonResponse().header(Header.AUTHORIZATION.toString(), getAuthorizationHeader());
    }
}
