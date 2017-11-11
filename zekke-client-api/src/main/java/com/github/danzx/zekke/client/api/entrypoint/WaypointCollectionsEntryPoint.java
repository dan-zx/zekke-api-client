/*
 * Copyright 2017 Daniel Pedraza-Arcega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danzx.zekke.client.api.entrypoint;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.entrypoint.query.ManyWaypointsLocationQuery;
import com.github.danzx.zekke.client.api.entrypoint.query.ManyWaypointsQuery;
import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;

/**
 * Waypoint API collections options fluent interface.
 *
 * @param <R> queries must return a {@link Collection} of a subclass of {@link BaseWaypoint} or a container for this
 *            object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface WaypointCollectionsEntryPoint<R> {

    /**
     * @param location the location of the query.
     * @return a query that returns a collections of waypoints near the given location.
     */
    @Nonnull
    ManyWaypointsLocationQuery<R> near(@Nonnull Coordinates location);

    /** @return a query that returns a collections of all waypoints. */
    @Nonnull
    ManyWaypointsQuery<R> all();
}
