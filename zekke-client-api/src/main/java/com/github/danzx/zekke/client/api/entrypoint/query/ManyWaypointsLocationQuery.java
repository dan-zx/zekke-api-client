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
package com.github.danzx.zekke.client.api.entrypoint.query;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.core.model.BaseWaypoint;

/**
 * Query that returns many waypoints filtered by a location.
 *
 * @param <R> a {@link Collection} of a subclass of {@link BaseWaypoint} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface ManyWaypointsLocationQuery<R> extends ManyResultsQuery<R> {

    /** {@inheritDoc} */
    @Override @Nonnull ManyWaypointsLocationQuery<R> limit(@Nullable Integer limit);

    /**
     * @param distance limits the results to those waypoints that are at most the specified distance from the center
     *                 point.
     * @return this object for method chaining.
     */
    @Nonnull ManyWaypointsLocationQuery<R> atMostDistance(@Nullable Integer distance);
}
