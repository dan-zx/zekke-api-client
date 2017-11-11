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
package com.github.danzx.zekke.client.api.rest.client.query;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.entrypoint.query.Query;
import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;

import com.google.gson.reflect.TypeToken;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Base HTTP query that accepts JSON bodies.
 *
 * @param <R> the actual object type to return.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public abstract class BaseHttpJsonQuery<R> implements Query<R> {

    private final TypeToken<R> returnType;
    private final HttpJsonClient httpJsonClient;

    /**
     * Constructor.
     *
     * @param httpJsonClient the HTTP internal client.
     * @param returnType the type of the returned result.
     */
    protected BaseHttpJsonQuery(@Nonnull HttpJsonClient httpJsonClient, @Nonnull TypeToken<R> returnType) {
        this.httpJsonClient = requireNonNull(httpJsonClient);
        this.returnType = requireNonNull(returnType);
    }

    /** {@inheritDoc} */
    @Override
    public R get() {
        return httpJsonClient.executeRequestForObject(request(), returnType.getType());
    }

    /** @return a GET request built with {@link #urlBuilder()}.build() result and setting the Authorization header.*/
    protected @Nonnull Request request() {
        return new Request.Builder()
                .url(urlBuilder().build())
                .get()
                .build();
    }

    /**
     * Use this method to build the endpoint URL with query params.
     *
     * @return the base URL.
     */
    protected @Nonnull HttpUrl.Builder urlBuilder() {
        return httpJsonClient().newUrlBuilder();
    }

    /** @return the HTTP internal client. */
    protected @Nonnull HttpJsonClient httpJsonClient() {
        return httpJsonClient;
    }
}
