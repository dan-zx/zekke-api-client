package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static java.util.Objects.requireNonNull;

abstract class HttpQuery<R> implements Query<R> {

    private final HttpClient httpClient;

    protected HttpQuery(HttpClient httpClient) {
        this.httpClient = requireNonNull(httpClient);
    }

    protected abstract HttpUrl buildUrl();
    protected abstract Request buildRequest();

    protected HttpClient getHttpClient() {
        return httpClient;
    }
}
