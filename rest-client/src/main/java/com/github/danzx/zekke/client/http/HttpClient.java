package com.github.danzx.zekke.client.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.exception.ApiException;
import com.github.danzx.zekke.client.util.Charset;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.util.Objects.requireNonNull;

public class HttpClient {

    private final Gson gson;
    private final OkHttpClient httpClient;
    private final HttpUrl baseUrl;

    public HttpClient(OkHttpClient httpClient, HttpUrl baseUrl) {
        this.httpClient = requireNonNull(httpClient);
        this.baseUrl = requireNonNull(baseUrl);
        gson = new Gson();
    }

    public HttpUrl.Builder newBaseBuilder() {
        return baseUrl.newBuilder();
    }

    public <T> T doGetForJson(Request request, TypeToken<T> type) {
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.body().contentLength() == 0L) throw new HttpResponseBodyException("Expecting to have response with body");
            return parseResponse(response, type);
        } catch (SocketTimeoutException ex) {
            throw new HttpRequestException("Connection timeout", ex);
        } catch (IOException ex) {
            throw new HttpRequestException("Failed to perform request", ex);
        } finally {
            if (response != null) response.close();
        }
    }

    private <T> T parseResponse(Response response, TypeToken<T> type) throws ApiException, HttpResponseBodyException {
        try {
            String json = response.body().string();
            if (response.isSuccessful()) return gson.fromJson(json, type.getType());
            else {
                ApiErrorDetail error = gson.fromJson(json, ApiErrorDetail.class);
                throw new ApiException(error);
            }
        } catch (IOException ex) {
            throw new HttpResponseBodyException("Failed to convert response body to string", ex);
        }
    }

    public Request.Builder newBaseRequestBuilderForJsonResponse(HttpUrl url) {
        return new Request.Builder()
                .url(url)
                .header(Header.ACCEPT.toString(), ContentType.APPLICATION_JSON.value())
                .header(Header.ACCEPT_LANGUAGE.toString(), Locale.getDefault().getLanguage())
                .header(Header.ACCEPT_CHARSET.toString(), Charset.UTF_8.toString());
    }
}
