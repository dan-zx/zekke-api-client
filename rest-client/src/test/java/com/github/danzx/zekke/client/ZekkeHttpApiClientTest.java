package com.github.danzx.zekke.client;

import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.exception.ApiException;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.http.ContentType;
import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.HttpRequestException;
import com.github.danzx.zekke.client.http.HttpResponseBodyException;
import com.github.danzx.zekke.client.http.HttpStatus;
import com.github.danzx.zekke.client.http.Method;
import com.github.danzx.zekke.client.test.mock.http.HttpMockTest;
import com.github.danzx.zekke.client.test.mock.http.ResponseFile;
import com.github.danzx.zekke.client.util.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.github.danzx.zekke.client.test.assertion.ProjectAssertions.assertThat;
import static com.github.danzx.zekke.client.test.assertion.ProjectAssertions.failBecauseExceptionWasNotThrown;
import static com.github.danzx.zekke.client.test.util.Pair.pairOf;
import static java.util.Collections.singletonMap;

@RunWith(JUnitParamsRunner.class)
public class ZekkeHttpApiClientTest extends HttpMockTest {

    private ZekkeHttpApiClient client;
    private AccessTokenHolder fakeAccessTokenHolder;


    @Override
    public void onBeforeEachTest() throws Exception {
        super.onBeforeEachTest();
        client = new ZekkeHttpApiClient(getMockHttpClient());
        fakeAccessTokenHolder = new AccessTokenHolder();
        fakeAccessTokenHolder.setAccessToken("token");
    }

    @Test
    public void shouldGetAnonymousAccessToken() throws InterruptedException {
        getMockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client.authenticateAnonymously().get();
        RecordedRequest request = getMockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(Method.GET)
                .hasPathEndingWith("/authentication/jwt/anonymous")
                .containsHeaders(pairOf(Header.ACCEPT, ContentType.APPLICATION_JSON.getValue()), pairOf(Header.ACCEPT_CHARSET, Charset.UTF_8.toString()));
        assertThat(accessTokenHolder).isNotNull().isEqualTo(fakeAccessTokenHolder);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetAnonymousAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        getMockServer().enqueue(mockResponse);
        try {
            client.authenticateAnonymously().get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ((ApiException) expectedException).getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    @Test
    public void shouldGetAdminAccessToken() throws InterruptedException {
        getMockServer().enqueue(ResponseFile.ACCESS_TOKEN.toMockResponse());
        AccessTokenHolder accessTokenHolder = client.authenticateAdmin("myUserId", "myPassword").get();
        RecordedRequest request = getMockServer().takeRequest();
        assertThat(request).isNotNull()
                .hasMethod(Method.GET)
                .hasPathEndingWith("/authentication/jwt/admin")
                .containsHeaders(pairOf(Header.ACCEPT, ContentType.APPLICATION_JSON.getValue()), pairOf(Header.ACCEPT_CHARSET, Charset.UTF_8.toString()), pairOf(Header.AUTHORIZATION, "Basic bXlVc2VySWQ6bXlQYXNzd29yZA=="));
        assertThat(accessTokenHolder).isNotNull().isEqualTo(fakeAccessTokenHolder);
    }

    @Test
    @Parameters(method = "mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected")
    public void shouldGetAdminAccessTokenThrowException(MockResponse mockResponse, Exception expectedException) {
        getMockServer().enqueue(mockResponse);
        try {
            client.authenticateAdmin("myUserId", "myPassword").get();
            failBecauseExceptionWasNotThrown(expectedException.getClass());
        } catch (ApiException ex) {
            assertThat(ex).hasMessage(expectedException.getMessage());
            ApiErrorDetail expectedApiErrorDetail = ((ApiException) expectedException).getApiErrorDetail();
            assertThat(ex.getApiErrorDetail()).isEqualTo(expectedApiErrorDetail);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(expectedException.getClass()).hasMessage(expectedException.getMessage());
        }
    }

    public Object[] mockResponsesAndExceptionsExpectedWhenJsonBodyIsExpected() {
        ApiErrorDetail badRequestErrorDetail = new ApiErrorDetail();
        badRequestErrorDetail.setStatusCode(HttpStatus.BAD_REQUEST.value());
        badRequestErrorDetail.setErrorDetail("Parameter validation failed");
        badRequestErrorDetail.setErrorType(ApiErrorDetail.ErrorType.PARAM_VALIDATION);
        badRequestErrorDetail.setParamErrors(singletonMap("arg0", "not valid"));

        ApiErrorDetail authorizationErrorDetail = new ApiErrorDetail();
        authorizationErrorDetail.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        authorizationErrorDetail.setErrorDetail("Unauthorized to use resource");
        authorizationErrorDetail.setErrorType(ApiErrorDetail.ErrorType.AUTHORIZATION);

        ApiErrorDetail notFoundErrorDetail = new ApiErrorDetail();
        notFoundErrorDetail.setStatusCode(HttpStatus.NOT_FOUND.value());
        notFoundErrorDetail.setErrorDetail("Resource not found");
        notFoundErrorDetail.setErrorType(ApiErrorDetail.ErrorType.NOT_FOUND);

        ApiErrorDetail serverErrorDetail = new ApiErrorDetail();
        serverErrorDetail.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        serverErrorDetail.setErrorDetail("Something bad happen in the server");
        serverErrorDetail.setErrorType(ApiErrorDetail.ErrorType.SERVER_ERROR);

        ApiErrorDetail otherErrorDetail = new ApiErrorDetail();
        otherErrorDetail.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        otherErrorDetail.setErrorDetail("Unexpected error");
        otherErrorDetail.setErrorType(ApiErrorDetail.ErrorType.OTHER);

        return new Object[][] {
                {ResponseFile.BAD_REQUEST_ERROR.toMockResponse(), new ApiException(badRequestErrorDetail)},
                {ResponseFile.AUTHORIZATION_FAILED_ERROR.toMockResponse(), new ApiException(authorizationErrorDetail)},
                {ResponseFile.NOT_FOUND_ERROR.toMockResponse(), new ApiException(notFoundErrorDetail)},
                {ResponseFile.SERVER_ERROR.toMockResponse(), new ApiException(serverErrorDetail)},
                {ResponseFile.OTHER_ERROR.toMockResponse(), new ApiException(otherErrorDetail)},
                {ResponseFile.EMPTY_OBJECT.toSlowMockResponse(), new HttpRequestException("Connection timeout")},
                {ResponseFile.NO_CONTENT.toMockResponse(), new HttpResponseBodyException("Expecting to have response with body")}
        };
    }
}
