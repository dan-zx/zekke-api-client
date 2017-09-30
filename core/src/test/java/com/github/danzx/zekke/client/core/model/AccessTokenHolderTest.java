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

public class AccessTokenHolderTest {

    private static final AccessTokenHolder TESTEE = newAccessTokenHolder();

    @Test
    public void shouldGettersReturnNonNullValues() {
        assertThat(TESTEE.getAccessToken()).isNotNull();
    }

    @Test
    public void shouldEqualsBeTrueWhenSameReference() {
        assertThat(TESTEE.equals(TESTEE)).isTrue();
    }

    @Test
    public void shouldEqualsBeTrueWhenObjectsAreNotTheSameReference() {
        assertThat(TESTEE.equals(newAccessTokenHolder())).isTrue();
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
        AccessTokenHolder other = newAccessTokenHolder();
        other.setAccessToken("otherToken");
        assertThat(TESTEE.equals(other)).isFalse();
    }

    @Test
    public void shouldHashCodeBeEqualWhenSameObjectReference() {
        AccessTokenHolder other = newAccessTokenHolder();
        assertThat(TESTEE.hashCode()).isEqualTo(TESTEE.hashCode()).isEqualTo(other.hashCode());
    }

    private static AccessTokenHolder newAccessTokenHolder() {
        AccessTokenHolder accessTokenHolder = new AccessTokenHolder();
        accessTokenHolder.setAccessToken("token");
        return accessTokenHolder;
    }
}
