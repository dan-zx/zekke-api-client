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

import com.github.danzx.zekke.client.http.HttpClient;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class AnonymouslyAuthenticationHttpQuery extends AuthenticationHttpQuery {

    public AnonymouslyAuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected Request buildRequest() {
        return buildRequestForJsonResponse().build();
    }

    @Override
    protected HttpUrl buildUrl() {
        return super.buildUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.ANONYMOUS)
                .build();
    }
}
