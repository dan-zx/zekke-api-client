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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import com.github.danzx.zekke.client.api.rest.http.interceptor.DefaultHeaderSetterInterceptor;
import com.github.danzx.zekke.client.api.rest.http.interceptor.Slf4jHttpLoggingInterceptor;
import com.github.danzx.zekke.client.api.rest.test.util.Files;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class CachedHttpJsonClientTest {

    private static final Type WAYPOINT_TYPE = TypeToken.get(TypedWaypoint.class).getType();
    private static final String MOCK_PATH = "/zekke/api/mock";
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR = Slf4jHttpLoggingInterceptor.instance();
    private static final DefaultHeaderSetterInterceptor DEFAULT_HEADER_SETTER_INTERCEPTOR = new DefaultHeaderSetterInterceptor();

    static {
        LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static MockWebServer mockServer;
    private static HttpUrl mockServerUrl;
    private static HttpJsonClient mockHttpClient;

    @BeforeClass
    public static void onBeforeAll() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
        mockServerUrl = mockServer.url(MOCK_PATH);
        OkHttpClient okHttpClient = buildOkHttpClient().build();
        Gson gson = buildGson().create();
        mockHttpClient = new HttpJsonClient(okHttpClient, gson, mockServerUrl);
    }

    @AfterClass
    public static void onAfterAll() throws Exception {
        mockServer.shutdown();
    }

    protected static OkHttpClient.Builder buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(LOGGING_INTERCEPTOR)
                .addInterceptor(DEFAULT_HEADER_SETTER_INTERCEPTOR)
                .cache(new Cache(cacheDirectory(), 10 * 1024 * 1024));
    }

    protected static GsonBuilder buildGson() {
        return new GsonBuilder();
    }

    @Test
    @Parameters(method = "mockResponsesAndReturnedObjects")
    public void shouldExecuteRequestForObjectReturnExpectedObjectWhitCachedResponse(MockResponse mockResponse, TypedWaypoint expected) {
        mockServer.enqueue(mockResponse);
        Request request = new Request.Builder()
                .url(mockServerUrl)
                .get()
                .build();
        TypedWaypoint actual = mockHttpClient.executeRequestForObject(request, WAYPOINT_TYPE);
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    public Object[] mockResponsesAndReturnedObjects() {
        Object[] eTagResponses = eTagResponses();
        Object[] lastModifiedResponses = lastModifiedResponses();
        int eTagResponsesLength = eTagResponses.length;
        int lastModifiedResponsesLength = lastModifiedResponses.length;
        Object[] mockResponses = new Object[eTagResponsesLength + lastModifiedResponsesLength];
        System.arraycopy(eTagResponses, 0, mockResponses, 0, eTagResponsesLength);
        System.arraycopy(lastModifiedResponses, 0, mockResponses, eTagResponsesLength, lastModifiedResponsesLength);
        return mockResponses;
    }

    private Object[] lastModifiedResponses() {
        TypedWaypoint waypoint2 = newTypedWaypoint(2L, "A poi name", TypedWaypoint.Type.POI, 50.88437, -78.10897);
        TypedWaypoint emptyWaypoint = new TypedWaypoint();
        String modificationDate = "Sun, 18 Oct 2015 05:44:48 GMT";
        MockResponse firstResponse = new MockResponse()
                .setStatus(HttpStatus.OK.toString())
                .setHeader(HttpHeader.DATE.toString(), modificationDate)
                .setHeader(HttpHeader.LAST_MODIFIED.toString(), modificationDate)
                .setHeader(HttpHeader.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.value())
                .setBody(Files.readFromClasspath("/responses/waypoint.json"));

        MockResponse notModifiedResponse = new MockResponse()
                .setStatus(HttpStatus.NOT_MODIFIED.toString())
                .setHeader(HttpHeader.DATE.toString(), modificationDate);

        modificationDate = "Sun, 18 Oct 2015 05:45:45 GMT";
        MockResponse modifiedResponse = new MockResponse()
                .setStatus(HttpStatus.OK.toString())
                .setHeader(HttpHeader.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.value())
                .setHeader(HttpHeader.DATE.toString(), modificationDate)
                .setHeader(HttpHeader.LAST_MODIFIED.toString(), modificationDate)
                .setBody(Files.readFromClasspath("/responses/empty_object.json"));
        return new Object[][]{
                {firstResponse, waypoint2},
                {notModifiedResponse, waypoint2},
                {modifiedResponse, emptyWaypoint}
        };
    }

    private Object[] eTagResponses() {
        TypedWaypoint waypoint2 = newTypedWaypoint(2L, "A poi name", TypedWaypoint.Type.POI, 50.88437, -78.10897);
        TypedWaypoint emptyWaypoint = new TypedWaypoint();
        String modificationDate = "Sun, 18 Oct 2015 07:57:29 GMT";
        String eTag = "\"30057E5031BCF44D47B005A1F1700F7B\"";
        MockResponse firstResponse = new MockResponse()
                .setStatus(HttpStatus.OK.toString())
                .setHeader(HttpHeader.DATE.toString(), modificationDate)
                .setHeader(HttpHeader.ETAG.toString(), eTag)
                .setHeader(HttpHeader.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.value())
                .setBody(Files.readFromClasspath("/responses/waypoint.json"));

        MockResponse notModifiedResponse = new MockResponse()
                .setStatus(HttpStatus.NOT_MODIFIED.toString())
                .setHeader(HttpHeader.DATE.toString(), modificationDate)
                .setHeader(HttpHeader.ETAG.toString(), eTag);

        modificationDate = "Sun, 18 Oct 2015 07:59:01 GMT";
        eTag = "\"E83CA39A795E57283EC1D12EDA0FECD3\"";
        MockResponse modifiedResponse = new MockResponse()
                .setStatus(HttpStatus.OK.toString())
                .setHeader(HttpHeader.DATE.toString(), modificationDate)
                .setHeader(HttpHeader.ETAG.toString(), eTag)
                .setHeader(HttpHeader.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.value())
                .setBody(Files.readFromClasspath("/responses/empty_object.json"));
        return new Object[][]{
                {firstResponse, waypoint2},
                {notModifiedResponse, waypoint2},
                {modifiedResponse, emptyWaypoint}
        };
    }

    private TypedWaypoint newTypedWaypoint(Long id, String name, TypedWaypoint.Type type, Double latitude, Double longitude) {
        TypedWaypoint waypoint = new TypedWaypoint();
        waypoint.setId(id);
        waypoint.setType(type);
        waypoint.setName(name);
        if (latitude != null || longitude != null) {
            Coordinates location = new Coordinates();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            waypoint.setLocation(location);
        }
        return waypoint;
    }

    private static File cacheDirectory() {
        File cacheDirectory = new File(System.getProperty("java.io.tmpdir"));
        if (!cacheDirectory.exists()) {
            try {
                cacheDirectory = File.createTempFile("okhttp-cache", ".tmp");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return cacheDirectory;
    }
}
