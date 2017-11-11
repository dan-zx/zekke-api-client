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
package com.github.danzx.zekke.client.api.rest.client;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;

import static com.github.danzx.zekke.client.api.rest.test.assertion.ProjectAssertions.assertThat;
import static com.github.danzx.zekke.client.api.rest.test.assertion.ProjectAssertions.failBecauseExceptionWasNotThrown;
import static com.github.danzx.zekke.client.api.rest.test.util.Pair.pairOf;

import java.util.List;
import java.util.Map;

import com.github.danzx.zekke.client.api.entrypoint.query.ManyPoisQuery;
import com.github.danzx.zekke.client.api.entrypoint.query.ManyWaypointsLocationQuery;
import com.github.danzx.zekke.client.api.entrypoint.query.ManyWaypointsQuery;
import com.github.danzx.zekke.client.api.rest.auth.BearerTokenAuthorizationCredentials;
import com.github.danzx.zekke.client.api.rest.auth.CredentialsSupplier;
import com.github.danzx.zekke.client.api.rest.http.ContentType;
import com.github.danzx.zekke.client.api.rest.http.HttpHeader;
import com.github.danzx.zekke.client.api.rest.http.HttpMethod;
import com.github.danzx.zekke.client.api.rest.http.HttpRequestException;
import com.github.danzx.zekke.client.api.rest.http.HttpStatus;
import com.github.danzx.zekke.client.api.rest.test.mock.http.HttpMockTest;
import com.github.danzx.zekke.client.api.rest.test.mock.http.ResponseFile;
import com.github.danzx.zekke.client.api.rest.util.Charset;
import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.exception.ApiException;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.core.model.Walkway;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ZekkeHttpApiClientTest extends HttpMockTest {

    private static final String FAKE_AUTHENTICATION_HEADER_INFO = "Bearer token";
    private ZekkeRestApi client;

    @Override
    public void onBeforeEach() throws Exception {
        super.onBeforeEach();
        client = new ZekkeRestApi(mockHttpClient());
        client.setAccessTokenSupplier(new CredentialsSupplier<BearerTokenAuthorizationCredentials>() {
            @Override
            public BearerTokenAuthorizationCredentials get() {
                return new BearerTokenAuthorizationCredentials("token");
            }
        });
    }

    @Test
    public void shouldGetAnonymousAccessToken() throws InterruptedException {
        HttpUrl expectedUrl = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT)
                .addPathSegment(ApiUrlParts.PathSegments.ANONYMOUS)
                .build();
        mockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client
                .authenticate()
                .anonymously()
                .get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrl)
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()));
        assertThat(accessTokenHolder).isNotNull().extracting("accessToken").containsOnly("token");
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetAnonymousAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.authenticate()
                    .anonymously()
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    public void shouldGetAdminAccessToken() throws InterruptedException {
        HttpUrl expectedUrl = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.AUTHENTICATION)
                .addPathSegment(ApiUrlParts.PathSegments.JWT)
                .addPathSegment(ApiUrlParts.PathSegments.ADMIN)
                .build();
        String authenticationHeaderInfo = "Basic bXlVc2VySWQ6bXlQYXNzd29yZA==";
        mockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client
                .authenticate()
                .withAdminPrivileges("myUserId", "myPassword")
                .get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrl)
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, authenticationHeaderInfo));
        assertThat(accessTokenHolder).isNotNull().extracting("accessToken").containsOnly("token");
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetAdminAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.authenticate()
                    .withAdminPrivileges("myUserId", "myPassword")
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    public void shouldGetOneWaypoint() throws InterruptedException {
        long waypointId = 2L;
        HttpUrl expectedUrl = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(Long.toString(waypointId))
                .build();
        mockServer().enqueue(ResponseFile.ONE_WAYPOINT.toMockResponse());
        TypedWaypoint expectedWaypoint = newTypedWaypoint(waypointId, "A poi name", TypedWaypoint.Type.POI, 50.88437, -78.10897);
        TypedWaypoint actualWaypoint = client
                .findWaypoints()
                .typed()
                .byId(expectedWaypoint.getId())
                .get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrl)
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualWaypoint).isNotNull().isEqualTo(expectedWaypoint);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetOneWaypointThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .typed()
                    .byId(1L)
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    @Parameters(method = "limitsAndBoundingBoxes")
    public void shouldGetWaypoints(Integer limit, BoundingBox bbox) throws InterruptedException {
        List<TypedWaypoint> expectedWaypoints = allWaypoints();
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS);
        mockServer().enqueue(ResponseFile.WAYPOINTS.toMockResponse());
        ManyWaypointsQuery<List<TypedWaypoint>> query = client
                .findWaypoints()
                .typed()
                .all();
        if (limit != null) {
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
            query.limit(limit);
        }
        if (bbox != null) {
            query.enclosedInRectangle(bbox);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.BOUNDING_BOX, bbox.toString());
        }
        List<TypedWaypoint> actualWaypoints = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualWaypoints).isNotNull().isNotEmpty().hasSameSizeAs(expectedWaypoints).containsExactlyElementsOf(expectedWaypoints);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetWaypointsThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .typed()
                    .all()
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    @Parameters(method = "limitsAndBoundingBoxes")
    public void shouldGetWalkways(Integer limit, BoundingBox bbox) throws InterruptedException {
        List<Walkway> expectedWalkways = allWalkways();
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(ApiUrlParts.PathSegments.WALKWAYS);
        mockServer().enqueue(ResponseFile.WALKWAYS.toMockResponse());
        ManyWaypointsQuery<List<Walkway>> query = client
                .findWaypoints()
                .walkways()
                .all();
        if (limit != null) {
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
            query.limit(limit);
        }
        if (bbox != null) {
            query.enclosedInRectangle(bbox);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.BOUNDING_BOX, bbox.toString());
        }
        List<Walkway> actualWalkways = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualWalkways).isNotNull().isNotEmpty().hasSameSizeAs(expectedWalkways).containsExactlyElementsOf(expectedWalkways);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetWalkwaysThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .walkways()
                    .all()
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    @Parameters(method = "manyPoisQueryParams")
    public void shouldGetPois(Integer limit, BoundingBox bbox, boolean arePoisForSuggestions, String nameQuery) throws InterruptedException {
        List<Poi> expectedPois = allPois();
        ManyPoisQuery<List<Poi>> query = client.findWaypoints()
                .pois()
                .all();
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(ApiUrlParts.PathSegments.POIS);
        if (limit != null) {
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
            query.limit(limit);
        }
        if (bbox != null) {
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.BOUNDING_BOX, bbox.toString());
            query.enclosedInRectangle(bbox);
        }
        if (nameQuery != null) {
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.QUERY, nameQuery);
            query.withNameContaining(nameQuery);
        }
        if (arePoisForSuggestions) {
            expectedUrlBuilder.addPathSegment(ApiUrlParts.PathSegments.NAMES);
            query.forNameSuggestions();
            for (Poi poi : expectedPois) poi.setLocation(null);
            mockServer().enqueue(ResponseFile.POIS_ONLY_WITH_ID_NAME.toMockResponse());
        } else {
            query.complete();
            mockServer().enqueue(ResponseFile.POIS.toMockResponse());
        }
        List<Poi> actualPois = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualPois).isNotNull().isNotEmpty().hasSameSizeAs(expectedPois).containsExactlyElementsOf(expectedPois);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetPoisThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .pois()
                    .all()
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }


    @Test
    @Parameters(method = "nearWaypointsQueryParams")
    public void shouldGetNearWaypoints(Integer distance, Integer limit) throws InterruptedException {
        List<TypedWaypoint> expectedWaypoints = allWaypoints();
        mockServer().enqueue(ResponseFile.WAYPOINTS.toMockResponse());
        Coordinates location = newCoordinates(19.387591, -99.052734);
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(ApiUrlParts.PathSegments.NEAR)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, location.toString());
        ManyWaypointsLocationQuery<List<TypedWaypoint>> query = client
                .findWaypoints()
                .typed()
                .near(location);
        if (distance != null) {
            query.atMostDistance(distance);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.DISTANCE, distance.toString());
        }
        if (limit != null) {
            query.limit(limit);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
        }
        List<TypedWaypoint> actualWaypoints = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualWaypoints).isNotNull().isNotEmpty().hasSameSizeAs(expectedWaypoints).containsExactlyElementsOf(expectedWaypoints);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetNearWaypointsThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .typed()
                    .near(newCoordinates(19.387591, -99.052734))
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    @Parameters(method = "nearWaypointsQueryParams")
    public void shouldGetNearWalkways(Integer distance, Integer limit) throws InterruptedException {
        List<Walkway> expectedWalkways = allWalkways();
        mockServer().enqueue(ResponseFile.WALKWAYS.toMockResponse());
        Coordinates location = newCoordinates(19.387591, -99.052734);
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(ApiUrlParts.PathSegments.WALKWAYS)
                .addPathSegment(ApiUrlParts.PathSegments.NEAR)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, location.toString());
        ManyWaypointsLocationQuery<List<Walkway>> query = client.findWaypoints()
                .walkways()
                .near(location);
        if (distance != null) {
            query.atMostDistance(distance);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.DISTANCE, distance.toString());
        }
        if (limit != null) {
            query.limit(limit);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
        }
        List<Walkway> actualWalkways = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualWalkways).isNotNull().isNotEmpty().hasSameSizeAs(expectedWalkways).containsExactlyElementsOf(expectedWalkways);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetNearWalkwaysThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .walkways()
                    .near(newCoordinates(19.387591, -99.052734))
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    @Parameters(method = "nearWaypointsQueryParams")
    public void shouldGetNearPois(Integer distance, Integer limit) throws InterruptedException {
        List<Poi> expectedPois = allPois();
        mockServer().enqueue(ResponseFile.POIS.toMockResponse());
        Coordinates location = newCoordinates(19.387591, -99.052734);
        HttpUrl.Builder expectedUrlBuilder = mockServerUrl()
                .newBuilder()
                .addPathSegment(ApiUrlParts.PathSegments.WAYPOINTS)
                .addPathSegment(ApiUrlParts.PathSegments.POIS)
                .addPathSegment(ApiUrlParts.PathSegments.NEAR)
                .addQueryParameter(ApiUrlParts.QueryParams.LOCATION, location.toString());
        ManyWaypointsLocationQuery<List<Poi>> query = client.findWaypoints()
                .pois()
                .near(location);
        if (distance != null) {
            query.atMostDistance(distance);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.DISTANCE, distance.toString());
        }
        if (limit != null) {
            query.limit(limit);
            expectedUrlBuilder.addQueryParameter(ApiUrlParts.QueryParams.LIMIT, limit.toString());
        }
        List<Poi> actualPois = query.get();
        RecordedRequest request = mockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(HttpMethod.GET)
                .hasUrl(expectedUrlBuilder.build())
                .containsHeaders(pairOf(HttpHeader.ACCEPT, ContentType.APPLICATION_JSON.value()), pairOf(HttpHeader.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(HttpHeader.AUTHORIZATION, FAKE_AUTHENTICATION_HEADER_INFO));
        assertThat(actualPois).isNotNull().isNotEmpty().hasSameSizeAs(expectedPois).containsExactlyElementsOf(expectedPois);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetNearPoisThrowException(MockResponse mockResponse, Exception expectedException) {
        mockServer().enqueue(mockResponse);
        try {
            client.findWaypoints()
                    .pois()
                    .near(newCoordinates(19.387591, -99.052734))
                    .get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ex.getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    public Object[] limitsAndBoundingBoxes() {
        int limit = 2;
        BoundingBox boundingBox = newBoundingBox(50.88437, -78.10897, 20.88175, -11.00459);
        return new Object[][]{
                {limit, boundingBox},
                {limit, null},
                {null, boundingBox},
                {null, null}
        };
    }

    public Object[] manyPoisQueryParams() {
        int limit = 2;
        BoundingBox boundingBox = newBoundingBox(50.88437, -78.10897, 20.88175, -11.00459);
        boolean arePoisForSuggestions = false;
        String nameQuery = "q";
        return new Object[][]{
                {limit, boundingBox, arePoisForSuggestions, nameQuery},
                {limit, boundingBox, arePoisForSuggestions, null},
                {limit, boundingBox, !arePoisForSuggestions, nameQuery},
                {limit, boundingBox, !arePoisForSuggestions, null},
                {limit, null, arePoisForSuggestions, nameQuery},
                {limit, null, arePoisForSuggestions, null},
                {limit, null, !arePoisForSuggestions, nameQuery},
                {limit, null, !arePoisForSuggestions, null},
                {null, boundingBox, arePoisForSuggestions, nameQuery},
                {null, boundingBox, arePoisForSuggestions, null},
                {null, boundingBox, !arePoisForSuggestions, nameQuery},
                {null, boundingBox, !arePoisForSuggestions, null},
                {null, null, arePoisForSuggestions, nameQuery},
                {null, null, arePoisForSuggestions, null},
                {null, null, !arePoisForSuggestions, nameQuery},
                {null, null, !arePoisForSuggestions, null}
        };
    }

    public Object[] nearWaypointsQueryParams() {
        int distance = 100;
        int limit = 4;
        return new Object[][]{
                {distance, limit},
                {distance, null},
                {null, limit},
                {null, null},
        };
    }

    public Object[] mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected() {
        ApiErrorDetail badRequestErrorDetail = newApiErrorDetail(HttpStatus.BAD_REQUEST, "Parameter validation failed", ApiErrorDetail.ErrorType.PARAM_VALIDATION, singletonMap("arg0", "not valid"));
        ApiErrorDetail authorizationErrorDetail = newApiErrorDetail(HttpStatus.UNAUTHORIZED, "Unauthorized to use resource", ApiErrorDetail.ErrorType.AUTHORIZATION, null);
        ApiErrorDetail notFoundErrorDetail = newApiErrorDetail(HttpStatus.NOT_FOUND, "Resource not found", ApiErrorDetail.ErrorType.NOT_FOUND, null);
        ApiErrorDetail serverErrorDetail = newApiErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something bad happen in the server", ApiErrorDetail.ErrorType.SERVER_ERROR, null);
        ApiErrorDetail otherErrorDetail = newApiErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected error", ApiErrorDetail.ErrorType.OTHER, null);

        return new Object[][]{
                {ResponseFile.BAD_REQUEST_ERROR.toMockResponse(), new ApiException(badRequestErrorDetail)},
                {ResponseFile.AUTHORIZATION_FAILED_ERROR.toMockResponse(), new ApiException(authorizationErrorDetail)},
                {ResponseFile.NOT_FOUND_ERROR.toMockResponse(), new ApiException(notFoundErrorDetail)},
                {ResponseFile.SERVER_ERROR.toMockResponse(), new ApiException(serverErrorDetail)},
                {ResponseFile.OTHER_ERROR.toMockResponse(), new ApiException(otherErrorDetail)},
                {ResponseFile.EMPTY_OBJECT.toSlowMockResponse(), new HttpRequestException("Connection timeout")}
        };
    }

    private List<TypedWaypoint> allWaypoints() {
        TypedWaypoint waypoint1 = newTypedWaypoint(1L, null, TypedWaypoint.Type.WALKWAY, 19.387591, -99.052734);
        TypedWaypoint waypoint2 = newTypedWaypoint(2L, "A poi name", TypedWaypoint.Type.POI, 50.88437, -78.10897);
        TypedWaypoint waypoint3 = newTypedWaypoint(3L, "A second poi name", TypedWaypoint.Type.POI, 20.88175, -11.00459);
        TypedWaypoint waypoint4 = newTypedWaypoint(4L, null, TypedWaypoint.Type.WALKWAY, 54.45657, -108.787921);
        return asList(waypoint1, waypoint2, waypoint3, waypoint4);
    }

    private List<Walkway> allWalkways() {
        Walkway walkway1 = newWalkway(1L, 19.387591, -99.052734);
        Walkway walkway4 = newWalkway(4L, 54.45657, -108.787921);
        return asList(walkway1, walkway4);
    }

    private List<Poi> allPois() {
        Poi poi2 = newPoi(2L, "A poi name", 50.88437, -78.10897);
        Poi poi3 = newPoi(3L, "A second poi name", 20.88175, -11.00459);
        return asList(poi2, poi3);
    }

    private ApiErrorDetail newApiErrorDetail(HttpStatus status, String errorDetail, ApiErrorDetail.ErrorType errorType, Map<String, String> paramErrors) {
        ApiErrorDetail apiErrorDetail = new ApiErrorDetail();
        apiErrorDetail.setStatusCode(status.code());
        apiErrorDetail.setErrorDetail(errorDetail);
        apiErrorDetail.setErrorType(errorType);
        apiErrorDetail.setParamErrors(paramErrors);
        return apiErrorDetail;
    }

    private TypedWaypoint newTypedWaypoint(Long id, String name, TypedWaypoint.Type type, Double latitude, Double longitude) {
        TypedWaypoint waypoint = new TypedWaypoint();
        initBaseWaypoint(waypoint, id, latitude, longitude);
        waypoint.setType(type);
        waypoint.setName(name);
        return waypoint;
    }

    private Walkway newWalkway(Long id, Double latitude, Double longitude) {
        Walkway walkway = new Walkway();
        initBaseWaypoint(walkway, id, latitude, longitude);
        return walkway;
    }

    private Poi newPoi(Long id, String name, Double latitude, Double longitude) {
        Poi poi = new Poi();
        initBaseWaypoint(poi, id, latitude, longitude);
        poi.setName(name);
        return poi;
    }

    private void initBaseWaypoint(BaseWaypoint baseWaypoint, Long id, Double latitude, Double longitude) {
        baseWaypoint.setId(id);
        if (latitude != null || longitude != null) {
            Coordinates location = new Coordinates();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            baseWaypoint.setLocation(location);
        }
    }

    private Coordinates newCoordinates(Double latitude, Double longitude) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        return coordinates;
    }

    private BoundingBox newBoundingBox(Double topLatitude, Double topLongitude, Double bottomLatitude, Double bottomLongitude) {
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setTopCoordinates(newCoordinates(topLatitude, topLongitude));
        boundingBox.setBottomCoordinates(newCoordinates(bottomLatitude, bottomLongitude));
        return boundingBox;
    }
}
