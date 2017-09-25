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
