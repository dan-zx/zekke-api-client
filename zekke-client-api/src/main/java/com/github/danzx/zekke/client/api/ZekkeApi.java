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
package com.github.danzx.zekke.client.api;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.github.danzx.zekke.client.api.entrypoint.AuthenticationEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.WaypointTypeEntryPoint;
import com.github.danzx.zekke.client.api.entrypoint.query.Query;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.core.model.Walkway;

/**
 * ZeKKe API fluent interface entry point.
 * Generics allow flexibility of preforming blocking and non blocking operations by returning lazy operations.
 * All operations must be implemented using {@link Query} interface or subinterface and returning the types specified in
 * this API.
 *
 * <p>
 * Example of a client that calls this API:
 * <pre>
 * {@code
 *      ZekkeApi client = // init ZekkeApi implementation object
 *      List<Poi> pois = client
 *          .findWaypoints()
 *          .pois()
 *              .all()
 *              .limit(10)
 *              .withNameContaining("statue")
 *              .complete()
 *          .get();
 * }
 * </pre>
 *
 * @param <A> queries must return an {@link AccessTokenHolder} or a container for this object.
 * @param <LT> queries must return a {@link Collection} of {@link TypedWaypoint} or a container for this object.
 * @param <T> queries must return a {@link TypedWaypoint} or a container for this object.
 * @param <LP> queries must return a {@link Collection} of {@link Poi} or a container for this object.
 * @param <LW> queries must return a {@link Collection} of {@link Walkway} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface ZekkeApi<A, LT, T, LP, LW> {

    /** @return the entry point of the Authentication API options. */
    @Nonnull AuthenticationEntryPoint<A> authenticate();

    /** @return the entry point to query the Waypoint API type options.*/
    @Nonnull WaypointTypeEntryPoint<LT, T, LP, LW> findWaypoints();
}
