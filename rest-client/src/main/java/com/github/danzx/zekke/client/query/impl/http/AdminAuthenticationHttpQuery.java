package com.github.danzx.zekke.client.query.impl.http;

import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.util.Base64;

import okhttp3.HttpUrl;
import okhttp3.Request;

import static java.util.Objects.requireNonNull;

public class AdminAuthenticationHttpQuery extends AuthenticationHttpQuery {

    private static final String AUTHORIZATION_HEADER_FORMAT = "Basic %s";
    private static final String BASIC_AUTHORIZATION_FORMAT = "%1$s:%2$s";

    private final String userId;
    private final String password;

    public AdminAuthenticationHttpQuery(HttpClient httpClient, String userId, String password) {
        super(httpClient);
        this.userId = requireNonNull(userId);
        this.password = requireNonNull(password);
    }

    @Override
    protected Request buildRequest() {
        byte[] userIdAndPasswordBytes = String.format(BASIC_AUTHORIZATION_FORMAT, userId, password).getBytes();
        userIdAndPasswordBytes = Base64.encode(userIdAndPasswordBytes);
        String headerInfo = String.format(AUTHORIZATION_HEADER_FORMAT, new String(userIdAndPasswordBytes));
        return getHttpClient().newBaseRequestBuilderForJsonResponse(buildUrl())
                .header(Header.AUTHORIZATION.toString(), headerInfo)
                .build();
    }

    @Override
    protected HttpUrl buildUrl() {
        return super.buildUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.ADMIN)
                .build();
    }
}
