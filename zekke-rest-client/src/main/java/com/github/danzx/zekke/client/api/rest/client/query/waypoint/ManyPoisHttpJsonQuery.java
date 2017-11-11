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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.entrypoint.query.ManyPoisQuery;
import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.client.ApiUrlParts;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.api.rest.util.Strings;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;

import okhttp3.HttpUrl;

/**
 * POI HTTP query to get many resource.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class ManyPoisHttpJsonQuery extends ManyWaypointsHttpJsonQuery<Poi> implements ManyPoisQuery<List<Poi>> {

    private boolean isQueryForNameSuggestions = false;
    private String nameQuery;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param accessTokenSupplier the authorization credentials supplier.
     */
    public ManyPoisHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nonnull CredentialsSupplier<BearerTokenAuthorizationCredentials> accessTokenSupplier) {
        super(httpJsonClient, accessTokenSupplier, Poi.class);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery limit(@Nullable Integer limit) {
        super.limit(limit);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery enclosedInRectangle(@Nullable BoundingBox bbox) {
        super.enclosedInRectangle(bbox);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery forNameSuggestions() {
        return setQueryForNameSuggestions(true);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery complete() {
        return setQueryForNameSuggestions(false);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery setQueryForNameSuggestions(boolean isQueryForNameSuggestions) {
        this.isQueryForNameSuggestions = isQueryForNameSuggestions;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public ManyPoisHttpJsonQuery withNameContaining(@Nullable String nameQuery) {
        this.nameQuery = nameQuery;
        return this;
    }

    /** @return the URL for POIs with query params set. */
    @Nonnull
    @Override
    protected HttpUrl.Builder urlBuilder() {
        HttpUrl.Builder builder =  super.urlBuilder();
        if (!Strings.isBlank(nameQuery)) builder.addQueryParameter(ApiUrlParts.QueryParams.QUERY, nameQuery);
        if (isQueryForNameSuggestions) builder.addPathSegment(ApiUrlParts.PathSegments.NAMES);
        return builder;
    }
}
