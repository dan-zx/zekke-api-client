package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.HttpClient;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class AnonymouslyAuthenticationHttpQuery extends AuthenticationHttpQuery {

    public AnonymouslyAuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public AccessTokenHolder get() {
        return getHttpClient().doGetForJson(buildRequest(), getTypeToken());
    }

    @Override
    protected Request buildRequest() {
        return getHttpClient()
                .newBaseRequestBuilderForJsonResponse(buildUrl())
                .build();
    }

    @Override
    protected HttpUrl buildUrl() {
        return super.buildUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.ANONYMOUS)
                .build();
    }
}
