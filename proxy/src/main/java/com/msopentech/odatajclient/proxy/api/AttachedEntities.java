/*
 * Copyright 2013 MS OpenTech.
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
package com.msopentech.odatajclient.proxy.api;

import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.HashMap;
import java.util.Map;

public class AttachedEntities {

    private final Map<Object, EntityTypeInvocationHandler> entities =
            new HashMap<Object, EntityTypeInvocationHandler>();

    public EntityTypeInvocationHandler getEntity(final Object key) {
        return entities.get(key);
    }

    public boolean contains(final Object key) {
        return entities.containsKey(key);
    }

    public EntityTypeInvocationHandler add(final Object key, final EntityTypeInvocationHandler entity) {
        return entities.put(key, entity);
    }

    public EntityTypeInvocationHandler remove(final Object key) {
        return entities.remove(key);
    }

    public boolean isEmpty() {
        return entities.isEmpty();
    }
}
