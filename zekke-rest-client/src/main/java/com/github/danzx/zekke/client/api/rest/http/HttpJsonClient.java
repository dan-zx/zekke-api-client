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
package com.github.danzx.zekke.client.api.rest.http;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.rest.util.TypeTokens;
import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.exception.ApiException;

import com.google.gson.Gson;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP client based on OkHttp and Gson.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class HttpJsonClient {

    private final OkHttpClient client;
    private final Gson gson;
    private final HttpUrl baseUrl;

    /**
     * Constructor.
     *
     * @param client the internal HTTP client.
     * @param gson the JSON parser.
     * @param baseUrl the base URL to connect to ZeKKe API.
     */
    public HttpJsonClient(@Nonnull OkHttpClient client, @Nonnull Gson gson, @Nonnull HttpUrl baseUrl) {
        this.client = requireNonNull(client);
        this.gson = requireNonNull(gson);
        this.baseUrl = requireNonNull(baseUrl);
    }

    /**
     * Executes the given request, the resulting response body is converted to the given type.
     *
     * @param request the request.
     * @param type the conversion type.
     * @param <R> the conversion type.
     * @return the response body converted to given type
     * @throws HttpRequestException when the request fail.
     * @throws HttpResponseBodyException when the body cannot be parsed.
     * @throws ApiException when ZeKKe API informs an error.
     */
    public @Nullable <R> R executeRequestForObject(@Nonnull Request request, @Nonnull final Type type) {
        requireNonNull(type);
        return executeRequest(request, new ResponseFunction<R>() {
            @Override
            public R apply(Response response) {
                ResponseBody body = response.body();
                if (response.isSuccessful()) return parseJsonResponse(body, type);
                else {
                    ApiErrorDetail apiErrorDetail = parseJsonResponse(body, TypeTokens.API_ERROR_DETAIL.getType());
                    throw new ApiException(apiErrorDetail);
                }
            }
        });
    }

    /**
     * Executes the given request and returns only the HTTP status.
     *
     * @param request the request.
     * @return the HTTP status.
     * @throws HttpRequestException when the request fail.
     * @throws HttpResponseBodyException when the body cannot be parsed.
     */
    public @Nonnull HttpStatus executeRequestNoResponse(@Nonnull Request request) {
        return executeRequest(request, new ResponseFunction<HttpStatus>() {
            @Override
            public HttpStatus apply(Response response) {
                return HttpStatus.fromCode(response.code());
            }
        });
    }

    /** @return a base URL object ready to append other paths/query params. */
    public @Nonnull HttpUrl.Builder newUrlBuilder() {
        return baseUrl.newBuilder();
    }

    private interface ResponseFunction<R> {
        R apply(Response response);
    }

    private <R> R executeRequest(Request request, ResponseFunction<R> responseFunction) {
        requireNonNull(request);
        try (Response response = client.newCall(request).execute()) {
            return responseFunction.apply(response);
        } catch (SocketTimeoutException ex) {
            throw new HttpRequestException("Connection timeout", ex);
        } catch (IOException ex) {
            throw new HttpRequestException("Failed to perform request", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T parseJsonResponse(ResponseBody body, Type type) {
        try {
            return (T) gson.fromJson(body.charStream(), type);
        } catch (Exception ex) {
            throw new HttpResponseBodyException("Failed to convert response body to object", ex);
        }
    }
}
