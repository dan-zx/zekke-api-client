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
