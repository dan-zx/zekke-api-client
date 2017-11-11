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

import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;

import okhttp3.HttpUrl;

/**
 * Anonymous authentication HTTP query. Authenticates a user with no credentials.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class AnonymousAuthenticationHttpJsonQuery extends BaseAuthenticationHttpJsonQuery {

    public AnonymousAuthenticationHttpJsonQuery(HttpJsonClient httpJsonClient) {
        super(httpJsonClient);
    }

    /** @return the JWT Authorization for anonymous users URL endpoint. */
    @Override
    protected HttpUrl.Builder urlBuilder() {
        return super.urlBuilder().addPathSegment(ApiUrlParts.PathSegments.ANONYMOUS);
    }
}
