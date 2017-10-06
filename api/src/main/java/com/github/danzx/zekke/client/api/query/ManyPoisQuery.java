package com.github.danzx.zekke.client.api.query;

import com.github.danzx.zekke.client.core.model.BoundingBox;

public abstract class ManyPoisQuery<R> extends ManyWaypointsQuery<R> {

    private boolean isQueryForNameSuggestions = false;
    private String nameQuery;

    @Override
    public ManyPoisQuery<R> limit(Integer limit) {
        super.limit(limit);
        return this;
    }

    @Override
    public ManyPoisQuery<R> enclosedInRectangle(BoundingBox bbox) {
        super.enclosedInRectangle(bbox);
        return this;
    }

    public ManyPoisQuery<R> forNameSuggestions() {
        isQueryForNameSuggestions = true;
        return this;
    }

    public ManyPoisQuery<R> complete() {
        isQueryForNameSuggestions = false;
        return this;
    }

    public ManyPoisQuery<R> withNameContaining(String nameQuery) {
        this.nameQuery = nameQuery;
        return this;
    }

    public ManyPoisQuery<R> setQueryForNameSuggestions(boolean isQueryForNameSuggestions) {
        return isQueryForNameSuggestions ? forNameSuggestions() : complete();
    }

    public boolean isQueryForNameSuggestions() {
        return isQueryForNameSuggestions;
    }

    public String nameQuery() {
        return nameQuery;
    }
}
