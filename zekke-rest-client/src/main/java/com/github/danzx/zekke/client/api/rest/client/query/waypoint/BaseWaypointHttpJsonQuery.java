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
package com.github.danzx.zekke.client.api.rest.client.query.waypoint;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.client.query.BaseHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Waypoint HTTP base query.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
abstract class BaseWaypointHttpJsonQuery<R> extends BaseHttpJsonQuery<R> {

    private final CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param accessTokenSupplier the authorization credentials supplier.
     * @param returnType the type of the returned result.
     */
    protected BaseWaypointHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nonnull CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier, @Nonnull TypeToken<R> returnType) {
        super(httpJsonClient, returnType);
        this.accessTokenSupplier = requireNonNull(accessTokenSupplier);
    }

    /** @return a GET request built with {@link #urlBuilder()}.build() result and setting the Authorization header.*/
    @Nonnull
    @Override
    protected Request request() {
        BearerTokenAuthorizationCredentials credentials = accessTokenSupplier.get();
        return new Request.Builder()
                .url(urlBuilder().build())
                .header(credentials.header(), credentials.value())
                .build();
    }

    /** @return the Waypoint URL endpoint. */
    @Nonnull
    @Override
    protected HttpUrl.Builder urlBuilder() {
        return super.urlBuilder().addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS);
    }
}
