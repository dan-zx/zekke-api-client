package com.github.danzx.zekke.client.api.query;

public interface TypedWaypointSelectionQuery<L, S> extends WaypointSelectionQuery<L> {
    Query<S> byId(long id);
}
