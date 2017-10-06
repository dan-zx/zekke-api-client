package com.github.danzx.zekke.client.api.query;

public interface PoiSelectionQuery<R> extends WaypointSelectionQuery<R> {
    ManyPoisQuery<R> all();
}
