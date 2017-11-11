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

/**
 * Common HTTP headers.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public enum HttpHeader {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_DATETIME("Accept-Datetime"),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-HttpMethod"),
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-HttpHeader"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    COOKIE("Cookie"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    ETAG("ETag"),
    EXPECT("Expect"),
    FORWARDED("Forwarded"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    LAST_MODIFIED("Last-Modified"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PRAGMA("Pragma"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    USER_AGENT("User-Agent"),
    UPGRADE("User-Upgrade"),
    VIA("Via"),
    WARNING("Warning");

    private final String value;

    HttpHeader(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
