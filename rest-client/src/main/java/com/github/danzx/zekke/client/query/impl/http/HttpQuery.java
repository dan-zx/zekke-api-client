package com.github.danzx.zekke.client.query.impl.http;

import com.google.gson.reflect.TypeToken;

import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static java.util.Objects.requireNonNull;

abstract class HttpQuery<R> implements Query<R> {

    private final HttpClient httpClient;
    private final TypeToken<R> typeToken;

    protected HttpQuery(HttpClient httpClient, TypeToken<R> typeToken) {
        this.httpClient = requireNonNull(httpClient);
        this.typeToken = requireNonNull(typeToken);
    }

    @Override
    public R get() {
        return getHttpClient().doGetForJson(buildRequest(), typeToken);
    }

    protected abstract HttpUrl buildUrl();

    protected abstract Request buildRequest();

    protected Request.Builder buildRequestForJsonResponse() {
        return getHttpClient().newBaseRequestBuilderForJsonResponse(buildUrl());
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }
}
