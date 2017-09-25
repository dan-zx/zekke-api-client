package com.github.danzx.zekke.client;

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

public class ZekkeHttpApiClient extends BaseZekkeApiClient {

    private static final HttpClient DEFAULT_HTTP_CLIENT = new HttpClient();

    private HttpClient httpClient;

    @Override
    public AnonymouslyAuthenticationHttpQuery authenticateAnonymously() {
        return new AnonymouslyAuthenticationHttpQuery(getHttpClient());
    }

    @Override
    public AdminAuthenticationHttpQuery authenticateAdmin(String userId, String password) {
        return new AdminAuthenticationHttpQuery(getHttpClient(), userId, password);
    }

    @Override
    public ManyWaypointsHttpQuery<TypedWaypoint> waypoints() {
        return new ManyWaypointsHttpQuery<>(TypedWaypoint.class, getHttpClient(), getTokenHolder());
    }

    @Override
    public ManyWaypointsHttpQuery<Walkway> walkways() {
        return new ManyWaypointsHttpQuery<>(Walkway.class, getHttpClient(), getTokenHolder());
    }

    @Override
    public ManyPoisHttpQuery pois() {
        return new ManyPoisHttpQuery(getHttpClient(), getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<TypedWaypoint> waypointsNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(TypedWaypoint.class, getHttpClient(), location, getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<Walkway> walkwaysNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(Walkway.class, getHttpClient(), location, getTokenHolder());
    }

    @Override
    public ManyNearWaypointsHttpQuery<Poi> poisNear(Coordinates location) {
        return new ManyNearWaypointsHttpQuery<>(Poi.class, getHttpClient(), location, getTokenHolder());
    }

    public HttpClient getHttpClient() {
        return httpClient == null ? DEFAULT_HTTP_CLIENT : httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
