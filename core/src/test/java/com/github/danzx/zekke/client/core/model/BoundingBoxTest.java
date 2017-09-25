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

public class BoundingBoxTest {

    private static final BoundingBox TESTEE;

    static {
        TESTEE = new BoundingBox();
        TESTEE.setBottomCoordinates(newCoordinates(19.023, -39.453));
        TESTEE.setTopCoordinates(newCoordinates(48.125, 109.27));
    }

    @Test
    public void shouldEqualsBeTrueWhenSameReference() {
        assertThat(TESTEE.equals(TESTEE)).isTrue();
    }

    @Test
    public void shouldEqualsBeTrueWhenObjectsAreNotTheSameReference() {
        BoundingBox other = new BoundingBox();
        other.setTopCoordinates(TESTEE.getTopCoordinates());
        other.setBottomCoordinates(TESTEE.getBottomCoordinates());
        assertThat(TESTEE.equals(other)).isTrue();
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
        BoundingBox other = new BoundingBox();
        other.setTopCoordinates(TESTEE.getTopCoordinates());
        other.setBottomCoordinates(newCoordinates(19.4535, 72.365));
        assertThat(TESTEE.equals(other)).isFalse();

        other = new BoundingBox();
        other.setTopCoordinates(newCoordinates(36.234, -24.365));
        other.setBottomCoordinates(TESTEE.getBottomCoordinates());
        assertThat(TESTEE.equals(other)).isFalse();
    }

    @Test
    public void shouldHashCodeBeEqualWhenSameObjectReference() {
        BoundingBox other = new BoundingBox();
        other.setTopCoordinates(TESTEE.getTopCoordinates());
        other.setBottomCoordinates(TESTEE.getBottomCoordinates());
        assertThat(TESTEE.hashCode()).isEqualTo(TESTEE.hashCode()).isEqualTo(other.hashCode());
    }

    private static Coordinates newCoordinates(double lat, double lng) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(lat);
        coordinates.setLongitude(lng);
        return coordinates;
    }
}
