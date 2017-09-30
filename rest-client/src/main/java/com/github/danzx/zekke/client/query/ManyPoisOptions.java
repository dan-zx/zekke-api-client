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
package com.github.danzx.zekke.client.query;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.BoundingBox;

public interface ManyPoisOptions extends ManyWaypointsOptions {

    @Override ManyPoisOptions enclosedInRectangle(BoundingBox bbox);

    ManyPoisOptions forSuggestions();

    ManyPoisOptions complete();

    ManyPoisOptions withNameContainig(String nameQuery);

    boolean arePoisComplete();

    String getNameQuery();

    class Builder extends ManyWaypointsOptions.Builder implements ManyPoisOptions {

        private boolean arePoisComplete;
        private String nameQuery;

        @Override
        public ManyPoisOptions.Builder enclosedInRectangle(BoundingBox bbox) {
            return (ManyPoisOptions.Builder) super.enclosedInRectangle(bbox);
        }

        @Override
        public ManyPoisOptions.Builder forSuggestions() {
            arePoisComplete = false;
            return this;
        }

        @Override
        public ManyPoisOptions.Builder complete() {
            arePoisComplete = true;
            return this;
        }

        @Override
        public ManyPoisOptions.Builder withNameContainig(String nameQuery) {
            this.nameQuery = requireNonNull(nameQuery);
            return this;
        }

        @Override
        public boolean arePoisComplete() {
            return arePoisComplete;
        }

        @Override
        public String getNameQuery() {
            return nameQuery;
        }
    }
}
