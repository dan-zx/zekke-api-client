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
package com.github.danzx.zekke.client.api.rest.test.assertion;

import static com.github.danzx.zekke.client.api.rest.test.util.Pair.pairOf;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.danzx.zekke.client.api.rest.http.HttpHeader;
import com.github.danzx.zekke.client.api.rest.http.HttpMethod;
import com.github.danzx.zekke.client.api.rest.test.util.Pair;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.RecordedRequest;

import org.assertj.core.api.AbstractAssert;

public class RecordedRequestAssert extends AbstractAssert<RecordedRequestAssert, RecordedRequest> {

    public RecordedRequestAssert(RecordedRequest actual) {
        super(actual, RecordedRequestAssert.class);
    }

    public RecordedRequestAssert hasMethod(HttpMethod method) {
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
    public final RecordedRequestAssert containsHeaders(Pair<HttpHeader, String>... headers) {
        isNotNull();
        List<Pair<HttpHeader, String>> found = new ArrayList<>();
        boolean failedValidation = false;
        for (Pair<HttpHeader, String> headerInfo : headers) {
            String actualHeaderValue = actual.getHeader(headerInfo._0.toString());
            found.add(pairOf(headerInfo._0, actualHeaderValue));
            if (!Objects.equals(actualHeaderValue, headerInfo._1)) failedValidation = true;
        }
        if (failedValidation) failWithMessage("Expected to have request's headers to contain <%s> but found <%s>", Arrays.toString(headers), found);
        return this;
    }
}
