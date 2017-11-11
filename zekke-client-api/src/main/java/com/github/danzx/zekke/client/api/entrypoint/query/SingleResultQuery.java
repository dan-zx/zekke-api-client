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
package com.github.danzx.zekke.client.api.entrypoint.query;

import javax.annotation.Nullable;

/**
 * Query that returns a single result
 *
 * @param <R> the actual object type to return.
 *
 * @author Daniel Pedraza-Arcega
 * @since version 1.0
 */
public interface SingleResultQuery<R> extends Query<R> {

    /** @return a single object or a container of for this object. */
    @Override @Nullable R get();
}
