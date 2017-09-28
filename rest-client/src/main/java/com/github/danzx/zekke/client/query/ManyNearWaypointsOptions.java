package com.github.danzx.zekke.client.query;

import com.github.danzx.zekke.client.core.model.Coordinates;

import static java.util.Objects.requireNonNull;

public interface ManyNearWaypointsOptions {

    ManyNearWaypointsOptions atMostDistance(int distance);

    ManyNearWaypointsOptions limit(int limit);

    Coordinates getLocation();

    Integer getDistance();

    Integer getLimit();

    class Builder implements ManyNearWaypointsOptions {

        private final Coordinates location;
        private Integer distance;
        private Integer limit;

        public Builder(Coordinates location) {
            this.location = requireNonNull(location);
        }

        @Override
        public Builder atMostDistance(int distance) {
            this.distance = distance;
            return this;
        }

        @Override
        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public Coordinates getLocation() {
            return location;
        }

        @Override
        public Integer getDistance() {
            return distance;
        }

        @Override
        public Integer getLimit() {
            return limit;
        }
    }
}
