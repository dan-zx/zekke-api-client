package com.github.danzx.zekke.client.query;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.BoundingBox;

public interface ManyPoisOptions extends ManyWaypointsOptions {

    @Override ManyPoisOptions enclosedInRectangle(BoundingBox bbox);

    ManyPoisOptions forSuggestions();

    ManyPoisOptions complete();

    ManyPoisOptions withNameContainig(String nameQuery);

    boolean arePoisComplete();

    String getNameQuery();

    class Builder extends ManyWaypointsOptions.Builder implements ManyPoisOptions {

        private boolean arePoisComplete;
        private String nameQuery;

        @Override
        public ManyPoisOptions.Builder enclosedInRectangle(BoundingBox bbox) {
            return (ManyPoisOptions.Builder) super.enclosedInRectangle(bbox);
        }

        @Override
        public ManyPoisOptions.Builder forSuggestions() {
            arePoisComplete = false;
            return this;
        }

        @Override
        public ManyPoisOptions.Builder complete() {
            arePoisComplete = true;
            return this;
        }

        @Override
        public ManyPoisOptions.Builder withNameContainig(String nameQuery) {
            this.nameQuery = requireNonNull(nameQuery);
            return this;
        }

        @Override
        public boolean arePoisComplete() {
            return arePoisComplete;
        }

        @Override
        public String getNameQuery() {
            return nameQuery;
        }
    }
}
