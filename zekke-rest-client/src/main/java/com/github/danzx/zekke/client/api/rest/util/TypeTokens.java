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
package com.github.danzx.zekke.client.api.rest.util;

import com.github.danzx.zekke.client.core.exception.ApiErrorDetail;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.core.model.TypedWaypoint;

import com.google.gson.reflect.TypeToken;

/**
 * TypeToken utils.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public class TypeTokens {

    public static final TypeToken<AccessTokenHolder> ACCESS_TOKEN_HOLDER = TypeToken.get(AccessTokenHolder.class);
    public static final TypeToken<ApiErrorDetail>    API_ERROR_DETAIL    = TypeToken.get(ApiErrorDetail.class);
    public static final TypeToken<TypedWaypoint>     WAYPOINT            = TypeToken.get(TypedWaypoint.class);

    private TypeTokens() {
        throw new AssertionError();
    }
}
