package com.github.danzx.zekke.client.api.query;

public interface WaypointTypeStarterQuery<LT, ST, P, W> {
    TypedWaypointSelectionQuery<LT, ST> typed();
    PoiSelectionQuery<P> pois();
    WaypointSelectionQuery<W> walkways();
}
