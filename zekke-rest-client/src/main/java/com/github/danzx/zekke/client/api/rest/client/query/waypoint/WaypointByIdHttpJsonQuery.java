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

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.entrypoint.query.SingleResultQuery;
import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.api.rest.util.TypeTokens;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;

import okhttp3.HttpUrl;

/**
 * Waypoint HTTP query to get one resource.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class WaypointByIdHttpJsonQuery extends BaseWaypointHttpJsonQuery<TypedWaypoint> implements SingleResultQuery<TypedWaypoint> {

    private final long id;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param accessTokenSupplier the authorization credentials supplier.
     * @param id the waypoint id.
     */
    public WaypointByIdHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nonnull CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier, long id) {
        super(httpJsonClient, accessTokenSupplier, TypeTokens.WAYPOINT);
        this.id = id;
    }

    /** @return the Waypoint URL endpoint to find one resource. */
    @Override
    protected HttpUrl.Builder urlBuilder() {
        return super.urlBuilder().addPathSegment(Long.toString(id));
    }
}
