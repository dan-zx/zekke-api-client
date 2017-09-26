package com.github.danzx.zekke.client.http;

public class HttpRequestException extends RuntimeException {

    private static final long serialVersionUID = 726834298745937691L;

    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }
}
