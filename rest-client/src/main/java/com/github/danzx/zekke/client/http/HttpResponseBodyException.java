package com.github.danzx.zekke.client.http;

public class HttpResponseBodyException extends RuntimeException {

    public HttpResponseBodyException(String message) {
        super(message);
    }

    public HttpResponseBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpResponseBodyException(Throwable cause) {
        super(cause);
    }
}
