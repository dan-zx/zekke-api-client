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
 * Access token holder. Pretty obvious isn't it?
 * 
 * @author Daniel Pedraza-Arcega
 */
public class AccessTokenHolder {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accessToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return isAccessTokenHolderEqualTo((AccessTokenHolder) obj);
    }

    /**
     * Use this method to complete your equals method.
     *
     * @see Object#equals(Object)
     * @param other the reference object with which to compare.
     * @return {@code true} if this object is the same as the argument; {@code false} otherwise.
     */
    protected boolean isAccessTokenHolderEqualTo(AccessTokenHolder other) {
        return Objects.equals(accessToken, other.accessToken);
    }

    @Override
    public String toString() {
        return "{ accessToken: " + accessToken + " }";
    }
}
