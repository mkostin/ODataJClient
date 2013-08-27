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

import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NavigationLinks {

    private final Map<String, NavigationLink> navigationLinks = new HashMap<String, NavigationLink>();

    public Set<String> getLinkNames() {
        return navigationLinks.keySet();
    }

    public ODataLinkType getLinkType(final String sourceName) {
        return navigationLinks.get(sourceName).getLinkType();
    }

    public NavigationLink get(final String name) {
        return navigationLinks.get(name);
    }

    public Set<EntityTypeInvocationHandler> getLinkedEntities(final String name) {
        return navigationLinks.get(name).getLinkEntities();
    }

    public boolean contains(final String name) {
        return navigationLinks.containsKey(name);
    }

    public void add(final String name, final EntityTypeInvocationHandler entity, final ODataLinkType type) {
        add(name, Collections.<EntityTypeInvocationHandler>singleton(entity), type);
    }

    public void add(final String name, final Collection<EntityTypeInvocationHandler> entity, final ODataLinkType type) {
        final NavigationLink attachedLink;

        if (contains(name)) {
            attachedLink = navigationLinks.get(name);
        } else {
            attachedLink = new NavigationLink(type);
            navigationLinks.put(name, new NavigationLink(type));
        }

        if (attachedLink.getLinkType() != type) {
            throw new IllegalArgumentException(
                    "Invalid link type: expected '" + attachedLink.getLinkType() + "'; found '" + type + "'");
        }

        getLinkedEntities(name).addAll(entity);
    }

    public void remove(final String name) {
        navigationLinks.remove(name);
    }

    public void remove(final String name, final EntityTypeInvocationHandler entity) {
        remove(name, Collections.<EntityTypeInvocationHandler>singleton(entity));
    }

    public void remove(final String name, final Collection<EntityTypeInvocationHandler> entities) {
        if (contains(name)) {
            final Set<EntityTypeInvocationHandler> linkedEntities = getLinkedEntities(name);
            linkedEntities.removeAll(entities);
            if (linkedEntities.isEmpty()) {
                remove(name);
            }
        }
    }

    public boolean isEmpty() {
        return navigationLinks.isEmpty();
    }
}
