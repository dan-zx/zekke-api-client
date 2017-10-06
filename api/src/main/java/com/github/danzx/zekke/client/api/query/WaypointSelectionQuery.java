package com.github.danzx.zekke.client.api.query;

import com.github.danzx.zekke.client.core.model.Coordinates;

public interface WaypointSelectionQuery<R> {
    NearWaypointsQuery<R> near(Coordinates location);
    ManyWaypointsQuery<R> all();
}
