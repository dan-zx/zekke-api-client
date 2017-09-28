package com.github.danzx.zekke.client.http;

import okhttp3.MediaType;

public enum ContentType {

    ALL("*/*"),
    APPLICATION_ATOM_XML("application/atom+xml"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_JSON("application/json"),
    APPLICATION_JSON_PATCH("application/json+patch"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_XML("application/xml"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml");

    private final String value;
    private final MediaType mediaType;

    ContentType(String value) {
        this.value = value;
        mediaType = MediaType.parse(this.value);
    }

    public String value() {
        return value;
    }

    public MediaType mediaType() {
        return mediaType;
    }
}
