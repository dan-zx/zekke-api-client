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
package com.github.danzx.zekke.client.api.entrypoint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.danzx.zekke.client.api.entrypoint.query.SingleResultQuery;
import com.github.danzx.zekke.client.core.model.AccessTokenHolder;

/**
 * Authentication API options fluent interface.
 *
 * @param <R> queries must return an {@link AccessTokenHolder} or a container for this object.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface AuthenticationEntryPoint<R> {

    /** @return a query that performs an anonymous authentication, i.e., no user id or password required. */
    @Nonnull
    SingleResultQuery<R> anonymously();

    /**
     * @param userId the user id.
     * @param password the password of the user.
     * @return query that performs a login based authentication using the given credentials.
     */
    @Nonnull SingleResultQuery<R> withAdminPrivileges(@Nullable String userId, @Nullable String password);
}
