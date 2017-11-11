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

import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;

/**
 * Query that returns many POIs.
 *
 * @param <R> a {@link Collection} of {@link Poi} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface ManyPoisQuery<R> extends ManyWaypointsQuery<R> {

    /** {@inheritDoc} */
    @Override @Nonnull ManyPoisQuery<R> limit(@Nullable Integer limit);

    /** {@inheritDoc} */
    @Override @Nonnull ManyPoisQuery<R> enclosedInRectangle(@Nullable BoundingBox bbox);

    /**
     * Sets this query to fetch POIs for name completion only.
     * @return  this object for method chaining.
     */
    @Nonnull ManyPoisQuery<R> forNameSuggestions();

    /**
     * Sets this query to fetch POIs with all its information.
     * @return  this object for method chaining.
     */
    @Nonnull ManyPoisQuery<R> complete();

    /**
     * Sets this query to either fetch POIs for name completion or fetch all its information.
     * @see #forNameSuggestions()
     * @see #complete()
     * @param isQueryForNameSuggestions {@code true} for name completion; {@code false} for all info.
     * @return this object for method chaining.
     */
    @Nonnull ManyPoisQuery<R> setQueryForNameSuggestions(boolean isQueryForNameSuggestions);

    /**
     * @param nameQuery if not {@code null}, filter by POI names that contain this string.
     * @return this object for method chaining.
     */
    @Nonnull ManyPoisQuery<R> withNameContaining(@Nullable String nameQuery);
}
