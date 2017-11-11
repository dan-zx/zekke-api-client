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

import static java.util.Collections.singletonMap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ApiExceptionTest {

    @Test
    public void shouldHaveMessageAndErrorDetails() {
        ApiErrorDetail apiErrorDetail = new ApiErrorDetail();
        apiErrorDetail.setStatusCode(500);
        apiErrorDetail.setErrorDetail("Generic error");
        apiErrorDetail.setErrorType(ApiErrorDetail.ErrorType.OTHER);
        apiErrorDetail.setParamErrors(singletonMap("arg0", "value0"));

        assertThat(new ApiException(apiErrorDetail)).hasNoCause().hasMessage(apiErrorDetail.getErrorDetail()).extracting("apiErrorDetail").containsOnly(apiErrorDetail);
    }
}
