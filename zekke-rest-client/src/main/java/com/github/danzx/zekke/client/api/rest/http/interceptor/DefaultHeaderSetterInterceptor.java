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
package com.github.danzx.zekke.client.api.rest.http.interceptor;

import java.io.IOException;
import java.util.Locale;

import com.github.danzx.zekke.client.api.rest.http.ContentType;
import com.github.danzx.zekke.client.api.rest.http.HttpHeader;
import com.github.danzx.zekke.client.api.rest.util.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor that sets the default headers for JSON requests.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class DefaultHeaderSetterInterceptor implements Interceptor {

    /** {@inheritDoc} */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
        setHeaderIfNeeded(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value(), originalRequest, requestBuilder);
        setHeaderIfNeeded(HttpHeader.ACCEPT_LANGUAGE, Locale.getDefault().getLanguage(), originalRequest, requestBuilder);
        setHeaderIfNeeded(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString(), originalRequest, requestBuilder);
        return chain.proceed(requestBuilder.build());
    }

    private void setHeaderIfNeeded(HttpHeader header, String defaultValue, Request orginalRequest, Request.Builder requestBuilder) {
        if (orginalRequest.header(header.toString()) == null) requestBuilder.header(header.toString(), defaultValue);
    }
}
