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
package com.github.danzx.zekke.client.core.exception;

import com.github.danzx.zekke.client.core.exception.ApiErrorDetail.ErrorType;

import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiErrorDetailTest {

    private static final ApiErrorDetail TESTEE = newCoordinates();

    @Test
    public void shouldEqualsBeTrueWhenSameReference() {
        assertThat(TESTEE.equals(TESTEE)).isTrue();
    }

    @Test
    public void shouldEqualsBeTrueWhenObjectsAreNotTheSameReference() {
        assertThat(TESTEE.equals(newCoordinates())).isTrue();
    }

    @Test
    public void shouldEqualsBeFalseWhenNull() {
        assertThat(TESTEE.equals(null)).isFalse();
    }

    @Test
    public void shouldEqualsBeFalseWhenComparingWithDifferentObject() {
        assertThat(TESTEE.equals(new Object())).isFalse();
    }

    @Test
    public void shouldEqualsBeFalseWhenAtLeastOnePropertyIsDifferent() {
        ApiErrorDetail other = newCoordinates();
        other.setStatusCode(404);
        assertThat(TESTEE.equals(other)).isFalse();

        other = newCoordinates();
        other.setErrorDetail("Not found");
        assertThat(TESTEE.equals(other)).isFalse();

        other = newCoordinates();
        other.setErrorType(ErrorType.RESOURCE_NOT_FOUND);
        assertThat(TESTEE.equals(other)).isFalse();

        other = newCoordinates();
        other.setParamErrors(singletonMap("url", "invalid"));
        assertThat(TESTEE.equals(other)).isFalse();
    }

    @Test
    public void shouldHashCodeBeEqualWhenSameObjectReference() {
        ApiErrorDetail other = newCoordinates();
        assertThat(TESTEE.hashCode()).isEqualTo(TESTEE.hashCode()).isEqualTo(other.hashCode());
    }

    private static ApiErrorDetail newCoordinates() {
        ApiErrorDetail apiErrorDetail = new ApiErrorDetail();
        apiErrorDetail.setStatusCode(500);
        apiErrorDetail.setErrorDetail("Generic error");
        apiErrorDetail.setErrorType(ErrorType.OTHER);
        apiErrorDetail.setParamErrors(singletonMap("arg0", "value0"));
        return apiErrorDetail;
    }
}
