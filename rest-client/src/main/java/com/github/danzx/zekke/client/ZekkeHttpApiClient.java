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
package com.github.danzx.zekke.client;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.core.model.Walkway;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.impl.http.AdminAuthenticationHttpQuery;
import com.github.danzx.zekke.client.query.impl.http.AnonymouslyAuthenticationHttpQuery;
import com.github.danzx.zekke.client.query.impl.http.ManyNearWaypointsHttpQuery;
import com.github.danzx.zekke.client.query.impl.http.ManyPoisHttpQuery;
import com.github.danzx.zekke.client.query.impl.http.ManyWaypointsHttpQuery;
import com.github.danzx.zekke.client.query.impl.http.OneWaypointHttpQuery;

public class ZekkeHttpApiClient extends BaseZekkeApiClient {

    private final HttpClient httpClient;

    public ZekkeHttpApiClient(HttpClient httpClient) {
        this.httpClient = requireNonNull(httpClient);
    }

    @Override
    public AnonymouslyAuthenticationHttpQuery authenticateAnonymously() {
        return new AnonymouslyAuthenticationHttpQuery(httpClient);
    }

    @Override
    public AdminAuthenticationHttpQuery authenticateAdmin(String userId, String password) {
        return new AdminAuthenticationHttpQuery(httpClient, userId, password);
    }

    @Override
    public OneWaypointHttpQuery oneWaypoint(long id) {
        return new OneWaypointHttpQuery(httpClient, getTokenHolder(), id);
    }

    @Override
    public ManyWaypointsHttpQuery<TypedWaypoint> waypoints() {
        return new ManyWaypointsHttpQuery<>(TypedWaypoint.class, httpClient, getTokenHolder());
    }

    @Override
    public ManyWaypointsHttpQuery<Walkway> walkways() {
        return new ManyWaypointsHttpQuery<>(Walkway.class, httpClient, getTokenHolder());
    }

    @Override
    public ManyPoisHttpQuery pois() {
        return new ManyPoisHttpQuery(httpClient, getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<TypedWaypoint> waypointsNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(TypedWaypoint.class, httpClient, location, getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<Walkway> walkwaysNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(Walkway.class, httpClient, location, getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<Poi> poisNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(Poi.class, httpClient, location, getTokenHolder());
    }
}
