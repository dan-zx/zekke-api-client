package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyPoisOptions;
import com.github.danzx.zekke.client.query.Query;

import java.util.List;

import okhttp3.HttpUrl;

public class ManyPoisHttpQuery extends ManyWaypointsHttpQuery<Poi> implements ManyPoisOptions, Query<List<Poi>> {

    private final ManyPoisOptions.Builder optionsBuilder;

    public ManyPoisHttpQuery(HttpClient httpClient, AccessTokenHolder tokenHolder) {
        super(Poi.class, httpClient, tokenHolder);
        optionsBuilder = new ManyPoisOptions.Builder();
    }

    @Override
    protected HttpUrl buildEndpointUrl() {
        final HttpUrl.Builder baseUrl = super.buildEndpointUrl().newBuilder();
        if (!arePoisComplete()) baseUrl.addPathSegment(ApiUrlParts.PathSegments.NAMES);
        if (getNameQuery() != null) baseUrl.addQueryParameter(ApiUrlParts.QueryParams.QUERY, getNameQuery());
        return baseUrl.build();
    }

    @Override
    public ManyPoisHttpQuery enclosedInRectangle(BoundingBox bbox) {
        optionsBuilder.enclosedInRectangle(bbox);
        return this;
    }

    @Override
    public ManyPoisHttpQuery forSuggestions() {
        optionsBuilder.forSuggestions();
        return this;
    }

    @Override
    public ManyPoisHttpQuery complete() {
        optionsBuilder.complete();
        return this;
    }

    @Override
    public ManyPoisHttpQuery withNameContainig(String nameQuery) {
        optionsBuilder.withNameContainig(nameQuery);
        return this;
    }

    @Override
    public boolean arePoisComplete() {
        return optionsBuilder.arePoisComplete();
    }

    @Override
    public BoundingBox getBbox() {
        return optionsBuilder.getBbox();
    }

    @Override
    public String getNameQuery() {
        return optionsBuilder.getNameQuery();
    }
}
