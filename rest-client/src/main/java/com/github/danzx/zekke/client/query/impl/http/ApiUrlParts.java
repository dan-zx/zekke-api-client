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
package com.github.danzx.zekke.client.query.impl.http;

public class ApiUrlParts {

    public static class QueryParams {
        public static final String QUERY = "query";
        public static final String BOUNDING_BOX = "bbox";
        public static final String LOCATION = "location";
        public static final String DISTANCE = "distance";
        public static final String LIMIT = "limit";

        private QueryParams() {
            throw new AssertionError();
        }
    }

    public static class PathSegments {
        public static final String WAYPOINTS = "waypoints";
        public static final String POIS = "pois";
        public static final String WALKWAYS = "walkways";
        public static final String NAMES = "names";
        public static final String NEAR = "near";
        public static final String AUTHENTICATION = "authentication";
        public static final String JWT = "jwt";
        public static final String ANONYMOUS = "anonymous";
        public static final String ADMIN = "admin";

        private PathSegments() {
            throw new AssertionError();
        }
    }

    private ApiUrlParts() {
        throw new AssertionError();
    }
}
