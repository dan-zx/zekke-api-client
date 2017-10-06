package com.github.danzx.zekke.client.api.query;

import com.github.danzx.zekke.client.core.model.BoundingBox;

public abstract class ManyWaypointsQuery<R> extends LimitableQuery<R> {

    private BoundingBox boundingBox;

    @Override
    public ManyWaypointsQuery<R> limit(Integer limit) {
        super.limit(limit);
        return this;
    }

    public ManyWaypointsQuery<R> enclosedInRectangle(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
        return this;
    }

    public BoundingBox boundingBox() {
        return boundingBox;
    }
}
