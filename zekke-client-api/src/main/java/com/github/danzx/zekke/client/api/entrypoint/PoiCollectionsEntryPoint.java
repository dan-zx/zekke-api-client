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

import com.github.danzx.zekke.client.api.entrypoint.query.ManyPoisQuery;
import com.github.danzx.zekke.client.core.model.Poi;

/**
 * POI API collections fluent interface.
 *
 * @param <R> queries must return a {@link Collection} of {@link Poi} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface PoiCollectionsEntryPoint<R> extends WaypointCollectionsEntryPoint<R> {

    /** @return a query that returns a collections of all POIs. */
    @Override
    ManyPoisQuery<R> all();
}
