package com.github.danzx.zekke.client;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.query.Query;

public interface ZekkeApiClient {

    Query<?> authenticateAnonymously();
    Query<?> authenticateAdmin(String userId, String password);
    Query<?> oneWaypoint(long id);
    Query<?> waypoints();
    Query<?> walkways();
    Query<?> pois();
    Query<?> waypointsNear(Coordinates location);
    Query<?> walkwaysNear(Coordinates location);
    Query<?> poisNear(Coordinates location);
    void setAuthenticaton(AccessTokenHolder tokenHolder);
}
