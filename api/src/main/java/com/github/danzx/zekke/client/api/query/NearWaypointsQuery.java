package com.github.danzx.zekke.client.api.query;

import com.github.danzx.zekke.client.core.model.Coordinates;

import static java.util.Objects.requireNonNull;

public abstract class NearWaypointsQuery<R> extends LimitableQuery<R> {

    private final Coordinates location;
    private Integer distance;

    public NearWaypointsQuery(Coordinates location) {
        this.location = requireNonNull(location);
    }

    @Override
    public NearWaypointsQuery<R> limit(Integer limit) {
        super.limit(limit);
        return this;
    }

    public NearWaypointsQuery<R> atMostDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Coordinates location() {
        return location;
    }

    public Integer distance() {
        return distance;
    }
}
