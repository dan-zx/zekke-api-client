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

import java.util.List;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.ManyPoisOptions;
import com.github.danzx.zekke.client.query.Query;

import okhttp3.HttpUrl;

public class ManyPoisHttpQuery extends ManyWaypointsHttpQuery<Poi> implements ManyPoisOptions, Query<List<Poi>> {

    private final ManyPoisOptions.Builder optionsBuilder;

    public ManyPoisHttpQuery(HttpClient httpClient, AccessTokenHolder tokenHolder) {
        super(Poi.class, httpClient, tokenHolder);
        optionsBuilder = new ManyPoisOptions.Builder();
    }

    @Override
    protected HttpUrl buildUrl() {
        final HttpUrl.Builder baseUrl = super.buildUrl().newBuilder();
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
