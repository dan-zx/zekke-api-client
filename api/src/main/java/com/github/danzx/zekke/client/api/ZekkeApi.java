package com.github.danzx.zekke.client.api;

import com.github.danzx.zekke.client.api.query.WaypointTypeStarterQuery;
import com.github.danzx.zekke.client.api.query.AuthenticationSelectionQuery;

public interface ZekkeApi<A, LT, T, LP, LW> {
    AuthenticationSelectionQuery<A> authenticate();
    WaypointTypeStarterQuery<LT, T, LP, LW> findWaypoints();
}
