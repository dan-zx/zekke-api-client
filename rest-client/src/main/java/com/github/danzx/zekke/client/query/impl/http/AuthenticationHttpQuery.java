package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;

public abstract class AuthenticationHttpQuery extends HttpQuery<AccessTokenHolder> implements Query<AccessTokenHolder> {

    protected AuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient, TypeToken.get(AccessTokenHolder.class));
    }

    @Override
    protected HttpUrl buildUrl() {
        return getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT)
                .build();
    }
}
