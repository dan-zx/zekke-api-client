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
package com.github.danzx.zekke.client.api.rest.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Test;

public class HttpStatusTest {

    @Test
    public void shouldFromCodeReturnConstantWhenCodeExists() {
        HttpStatus expected = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
        HttpStatus actual = HttpStatus.fromCode(expected.code());
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    public void shouldFromCodeThrowIllegalArgumentExceptionWhen() {
        final int unknownCode = -1;
        try {
            HttpStatus.fromCode(unknownCode);
            failBecauseExceptionWasNotThrown(IllegalAccessException.class);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasMessage("No matching constant for [" + unknownCode + "]");
        }
    }
}
