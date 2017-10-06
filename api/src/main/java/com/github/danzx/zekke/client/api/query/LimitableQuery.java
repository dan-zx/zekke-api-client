package com.github.danzx.zekke.client.api.query;

public abstract class LimitableQuery<R> implements Query<R> {

    private Integer limit;

    public LimitableQuery<R> limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer limit() {
        return limit;
    }
}
