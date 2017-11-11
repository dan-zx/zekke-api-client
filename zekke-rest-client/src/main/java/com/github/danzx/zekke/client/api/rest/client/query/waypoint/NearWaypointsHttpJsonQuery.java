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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.entrypoint.query.ManyWaypointsLocationQuery;
import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.Walkway;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;

/**
 * Waypoint HTTP query to get many resource near a given location.
 *
 * @param <W> the Waypoint type.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class NearWaypointsHttpJsonQuery<W extends BaseWaypoint> extends BaseWaypointHttpJsonQuery<List<W>> implements ManyWaypointsLocationQuery<List<W>> {

    private final Coordinates location;
    private final Class<W> paramType;
    private Integer limit;
    private Integer distance;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param accessTokenSupplier the authorization credentials supplier.
     * @param paramType the type of the returned list.
     * @param location the location.
     */
    @SuppressWarnings("unchecked")
    public NearWaypointsHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nonnull CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier, @Nonnull Class<W> paramType, @Nonnull Coordinates location) {
        super(httpJsonClient, accessTokenSupplier, (TypeToken<List<W>>) TypeToken.getParameterized(ArrayList.class, paramType));
        this.paramType = paramType;
        this.location = requireNonNull(location);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public NearWaypointsHttpJsonQuery<W> limit(@Nullable Integer limit) {
        this.limit = limit;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public NearWaypointsHttpJsonQuery<W> atMostDistance(@Nullable Integer distance) {
        this.distance = distance;
        return this;
    }

    /** @return the URL for either POI, Walkways or Waypoints with query params set filtered by location. */
    @Nonnull
    @Override
    protected HttpUrl.Builder urlBuilder() {
        HttpUrl.Builder builder = super.urlBuilder();
        if (paramType == Poi.class) builder.addPathSegment(ApiUrlParts.PathSegments.POIS);
        if (paramType == Walkway.class) builder.addPathSegment(ApiUrlParts.PathSegments.WALKWAYS);
        builder.addPathSegment(ApiUrlParts.PathSegments.NEAR)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, location.toString());
        if (limit != null) builder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
        if (distance != null) builder.addQueryParameter(ApiUrlParts.QueryParams.DISTANCE, distance.toString());
        return builder;
    }
}
