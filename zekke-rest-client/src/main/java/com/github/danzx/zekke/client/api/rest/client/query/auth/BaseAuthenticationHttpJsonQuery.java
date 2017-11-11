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

import com.github.danzx.zekke.client.api.entrypoint.query.SingleResultQuery;
import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.client.query.BaseHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.api.rest.util.TypeTokens;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;

import okhttp3.HttpUrl;

/**
 * Authentication HTTP base query.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
abstract class BaseAuthenticationHttpJsonQuery extends BaseHttpJsonQuery<AccessTokenHolder> implements SingleResultQuery<AccessTokenHolder> {

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     */
    protected BaseAuthenticationHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient) {
        super(httpJsonClient, TypeTokens.ACCESS_TOKEN_HOLDER);
    }

    /** @return the URL to point to the JWT Authorization endpoint. */
    @Nonnull
    @Override
    protected HttpUrl.Builder urlBuilder() {
        return super.urlBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT);
    }
}
