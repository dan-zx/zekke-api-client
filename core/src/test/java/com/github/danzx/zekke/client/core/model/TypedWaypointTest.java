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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedWaypointTest {

    private static final TypedWaypoint TESTEE = newTypedWaypoint();

    @Test
    public void shouldEqualsBeTrueWhenSameReference() {
        assertThat(TESTEE.equals(TESTEE)).isTrue();
    }

    @Test
    public void shouldEqualsBeTrueWhenObjectsAreNotTheSameReference() {
        assertThat(TESTEE.equals(newTypedWaypoint())).isTrue();
    }

    @Test
    public void shouldEqualsBeFalseWhenNull() {
        assertThat(TESTEE.equals(null)).isFalse();
    }

    @Test
    public void shouldEqualsBeFalseWhenComparingWithDifferentObject() {
        assertThat(TESTEE.equals(new Object())).isFalse();
    }

    @Test
    public void shouldEqualsBeFalseWhenAtLeastOnePropertyIsDifferent() {
        TypedWaypoint other = newTypedWaypoint();
        other.setId(2L);
        assertThat(TESTEE.equals(other)).isFalse();

        other = newTypedWaypoint();
        other.setType(TypedWaypoint.Type.WALKWAY);
        assertThat(TESTEE.equals(other)).isFalse();

        other = newTypedWaypoint();
        other.setName("OtherPoi");
        assertThat(TESTEE.equals(other)).isFalse();

        other = newTypedWaypoint();
        other.setLocation(newCoordinates(19.023, -39.453));
        assertThat(TESTEE.equals(other)).isFalse();
    }

    @Test
    public void shouldHashCodeBeEqualWhenSameObjectReference() {
        TypedWaypoint other = newTypedWaypoint();
        assertThat(TESTEE.hashCode()).isEqualTo(TESTEE.hashCode()).isEqualTo(other.hashCode());
    }

    private static TypedWaypoint newTypedWaypoint() {
        TypedWaypoint typedWaypoint = new TypedWaypoint();
        typedWaypoint.setId(1L);
        typedWaypoint.setType(TypedWaypoint.Type.POI);
        typedWaypoint.setName("aPoi");
        typedWaypoint.setLocation(newCoordinates(48.125, 109.27));
        return typedWaypoint;
    }

    private static Coordinates newCoordinates(double lat, double lng) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(lat);
        coordinates.setLongitude(lng);
        return coordinates;
    }
}
