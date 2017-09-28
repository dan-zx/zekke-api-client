package com.github.danzx.zekke.client.test.assertion;

import com.github.danzx.zekke.client.http.Header;
import com.github.danzx.zekke.client.http.Method;
import com.github.danzx.zekke.client.test.util.Pair;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.StringAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.mockwebserver.RecordedRequest;

import static com.github.danzx.zekke.client.test.util.Pair.pairOf;

public class RecordedRequestAssert extends AbstractAssert<RecordedRequestAssert, RecordedRequest> {

    public RecordedRequestAssert(RecordedRequest actual) {
        super(actual, RecordedRequestAssert.class);
    }

    public RecordedRequestAssert hasMethod(Method method) {
        isNotNull();
        String actualMethod = actual.getMethod();
        String expectedMethod = Objects.toString(method, null);
        new StringAssert(actualMethod).isEqualTo(expectedMethod).overridingErrorMessage("Expected to have request's method to be <%s> but was <%s>", expectedMethod, actualMethod);
        return this;
    }

    public RecordedRequestAssert hasPathEndingWith(String suffix) {
        isNotNull();
        String actualPath = actual.getPath();
        new StringAssert(actualPath).endsWith(suffix).overridingErrorMessage("Expected to have request's path to end with <%s> but was <%s>", suffix, actualPath);
        return this;
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
