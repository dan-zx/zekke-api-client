package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.HttpClient;

import okhttp3.HttpUrl;

public class AnonymouslyAuthenticationHttpQuery extends AuthenticationHttpQuery {

    public AnonymouslyAuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public AccessTokenHolder get() {
        HttpUrl url = jwtAuthenticationBaseUrl()
                .addPathSegment(ApiUrlParts.PathSegments.ANONYMOUS)
                .build();
        return getHttpClient().doGetForJson(getHttpClient().newBaseRequestBuilderForJsonResponse(url).build(), getTypeToken());
    }
}
