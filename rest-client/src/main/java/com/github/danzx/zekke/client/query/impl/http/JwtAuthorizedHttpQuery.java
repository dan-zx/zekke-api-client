package com.github.danzx.zekke.client.query.impl.http;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;

import com.google.gson.reflect.TypeToken;

import okhttp3.Request;

abstract class JwtAuthorizedHttpQuery<R> extends HttpQuery<R> {

    private static final String AUTHORIZATION_HEADER_FORMAT = "Bearer %s";

    private final AccessTokenHolder accessTokenHolder;

    protected JwtAuthorizedHttpQuery(HttpClient httpClient, TypeToken<R> typeToken, AccessTokenHolder accessTokenHolder) {
        super(httpClient, typeToken);
        this.accessTokenHolder = requireNonNull(accessTokenHolder);
    }

    protected String getAuthorizationHeader() {
        return String.format(AUTHORIZATION_HEADER_FORMAT, accessTokenHolder.getAccessToken());
    }

    @Override
    protected Request.Builder buildRequestForJsonResponse() {
        return super.buildRequestForJsonResponse().header(Header.AUTHORIZATION.toString(), getAuthorizationHeader());
    }
}
