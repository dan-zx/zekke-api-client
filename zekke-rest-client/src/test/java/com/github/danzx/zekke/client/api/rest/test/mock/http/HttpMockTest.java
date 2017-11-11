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
package com.github.danzx.zekke.client.api.rest.test.mock.http;

import java.util.concurrent.TimeUnit;

import com.github.danzx.zekke.client.api.rest.http.HttpJsonClient;
import com.github.danzx.zekke.client.api.rest.http.interceptor.DefaultHeaderSetterInterceptor;
import com.github.danzx.zekke.client.api.rest.http.interceptor.Slf4jHttpLoggingInterceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;

public abstract class HttpMockTest {

    private static final String MOCK_PATH = "/zekke/api/mock";
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR = Slf4jHttpLoggingInterceptor.instance();
    private static final DefaultHeaderSetterInterceptor DEFAULT_HEADER_SETTER_INTERCEPTOR = new DefaultHeaderSetterInterceptor();

    static {
        LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private MockWebServer mockServer;
    private HttpUrl mockServerUrl;
    private HttpJsonClient mockHttpClient;

    @Before
    public void onBeforeEach() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
        mockServerUrl = mockServer.url(MOCK_PATH);
        OkHttpClient okHttpClient = buildOkHttpClient().build();
        Gson gson = buildGson().create();
        mockHttpClient = new HttpJsonClient(okHttpClient, gson, mockServerUrl);
    }

    @After
    public void onAfterEach() throws Exception {
        mockServer.shutdown();
    }

    protected OkHttpClient.Builder buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(LOGGING_INTERCEPTOR)
                .addInterceptor(DEFAULT_HEADER_SETTER_INTERCEPTOR)
                .readTimeout(1, TimeUnit.SECONDS);
    }

    protected GsonBuilder buildGson() {
        return new GsonBuilder();
    }

    protected MockWebServer mockServer() {
        return mockServer;
    }

    protected HttpJsonClient mockHttpClient() {
        return mockHttpClient;
    }

    protected HttpUrl mockServerUrl() {
        return mockServerUrl;
    }
}
