package com.github.danzx.zekke.client.test;

import com.github.danzx.zekke.client.http.HttpClient;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;

public abstract class HttpMockTest {

    private static final String MOCK_PATH = "/zekke/api/mock";
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR;

    static {
        final Logger interceptorLogger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);
        LOGGING_INTERCEPTOR = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                interceptorLogger.debug(message);
            }
        });
        LOGGING_INTERCEPTOR.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private MockWebServer mockServer;
    private HttpUrl mockServerUrl;
    private HttpClient mockHttpClient;

    @Before
    public void onBeforeEachTest() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
        mockServerUrl = mockServer.url(MOCK_PATH);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(LOGGING_INTERCEPTOR)
                .readTimeout(1, TimeUnit.SECONDS)
                .build();
        mockHttpClient = new HttpClient(okHttpClient, mockServerUrl);
    }

    @After
    public void onAfterEachTest() throws Exception {
        mockServer.shutdown();
    }

    protected MockWebServer getMockServer() {
        return mockServer;
    }

    protected HttpClient getMockHttpClient() {
        return mockHttpClient;
    }
}
