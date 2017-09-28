package com.github.danzx.zekke.client.test.assertion;

import org.assertj.core.api.Assertions;

import okhttp3.mockwebserver.RecordedRequest;

public class ProjectAssertions extends Assertions {

    public static RecordedRequestAssert assertThat(RecordedRequest actual) {
        return new RecordedRequestAssert(actual);
    }
}
