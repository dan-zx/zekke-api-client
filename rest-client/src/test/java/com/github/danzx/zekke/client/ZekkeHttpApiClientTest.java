package com.github.danzx.zekke.client;

import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.exception.ApiException;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.ContentType;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpRequestException;
import com.github.danzx.zekke.client.http.HttpResponseBodyException;
import com.github.danzx.zekke.client.http.Method;
import com.github.danzx.zekke.client.test.HttpMockTest;
import com.github.danzx.zekke.client.test.ResponseFile;
import com.github.danzx.zekke.client.util.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@RunWith(JUnitParamsRunner.class)
public class ZekkeHttpApiClientTest extends HttpMockTest {

    private ZekkeHttpApiClient client;

    @Override
    public void onBeforeEachTest() throws Exception {
        super.onBeforeEachTest();
        client = new ZekkeHttpApiClient(getMockHttpClient());
    }

    @Test
    public void shouldGetAnonymousAccessToken() throws InterruptedException {
        getMockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client.authenticateAnonymously().get();
        RecordedRequest request = getMockServer().takeRequest();
        assertThat(request.getMethod()).isEqualTo(Method.GET.toString());
        assertThat(request.getPath()).endsWith("/authentication/jwt/anonymous");
        assertThat(request.getHeader(Header.ACCEPT.toString())).isEqualTo(ContentType.APPLICATION_JSON.getValue());
        assertThat(request.getHeader(Header.ACCEPT_CHARSET.toString())).isEqualTo(Charset.UTF_8.toString());
        assertThat(accessTokenHolder).isNotNull().extracting("accessToken").containsExactly("token");
    }

    @Test
    @Parameters(method = "accessTokenResponsesAndExceptionsExpected")
    public void shouldGetAnonymousAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        getMockServer().enqueue(mockResponse);
        try {
            client.authenticateAnonymously().get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
            if (ex instanceof ApiException) {
                ApiErrorDetail expectedApiErrorDetail = ((ApiException)expectedException).getApiErrorDetail();
                ApiErrorDetail actualApiErrorDetail = ((ApiException)ex).getApiErrorDetail();
                assertThat(actualApiErrorDetail).isEqualTo(expectedApiErrorDetail);
            }
        }
    }

    @Test
    public void shouldGetAdminAccessToken() throws InterruptedException {
        getMockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client.authenticateAdmin("myUserId", "myPassword").get();
        RecordedRequest request = getMockServer().takeRequest();
        assertThat(request.getMethod()).isEqualTo(Method.GET.toString());
        assertThat(request.getPath()).endsWith("/authentication/jwt/admin");
        assertThat(request.getHeader(Header.ACCEPT.toString())).isEqualTo(ContentType.APPLICATION_JSON.getValue());
        assertThat(request.getHeader(Header.ACCEPT_CHARSET.toString())).isEqualTo(Charset.UTF_8.toString());
        assertThat(request.getHeader(Header.AUTHORIZATION.toString())).isEqualTo("Basic bXlVc2VySWQ6bXlQYXNzd29yZA==");
        assertThat(accessTokenHolder).isNotNull().extracting("accessToken").containsExactly("token");
    }

    @Test
    @Parameters(method = "accessTokenResponsesAndExceptionsExpected")
    public void shouldGetAdminAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        getMockServer().enqueue(mockResponse);
        try {
            client.authenticateAdmin("myUserId", "myPassword").get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
            if (ex instanceof ApiException) {
                ApiErrorDetail expectedApiErrorDetail = ((ApiException)expectedException).getApiErrorDetail();
                ApiErrorDetail actualApiErrorDetail = ((ApiException)ex).getApiErrorDetail();
                assertThat(actualApiErrorDetail).isEqualTo(expectedApiErrorDetail);
            }
        }
    }

    public Object[] accessTokenResponsesAndExceptionsExpected() {
        ApiErrorDetail genericErrorDetail = new ApiErrorDetail();
        genericErrorDetail.setStatusCode(500);
        genericErrorDetail.setErrorDetail("Generic error");
        genericErrorDetail.setErrorType(ApiErrorDetail.ErrorType.SERVER_ERROR);
        genericErrorDetail.setParamErrors(singletonMap("arg0", "error0"));
        return new Object[][] {
                {ResponseFile.ACCESS_TOKEN.toSlowMockResponse(), new HttpRequestException("Connection timeout")},
                {ResponseFile.EMPTY.toMockResponse(), new HttpResponseBodyException("Expecting to have response with body")},
                {ResponseFile.GENERIC_ERROR.toMockResponse(), new ApiException(genericErrorDetail)}
        };
    }
}
