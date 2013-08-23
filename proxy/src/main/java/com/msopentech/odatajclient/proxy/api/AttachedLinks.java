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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttachedLinks {

    private final Map<String, Set<EntityTypeInvocationHandler>> links =
            new HashMap<String, Set<EntityTypeInvocationHandler>>();

    public Set<EntityTypeInvocationHandler> getLinkedEntities(final String name) {
        return links.get(name);
    }

    public boolean contains(final String name) {
        return links.containsKey(name);
    }

    public void add(final String name, final EntityTypeInvocationHandler entity) {
        add(name, Collections.<EntityTypeInvocationHandler>singleton(entity));
    }

    public void add(final String name, final Collection<EntityTypeInvocationHandler> entity) {
        final Set<EntityTypeInvocationHandler> linked;
        if (contains(name)) {
            linked = getLinkedEntities(name);
        } else {
            linked = new HashSet<EntityTypeInvocationHandler>();
            links.put(name, linked);
        }

        linked.addAll(entity);
    }

    public void remove(final String name) {
        links.remove(name);
    }

    public void remove(final String name, final EntityTypeInvocationHandler entity) {
        remove(name, Collections.<EntityTypeInvocationHandler>singleton(entity));
    }

    public void remove(final String name, final Collection<EntityTypeInvocationHandler> entities) {
        if (contains(name)) {
            Set<EntityTypeInvocationHandler> linkedEntities = getLinkedEntities(name);
            linkedEntities.removeAll(entities);
            if (linkedEntities.isEmpty()) {
                remove(name);
            }
        }
    }

    public boolean isEmpty() {
        return links.isEmpty();
    }
}
