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

import com.github.danzx.zekke.client.api.entrypoint.query.SingleResultQuery;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;

/**
 * TypedWaypoint API options fluent interface.
 *
 * @param <LT> queries must return a {@link Collection} of {@link TypedWaypoint} or a container for this object.
 * @param <T> queries must return a {@link TypedWaypoint} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface TypedWaypointEntryPoint<LT, T> extends WaypointCollectionsEntryPoint<LT> {

    /**
     * @param id the waypoint id.
     * @return  a query that returns a single waypoint by the given id.
     */
    @Nonnull
    SingleResultQuery<T> byId(long id);
}
