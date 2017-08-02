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
 * Represents a geographic point in the planet.
 * 
 * @author Daniel Pedraza-Arcega
 */
public class Coordinates {

    private static final String LAT_LNG_SEPARATOR = ",";

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return isCoordinatesEqualTo((Coordinates) obj);
    }

    /**
     * Use this method to complete your equals method.
     * 
     * @see Object#equals(Object)
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the argument; {@code false} otherwise.
     */
    protected boolean isCoordinatesEqualTo(Coordinates other) {
        return Objects.equals(latitude, other.latitude) && 
               Objects.equals(longitude, other.longitude);
    }

    /**
     * Returns a String object representing this Coordinates object. 
     * 
     * @return {@code <latitude_value>,<longitude_value>}
     */
    @Override
    public String toString() {
        return latitude + LAT_LNG_SEPARATOR + longitude;
    }
}
