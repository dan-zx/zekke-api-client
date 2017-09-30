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

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpClient;
import com.github.danzx.zekke.client.util.Base64;

import okhttp3.HttpUrl;
import okhttp3.Request;

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
        return buildRequestForJsonResponse()
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
