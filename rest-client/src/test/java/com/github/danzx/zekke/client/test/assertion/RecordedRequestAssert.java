package com.github.danzx.zekke.client.test.assertion;

import static com.github.danzx.zekke.client.test.util.Pair.pairOf;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.Method;
import com.github.danzx.zekke.client.test.util.Pair;

import org.assertj.core.api.AbstractAssert;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.RecordedRequest;

public class RecordedRequestAssert extends AbstractAssert<RecordedRequestAssert, RecordedRequest> {

    public RecordedRequestAssert(RecordedRequest actual) {
        super(actual, RecordedRequestAssert.class);
    }

    public RecordedRequestAssert hasMethod(Method method) {
        isNotNull();
        String expectedMethod = Objects.toString(method, null);
        assertThat(actual.getMethod()).isEqualTo(expectedMethod);
        return this;
    }

    public RecordedRequestAssert hasUrl(HttpUrl url) {
        isNotNull();
        HttpUrl actualUrl = actual.getRequestUrl();
        assertPath(actualUrl, url);
        assertQuery(actualUrl, url);
        return this;
    }

    private void assertPath(HttpUrl actualUrl, HttpUrl expectedUrl) {
        assertThat(actualUrl.encodedPath()).isEqualTo(expectedUrl.encodedPath());
    }

    private void assertQuery(HttpUrl actualUrl, HttpUrl expectedUrl) {
        Map<String, List<String>> actualParams = convertHttpUrlQueryToMap(actualUrl);
        Map<String, List<String>> expectedParams = convertHttpUrlQueryToMap(expectedUrl);
        assertThat(actualParams).hasSameSizeAs(expectedParams).isEqualTo(expectedParams);
    }

    private Map<String, List<String>> convertHttpUrlQueryToMap(HttpUrl url) {
        Map<String, List<String>> params = new HashMap<>();
        for (String paramName : url.queryParameterNames()) {
            params.put(paramName, url.queryParameterValues(paramName));
        }
        return params;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final RecordedRequestAssert containsHeaders(Pair<Header, String>... headers) {
        isNotNull();
        List<Pair<Header, String>> found = new ArrayList<>();
        boolean failedValidation = false;
        for (Pair<Header, String> headerInfo : headers) {
            String actualHeaderValue = actual.getHeader(headerInfo._0.toString());
            found.add(pairOf(headerInfo._0, actualHeaderValue));
            if (!Objects.equals(actualHeaderValue, headerInfo._1)) failedValidation = true;
        }
        if (failedValidation) failWithMessage("Expected to have request's headers to contain <%s> but found <%s>", Arrays.toString(headers), found);
        return this;
    }
}
