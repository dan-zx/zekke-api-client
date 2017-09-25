package com.github.danzx.zekke.client.test;

import com.github.danzx.zekke.client.http.HttpClient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public abstract class HttpMockTest {

    private static final String MOCK_PATH = "/zekke/api/mock";
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR;
    private static MockWebServer mockServer;
    private static HttpUrl mockServerUrl;

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

    private HttpClient mockHttpClient;

    @BeforeClass
    public static void onBeforeAllTests() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        mockServerUrl = mockServer.url(MOCK_PATH);
    }

    @AfterClass
    public static void onAfterAllTests() throws IOException {
        mockServer.shutdown();
    }

    @Before
    public void onBeforeEachTest() {
        mockHttpClient = new HttpClient();
        mockHttpClient.setBaseUrl(mockServerUrl);
        mockHttpClient.setHttpClient(
                new OkHttpClient.Builder()
                        .addInterceptor(LOGGING_INTERCEPTOR)
                        .readTimeout(1, TimeUnit.SECONDS)
                        .build());
    }

    @After
    public void onAfterEachTest() { }

    protected static void enqueueMockResponse(MockResponse mockResponse) {
        mockServer.enqueue(mockResponse);
    }

    protected HttpClient getMockHttpClient() {
        return mockHttpClient;
    }
}
