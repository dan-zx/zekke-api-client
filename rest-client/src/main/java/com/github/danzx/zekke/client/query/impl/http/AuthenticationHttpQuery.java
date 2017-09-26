package com.github.danzx.zekke.client.query.impl.http;

import com.google.gson.reflect.TypeToken;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.query.Query;

import okhttp3.HttpUrl;

public abstract class AuthenticationHttpQuery extends HttpQuery<AccessTokenHolder> implements Query<AccessTokenHolder> {

    private final TypeToken<AccessTokenHolder> typeToken;

    protected AuthenticationHttpQuery(HttpClient httpClient) {
        super(httpClient);
        typeToken = TypeToken.get(AccessTokenHolder.class);
    }

    protected HttpUrl.Builder jwtAuthenticationBaseUrl() {
        return getHttpClient().newBaseBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT);
    }

    protected TypeToken<AccessTokenHolder> getTypeToken() {
        return typeToken;
    }
}
