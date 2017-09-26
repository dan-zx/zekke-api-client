package com.github.danzx.zekke.client.query.impl.http;

import com.google.gson.reflect.TypeToken;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.Walkway;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyNearWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static java.util.Objects.requireNonNull;

public class ManyNearWaypointsHttpQuery<W extends BaseWaypoint> extends HttpQuery<List<W>> implements ManyNearWaypointsOptions, Query<List<W>> {

    private final Class<W> waypointClass;
    private final TypeToken<List<W>> typeToken;
    private final ManyNearWaypointsOptions.Builder optionsBuilder;
    private final AccessTokenHolder tokenHolder;

    @SuppressWarnings("unchecked")
    public ManyNearWaypointsHttpQuery(Class<W> waypointClass, HttpClient httpClient, Coordinates location, AccessTokenHolder tokenHolder) {
        super(httpClient);
        this.waypointClass = requireNonNull(waypointClass);
        this.tokenHolder = requireNonNull(tokenHolder);
        optionsBuilder = new ManyNearWaypointsOptions.Builder(location);
        typeToken = (TypeToken<List<W>>) TypeToken.getParameterized(ArrayList.class, this.waypointClass).getType();
    }

    @Override
    public List<W> get() {
        Request request = getHttpClient().newBaseRequestBuilderForJsonResponse(buildEndpointUrl())
                .header(Header.AUTHORIZATION.toString(), tokenHolder.getAccessToken())
                .build();
        return getHttpClient().doGetForJson(request, typeToken);
    }

    private HttpUrl buildEndpointUrl() {
        final HttpUrl.Builder urlBuilder = getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, getLocation().toString());
        if (waypointClass == Walkway.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.WALKWAYS);
        if (waypointClass == Poi.class) urlBuilder.addPathSegment(ApiUrlParts.PathSegments.POIS);
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
