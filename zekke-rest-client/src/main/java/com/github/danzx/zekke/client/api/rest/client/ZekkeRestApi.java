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
package com.github.danzx.zekke.client.api.rest.client;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.ZekkeApi;
import com.github.danzx.zekke.client.api.entrypoint.AuthenticationEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.PoiCollectionsEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.TypedWaypointEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.WaypointCollectionsEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.WaypointTypeEntryPoint;
import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.client.query.auth.AdminAuthenticationHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.client.query.auth.AnonymousAuthenticationHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.client.query.waypoint.ManyPoisHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.client.query.waypoint.ManyWaypointsHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.client.query.waypoint.NearWaypointsHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.client.query.waypoint.WaypointByIdHttpJsonQuery;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.core.model.Walkway;

/**
 * ZeKKe RESTful API blocking client.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class ZekkeRestApi implements ZekkeApi<AccessTokenHolder, List<TypedWaypoint>, TypedWaypoint, List<Poi>, List<Walkway>> {

    private final HttpJsonClient httpJsonClient;

    private CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     */
    public ZekkeRestApi(@Nonnull HttpJsonClient httpJsonClient) {
        this.httpJsonClient = requireNonNull(httpJsonClient);
        accessTokenSupplier = EmptyAccessTokenSupplier.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public AuthenticationEntryPoint<AccessTokenHolder> authenticate() {
        return new AuthenticationEntryPoint<AccessTokenHolder>() {
            @Override
            public AnonymousAuthenticationHttpJsonQuery anonymously() {
                return new AnonymousAuthenticationHttpJsonQuery(httpJsonClient);
            }

            @Override
            public AdminAuthenticationHttpJsonQuery withAdminPrivileges(String userId, String password) {
                return new AdminAuthenticationHttpJsonQuery(httpJsonClient, userId, password);
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public WaypointTypeEntryPoint<List<TypedWaypoint>, TypedWaypoint, List<Poi>, List<Walkway>> findWaypoints() {
        return new WaypointTypeEntryPoint<List<TypedWaypoint>, TypedWaypoint, List<Poi>, List<Walkway>>() {
            @Override
            public TypedWaypointEntryPoint<List<TypedWaypoint>, TypedWaypoint> typed() {
                return new TypedWaypointEntryPoint<List<TypedWaypoint>, TypedWaypoint>() {
                    @Override
                    public WaypointByIdHttpJsonQuery byId(long id) {
                        return new WaypointByIdHttpJsonQuery(httpJsonClient, accessTokenSupplier, id);
                    }

                    @Override
                    public NearWaypointsHttpJsonQuery<TypedWaypoint> near(Coordinates location) {
                        return new NearWaypointsHttpJsonQuery<>(httpJsonClient, accessTokenSupplier, TypedWaypoint.class, location);
                    }

                    @Override
                    public ManyWaypointsHttpJsonQuery<TypedWaypoint> all() {
                        return new ManyWaypointsHttpJsonQuery<>(httpJsonClient, accessTokenSupplier, TypedWaypoint.class);
                    }
                };
            }

            @Override
            public PoiCollectionsEntryPoint<List<Poi>> pois() {
                return new PoiCollectionsEntryPoint<List<Poi>>() {
                    @Override
                    public ManyPoisHttpJsonQuery all() {
                        return new ManyPoisHttpJsonQuery(httpJsonClient, accessTokenSupplier);
                    }

                    @Override
                    public NearWaypointsHttpJsonQuery<Poi> near(Coordinates location) {
                        return new NearWaypointsHttpJsonQuery<>(httpJsonClient, accessTokenSupplier, Poi.class, location);
                    }
                };
            }

            @Override
            public WaypointCollectionsEntryPoint<List<Walkway>> walkways() {
                return new WaypointCollectionsEntryPoint<List<Walkway>>() {
                    @Override
                    public ManyWaypointsHttpJsonQuery<Walkway> all() {
                        return new ManyWaypointsHttpJsonQuery<>(httpJsonClient, accessTokenSupplier, Walkway.class);
                    }

                    @Override
                    public NearWaypointsHttpJsonQuery<Walkway> near(Coordinates location) {
                        return new NearWaypointsHttpJsonQuery<>(httpJsonClient, accessTokenSupplier, Walkway.class, location);
                    }
                };
            }
        };
    }

    /** @param accessTokenSupplier a CredentialsSupplier of BearerTokenAuthorizationCredentials. */
    public void setAccessTokenSupplier(@Nonnull CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier) {
        this.accessTokenSupplier = requireNonNull(accessTokenSupplier);
    }

    private static class EmptyAccessTokenSupplier implements CredentialsSupplier<BearerTokenAuthorizationCredentials> {
        private static BearerTokenAuthorizationCredentials EMPTY_CREDENTIALS = new BearerTokenAuthorizationCredentials(null);
        private static EmptyAccessTokenSupplier INSTANCE = new EmptyAccessTokenSupplier();

        @Override @Nonnull
        public BearerTokenAuthorizationCredentials get() {
            return EMPTY_CREDENTIALS;
        }
    }
}
