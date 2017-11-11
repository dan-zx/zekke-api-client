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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.github.danzx.zekke.client.api.rest.test.mock.http.HttpMockTest;
import com.github.danzx.zekke.client.api.rest.test.mock.http.ResponseFile;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;

import com.google.gson.reflect.TypeToken;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import okhttp3.Request;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class HttpJsonClientTest extends HttpMockTest {

    private static final Type ARRAY_OF_WAYPOINTS_TYPE = TypeToken.getParameterized(ArrayList.class, TypedWaypoint.class).getType();
    private static final Type WAYPOINT_TYPE           = TypeToken.get(TypedWaypoint.class).getType();

    @Test
    @Parameters(method = "responsesWithExpectedObjects")
    public void shouldExecuteRequestForObjectReturnExpectedObjectWhitResponse(ResponseFile responseFile, Type returnType, Object expected) {
        mockServer().enqueue(responseFile.toMockResponse());
        Request request = new Request.Builder()
                .url(mockServerUrl())
                .get()
                .build();
        Object actual = mockHttpClient().executeRequestForObject(request, returnType);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldExecuteRequestNoResponseReturnHttpStatus() {
        mockServer().enqueue(ResponseFile.NO_CONTENT.toMockResponse());
        Request request = new Request.Builder()
                .url(mockServerUrl())
                .get()
                .build();
        HttpStatus httpStatus = mockHttpClient().executeRequestNoResponse(request);
        assertThat(httpStatus).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldExecuteRequestNoResponseThrowHttpRequestExceptionWhenConnectionTimeOut() {
        mockServer().enqueue(ResponseFile.SERVER_ERROR.toSlowMockResponse());
        Request request = new Request.Builder()
                .url(mockServerUrl())
                .get()
                .build();
        try {
            mockHttpClient().executeRequestNoResponse(request);
            failBecauseExceptionWasNotThrown(HttpRequestException.class);
        } catch (HttpRequestException ex) {
            assertThat(ex).hasMessage("Connection timeout");
        }
    }

    @Test
    public void shouldExecuteRequestForObjectThrowHttpRequestExceptionWhenConnectionTimeOut() {
        mockServer().enqueue(ResponseFile.SERVER_ERROR.toSlowMockResponse());
        Request request = new Request.Builder()
                .url(mockServerUrl())
                .get()
                .build();
        try {
            mockHttpClient().executeRequestForObject(request, ARRAY_OF_WAYPOINTS_TYPE);
            failBecauseExceptionWasNotThrown(HttpRequestException.class);
        } catch (HttpRequestException ex) {
            assertThat(ex).hasMessage("Connection timeout");
        }
    }

    @Test
    public void shouldExecuteRequestForObjectThrowHttpResponseBodyExceptionWhenResponseWontMatchExpectedType() {
        mockServer().enqueue(ResponseFile.EMPTY_OBJECT.toMockResponse());
        Request request = new Request.Builder()
                .url(mockServerUrl())
                .get()
                .build();
        try {
            mockHttpClient().executeRequestForObject(request, ARRAY_OF_WAYPOINTS_TYPE);
            failBecauseExceptionWasNotThrown(HttpResponseBodyException.class);
        } catch (HttpResponseBodyException ex) {
            assertThat(ex).hasMessage("Failed to convert response body to object");
        }
    }

    public Object[] responsesWithExpectedObjects() {
        TypedWaypoint waypoint1 = newTypedWaypoint(1L, null, TypedWaypoint.Type.WALKWAY, 19.387591, -99.052734);
        TypedWaypoint waypoint2 = newTypedWaypoint(2L, "A poi name", TypedWaypoint.Type.POI, 50.88437, -78.10897);
        TypedWaypoint waypoint3 = newTypedWaypoint(3L, "A second poi name", TypedWaypoint.Type.POI, 20.88175, -11.00459);
        TypedWaypoint waypoint4 = newTypedWaypoint(4L, null, TypedWaypoint.Type.WALKWAY, 54.45657, -108.787921);
        List<TypedWaypoint> waypoints = asList(waypoint1, waypoint2, waypoint3, waypoint4);
        return new Object[][]{
                {ResponseFile.ONE_WAYPOINT, WAYPOINT_TYPE, waypoint2},
                {ResponseFile.EMPTY_OBJECT, WAYPOINT_TYPE, new TypedWaypoint()},
                {ResponseFile.NO_CONTENT, WAYPOINT_TYPE, null},
                {ResponseFile.WAYPOINTS, ARRAY_OF_WAYPOINTS_TYPE, waypoints},
                {ResponseFile.EMPTY_ARRAY, ARRAY_OF_WAYPOINTS_TYPE, emptyList()},
                {ResponseFile.NO_CONTENT, ARRAY_OF_WAYPOINTS_TYPE, null}
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
}
