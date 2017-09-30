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
package com.github.danzx.zekke.client.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Test;

public class Base64Test {

    @Test
    public void shouldEncodeDecodeText() {
        final byte[] data = "this is my taunt".getBytes();

        byte[] encoded = Base64.encode(data);
        assertThat(encoded).isNotNull().isNotEmpty();

        String encodedText = new String(encoded);
        assertThat(encodedText).isNotNull().isNotEmpty().isEqualTo("dGhpcyBpcyBteSB0YXVudA==");

        byte[] decoded = Base64.decode(encoded);
        assertThat(decoded).isNotNull().isNotEmpty().isEqualTo(data);
    }

    @Test
    public void shouldEncodeThrowNullPointerExceptionWhenByteDataIsNull() {
        try {
            Base64.encode(null);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException ex) {
            assertThat(ex).hasMessage("byteData cannot be null");
        }
    }

    @Test
    public void shouldDecodeThrowNullPointerExceptionWhenByteDataIsNull() {
        try {
            Base64.decode(null);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (NullPointerException ex) {
            assertThat(ex).hasMessage("byteData cannot be null");
        }
    }
}
