package com.github.danzx.zekke.client.test;

import com.github.danzx.zekke.client.http.ContentType;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpStatus;

import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;

public enum ResponseFile {
    ACCESS_TOKEN("access_token.json", HttpStatus.OK),
    EMPTY("empty", HttpStatus.NO_CONTENT),
    GENERIC_ERROR("generic_error.json", HttpStatus.INTERNAL_SERVER_ERROR);

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
                .setHeader(Header.CONTENT_TYPE.toString(), ContentType.APPLICATION_JSON.getValue())
                .setBody(body);
    }

    public MockResponse toSlowMockResponse() {
        return toMockResponse()
                .throttleBody(1,1, TimeUnit.SECONDS);
    }
}
