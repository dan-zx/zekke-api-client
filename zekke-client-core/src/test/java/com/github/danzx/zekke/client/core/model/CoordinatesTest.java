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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CoordinatesTest {

    private static final Coordinates TESTEE = newCoordinates();

    @Test
    public void shouldGettersReturnNonNullValues() {
        assertThat(TESTEE.getLatitude()).isNotNull();
        assertThat(TESTEE.getLongitude()).isNotNull();
    }

    @Test
    public void shouldEqualsBeTrueWhenSameReference() {
        assertThat(TESTEE.equals(TESTEE)).isTrue();
    }

    @Test
    public void shouldEqualsBeTrueWhenObjectsAreNotTheSameReference() {
        assertThat(TESTEE.equals(newCoordinates())).isTrue();
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
        Coordinates other = newCoordinates();
        other.setLatitude(48.125);
        assertThat(TESTEE.equals(other)).isFalse();

        other = newCoordinates();
        other.setLongitude(109.27);
        assertThat(TESTEE.equals(other)).isFalse();
    }

    @Test
    public void shouldToStringReturnCorrectFormat() {
        assertThat(TESTEE.toString()).isNotNull().isNotEmpty().isEqualTo("19.023,-39.453");
    }

    @Test
    public void shouldHashCodeBeEqualWhenSameObjectReference() {
        Coordinates other = newCoordinates();
        assertThat(TESTEE.hashCode()).isEqualTo(TESTEE.hashCode()).isEqualTo(other.hashCode());
    }

    private static Coordinates newCoordinates() {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(19.023);
        coordinates.setLongitude(-39.453);
        return coordinates;
    }
}
