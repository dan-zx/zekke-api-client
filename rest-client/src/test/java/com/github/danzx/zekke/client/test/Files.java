package com.github.danzx.zekke.client.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Files {

    private Files() {
        throw new AssertionError();
    }

    public static String readFromClasspath(String location) {
        InputStream stream = Files.class.getResourceAsStream(location);
        requireNonNull(stream);
        BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
        StringBuilder content = new StringBuilder();
        String line;
        try {
            while ((line = buf.readLine()) != null) content.append(line).append("\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                buf.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return content.toString();
    }
}
