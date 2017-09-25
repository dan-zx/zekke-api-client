package com.github.danzx.zekke.client.http;

import okhttp3.MediaType;

public class MediaTypes {

    public final static MediaType APPLICATION_JSON_PATCH = MediaType.parse("application/json+patch");
    public final static MediaType APPLICATION_JSON = MediaType.parse("application/json");

    private MediaTypes() {
        throw new AssertionError();
    }
}
