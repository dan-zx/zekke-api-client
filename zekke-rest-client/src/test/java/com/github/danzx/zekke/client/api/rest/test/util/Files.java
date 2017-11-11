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
package com.github.danzx.zekke.client.api.rest.test.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.github.danzx.zekke.client.api.rest.util.Charset;

public class Files {

    private Files() {
        throw new AssertionError();
    }

    public static String readFromClasspath(String location) {
        InputStream stream = Files.class.getResourceAsStream(location);
        requireNonNull(stream);
        BufferedReader buf;
        try {
            buf = new BufferedReader(new InputStreamReader(stream, Charset.UTF_8.toString()));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
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
