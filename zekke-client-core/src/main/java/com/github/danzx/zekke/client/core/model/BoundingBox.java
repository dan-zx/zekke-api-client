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
 * Contains a bottom left coordinates and upper right coordinates of a rectangle.
 *
 * @author Daniel Pedraza-Arcega
 */
public class BoundingBox {

    private static final String COORDINATES_SEPARATOR = ";";

    private Coordinates bottomCoordinates;
    private Coordinates topCoordinates;

    public Coordinates getBottomCoordinates() {
        return bottomCoordinates;
    }

    public void setBottomCoordinates(Coordinates bottomCoordinates) {
        this.bottomCoordinates = bottomCoordinates;
    }

    public Coordinates getTopCoordinates() {
        return topCoordinates;
    }

    public void setTopCoordinates(Coordinates topCoordinates) {
        this.topCoordinates = topCoordinates;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottomCoordinates, topCoordinates);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return isBoundingBoxEqualTo((BoundingBox) obj);
    }

    /**
     * Use this method to complete your equals method.
     *
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the argument; {@code false} otherwise.
     * @see Object#equals(Object)
     */
    protected boolean isBoundingBoxEqualTo(BoundingBox other) {
        return Objects.equals(bottomCoordinates, other.bottomCoordinates) &&
                Objects.equals(topCoordinates, other.topCoordinates);
    }

    /**
     * Returns a String object representing this BoundingBox.
     *
     * @return {@code <bottom_latitude>,<left_longitude>;<top_latitude>,<right_longitude>}
     */
    @Override
    public String toString() {
        return bottomCoordinates + COORDINATES_SEPARATOR + topCoordinates;
    }
}
