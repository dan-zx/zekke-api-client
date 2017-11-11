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
package com.github.danzx.zekke.client.core.model;

import java.util.Objects;

/**
 * Base waypoint request/response object.
 *
 * @author Daniel Pedraza-Arcega
 */
public abstract class BaseWaypoint {

    private Long id;

    private Coordinates location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return isBaseWaypointEqualTo((BaseWaypoint) obj);
    }

    /**
     * Use this method to complete your equals method.
     *
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the argument; {@code false} otherwise.
     * @see Object#equals(Object)
     */
    protected boolean isBaseWaypointEqualTo(BaseWaypoint other) {
        return Objects.equals(id, other.id) &&
                Objects.equals(location, other.location);
    }

    @Override
    public String toString() {
        return "{ id:" + id + ", location:" + location + " }";
    }
}
