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

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;

public abstract class AuthenticationHttpQuery extends HttpQuery<AccessTokenHolder> implements Query<AccessTokenHolder> {

    protected AuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient, TypeToken.get(AccessTokenHolder.class));
    }

    @Override
    protected HttpUrl buildUrl() {
        return getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT)
                .build();
    }
}
