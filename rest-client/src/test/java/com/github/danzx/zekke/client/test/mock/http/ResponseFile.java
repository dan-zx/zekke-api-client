package com.github.danzx.zekke.client.test.mock.http;

import com.github.danzx.zekke.client.http.ContentType;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpStatus;
import com.github.danzx.zekke.client.test.util.Files;

import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;

public enum ResponseFile {
    ACCESS_TOKEN("access_token.json", HttpStatus.OK),
    ONE_WAYPOINT("waypoint.json", HttpStatus.OK),
    WAYPOINTS("waypoints.json", HttpStatus.OK),
    POIS("pois.json", HttpStatus.OK),
    POIS_ONLY_WITH_ID_NAME("pois_id-name.json", HttpStatus.OK),
    WALWAYS("walkways.json", HttpStatus.OK),
    NO_CONTENT("empty", HttpStatus.NO_CONTENT),
    BAD_REQUEST_ERROR("bad_request_error.json", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_FAILED_ERROR("authorization_error.json", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_ERROR("not_found_error.json", HttpStatus.NOT_FOUND),
    SERVER_ERROR("server_error.json", HttpStatus.INTERNAL_SERVER_ERROR),
    OTHER_ERROR("other_error.json", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_OBJECT("empty_object.json", HttpStatus.OK);

    private static final String LOCATION_PREFIX = "/responses/";

    private final String location;
    private final HttpStatus status;

    ResponseFile(String fileName, HttpStatus status) {
        location = LOCATION_PREFIX + fileName;
        this.status = status;
    }

    public MockResponse toMockResponse() {
        String body = Files.readFromClasspath(location);
        return new MockResponse()
                .setStatus(status.toString())
                .setHeader(Header.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.value())
                .setBody(body);
    }

    public MockResponse toSlowMockResponse() {
        return toMockResponse()
                .throttleBody(1,1, TimeUnit.SECONDS);
    }
}
