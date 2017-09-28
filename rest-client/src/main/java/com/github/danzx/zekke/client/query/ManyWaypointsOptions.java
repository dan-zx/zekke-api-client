package com.github.danzx.zekke.client.query;

import com.github.danzx.zekke.client.core.model.BoundingBox;

import static java.util.Objects.requireNonNull;

public interface ManyWaypointsOptions {

    ManyWaypointsOptions enclosedInRectangle(BoundingBox bbox);

    BoundingBox getBbox();

    class Builder implements ManyWaypointsOptions {

        private BoundingBox bbox;

        @Override
        public Builder enclosedInRectangle(BoundingBox bbox) {
            this.bbox = requireNonNull(bbox);
            return null;
        }

        @Override
        public BoundingBox getBbox() {
            return bbox;
        }
    }
}
