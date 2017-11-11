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
package com.github.danzx.zekke.client.api.rest.http.interceptor;

import okhttp3.logging.HttpLoggingInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J Logger logging interceptor.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class Slf4jHttpLoggingInterceptor {

    private Slf4jHttpLoggingInterceptor() {
        throw new AssertionError();
    }

    /** @return singleton instance. */
    public static HttpLoggingInterceptor instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final HttpLoggingInterceptor INSTANCE;

        static {
            final Logger interceptorLogger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);
            INSTANCE = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    interceptorLogger.debug(message);
                }
            });
        }

        private InstanceHolder() {
            throw new AssertionError();
        }
    }
}
