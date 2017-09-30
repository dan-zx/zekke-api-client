package com.github.danzx.zekke.client.query.impl.http;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.Walkway;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class ManyWaypointsHttpQuery<W extends BaseWaypoint> extends JwtAuthorizedHttpQuery<List<W>> implements ManyWaypointsOptions, Query<List<W>> {

    private final Class<W> waypointClass;
    private final ManyWaypointsOptions.Builder optionsBuilder;

    @SuppressWarnings("unchecked")
    public ManyWaypointsHttpQuery(Class<W> waypointClass, HttpClient httpClient, AccessTokenHolder tokenHolder) {
        super(httpClient, (TypeToken<List<W>>) TypeToken.getParameterized(ArrayList.class, waypointClass), tokenHolder);
        this.waypointClass = requireNonNull(waypointClass);
        optionsBuilder = new ManyWaypointsOptions.Builder();
    }

    @Override
    protected Request buildRequest() {
        return buildRequestForJsonResponse().build();
    }

    @Override
    protected HttpUrl buildUrl() {
        final HttpUrl.Builder urlBuilder = getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS);
        if (waypointClass == Walkway.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.WALKWAYS);
        if (waypointClass == Poi.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.POIS);
        if (getBbox() != null) urlBuilder.addQueryParameter(ApiUrlParts.QueryParams.BOUNDING_BOX, getBbox().toString());
        return urlBuilder.build();
    }

    @Override
    public ManyWaypointsHttpQuery<W> enclosedInRectangle(BoundingBox bbox) {
        optionsBuilder.enclosedInRectangle(bbox);
        return this;
    }

    @Override
    public BoundingBox getBbox() {
        return optionsBuilder.getBbox();
    }
}
