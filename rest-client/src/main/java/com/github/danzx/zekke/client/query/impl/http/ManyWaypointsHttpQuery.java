package com.github.danzx.zekke.client.query.impl.http;

import com.google.gson.reflect.TypeToken;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.Walkway;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static java.util.Objects.requireNonNull;

public class ManyWaypointsHttpQuery<W extends BaseWaypoint> extends HttpQuery<List<W>> implements ManyWaypointsOptions, Query<List<W>> {

    private final Class<W> waypointClass;
    private final TypeToken<List<W>> typeToken;
    private final ManyWaypointsOptions.Builder optionsBuilder;
    private final AccessTokenHolder tokenHolder;

    @SuppressWarnings("unchecked")
    public ManyWaypointsHttpQuery(Class<W> waypointClass, HttpClient httpClient, AccessTokenHolder tokenHolder) {
        super(httpClient);
        this.waypointClass = requireNonNull(waypointClass);
        this.tokenHolder = requireNonNull(tokenHolder);
        optionsBuilder = new ManyWaypointsOptions.Builder();
        typeToken = (TypeToken<List<W>>) TypeToken.getParameterized(ArrayList.class, this.waypointClass).getType();
    }

    @Override
    public List<W> get() {
        Request request = getHttpClient().newBaseRequestBuilderForJsonResponse(buildEndpointUrl())
                .header(Header.AUTHORIZATION.toString(), tokenHolder.getAccessToken())
                .build();
        return getHttpClient().doGetForJson(request, typeToken);
    }

    protected HttpUrl buildEndpointUrl() {
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
