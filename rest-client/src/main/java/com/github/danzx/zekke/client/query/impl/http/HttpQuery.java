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

import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

abstract class HttpQuery<R> implements Query<R> {

    private final HttpClient httpClient;
    private final TypeToken<R> typeToken;

    protected HttpQuery(HttpClient httpClient, TypeToken<R> typeToken) {
        this.httpClient = requireNonNull(httpClient);
        this.typeToken = requireNonNull(typeToken);
    }

    @Override
    public R get() {
        return getHttpClient().doGetForJson(buildRequest(), typeToken);
    }

    protected abstract HttpUrl buildUrl();

    protected abstract Request buildRequest();

    protected Request.Builder buildRequestForJsonResponse() {
        return getHttpClient().newBaseRequestBuilderForJsonResponse(buildUrl());
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }
}
