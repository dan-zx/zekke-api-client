package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.http.HttpClient;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class OneWaypointHttpQuery extends JwtAuthorizedHttpQuery<TypedWaypoint> {

    private final long id;

    public OneWaypointHttpQuery(HttpClient httpClient, AccessTokenHolder tokenHolder, long id) {
        super(httpClient, TypeToken.get(TypedWaypoint.class), tokenHolder);
        this.id = id;
    }

    @Override
    protected HttpUrl buildUrl() {
        return getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(Long.toString(id))
                .build();
    }

    @Override
    protected Request buildRequest() {
        return buildRequestForJsonResponse().build();
    }
}
