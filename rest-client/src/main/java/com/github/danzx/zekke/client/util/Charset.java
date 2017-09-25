package com.github.danzx.zekke.client.util;

public enum Charset {

    ISO_8859_1("ISO-8859-1"),
    US_ASCII("US-ASCII"),
    UTF_8("UTF-8"),
    UTF_16("UTF-16"),
    UTF_16BE("UTF-16BE"),
    UTF_16LE("UTF-16LE");

    private final String value;

    Charset(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
