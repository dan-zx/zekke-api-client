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

public interface ManyWaypointsOptions {

    ManyWaypointsOptions enclosedInRectangle(BoundingBox bbox);

    BoundingBox getBbox();

    class Builder implements ManyWaypointsOptions {

        private BoundingBox bbox;

        @Override
        public Builder enclosedInRectangle(BoundingBox bbox) {
            this.bbox = requireNonNull(bbox);
            return null;
        }

        @Override
        public BoundingBox getBbox() {
            return bbox;
        }
    }
}
