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

import java.util.ArrayList;
import java.util.List;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.Walkway;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyNearWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class ManyNearWaypointsHttpQuery<W extends BaseWaypoint> extends JwtAuthorizedHttpQuery<List<W>> implements ManyNearWaypointsOptions, Query<List<W>> {

    private final Class<W> waypointClass;
    private final ManyNearWaypointsOptions.Builder optionsBuilder;

    @SuppressWarnings("unchecked")
    public ManyNearWaypointsHttpQuery(Class<W> waypointClass, HttpClient httpClient, Coordinates location, AccessTokenHolder tokenHolder) {
        super(httpClient, (TypeToken<List<W>>) TypeToken.getParameterized(ArrayList.class, waypointClass), tokenHolder);
        this.waypointClass = requireNonNull(waypointClass);
        optionsBuilder = new ManyNearWaypointsOptions.Builder(location);

    }

    @Override
    protected Request buildRequest() {
        return buildRequestForJsonResponse().build();
    }

    @Override
    protected HttpUrl buildUrl() {
        HttpUrl.Builder urlBuilder = getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, getLocation().toString());
        if (waypointClass == Walkway.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.WALKWAYS);
        if (waypointClass == Poi.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.POIS);
        urlBuilder.addPathSegment(ApiUrlParts.PathSegments.NEAR);
        if (getDistance() != null) urlBuilder.addQueryParameter(ApiUrlParts.QueryParams.DISTANCE, getDistance().toString());
        if (getLimit() != null) urlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, getLimit().toString());
        return urlBuilder.build();
    }

    @Override
    public ManyNearWaypointsHttpQuery<W> atMostDistance(int distance) {
        optionsBuilder.atMostDistance(distance);
        return this;
    }

    @Override
    public ManyNearWaypointsHttpQuery<W> limit(int limit) {
        optionsBuilder.limit(limit);
        return this;
    }

    @Override
    public Coordinates getLocation() {
        return optionsBuilder.getLocation();
    }

    @Override
    public Integer getDistance() {
        return optionsBuilder.getDistance();
    }

    @Override
    public Integer getLimit() {
        return optionsBuilder.getLimit();
    }
}
