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
package com.msopentech.odatajclient.proxy.api.context;

import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * Link context.
 */
public class LinkContext implements Iterable<EntityLinkDesc> {

    /**
     * New defined links.
     */
    private final Map<EntityTypeInvocationHandler, NavigationLinks> attached =
            new LinkedHashMap<EntityTypeInvocationHandler, NavigationLinks>();

    /**
     * Gets all entities linked by the given source and the given navigation property name.
     *
     * @param source source.
     * @param sourceName navigation property name.
     * @return linked entity or entity set.
     */
    public Set<EntityTypeInvocationHandler> getLinkedEntities(
            final EntityTypeInvocationHandler source, final String sourceName) {
        if (isAttached(source, sourceName)) {
            return attached.get(source).getLinkedEntities(sourceName);
        } else {
            return Collections.<EntityTypeInvocationHandler>emptySet();
        }
    }

    /**
     * Checks if a link between the given entities has been already attached.
     *
     * @param source source entity.
     * @param sourceName navigation property name.
     * @param target target entity.
     * @return <tt>true</tt> if is attached; <tt>false</tt> otherwise.
     */
    public boolean isAttached(
            final EntityTypeInvocationHandler source,
            final String sourceName,
            final EntityTypeInvocationHandler target) {
        return attached.containsKey(source)
                && attached.get(source).contains(sourceName)
                && attached.get(source).getLinkedEntities(sourceName).contains(target);
    }

    /**
     * Checks if a link between the given source and other entities has been already attached.
     *
     * @param source source entity.
     * @param sourceName navigation link property.
     * @return <tt>true</tt> if is attached; <tt>false</tt> otherwise.
     */
    public boolean isAttached(
            final EntityTypeInvocationHandler source,
            final String sourceName) {
        return attached.containsKey(source) && attached.get(source).contains(sourceName);
    }

    /**
     * Attaches a new link definition.
     *
     * @param desc link descriptor.
     */
    public void attach(final EntityLinkDesc desc) {
        if (desc.getSource() == null
                || StringUtils.isBlank(desc.getSourceName())
                || desc.getTargets() == null
                || desc.getTargets().isEmpty()) {
            throw new IllegalArgumentException("Invalid link descriptor\n" + desc);
        }

        final NavigationLinks links;
        if (attached.containsKey(desc.getSource())) {
            links = attached.get(desc.getSource());
        } else {
            links = new NavigationLinks();
            attached.put(desc.getSource(), links);
        }

        links.add(desc.getSourceName(), desc.getTargets(), desc.getType());
    }

    /**
     * Detaches link.
     *
     * @param source link source.
     * @param name navigation property name.
     */
    public void detach(final EntityTypeInvocationHandler source, final String name) {
        if (attached.containsKey(source)) {
            attached.get(source).remove(name);
            if (attached.get(source).isEmpty()) {
                attached.remove(source);
            }
        }
    }

    /**
     * Detaches link.
     *
     * @param desc link descriptor.
     */
    public void detach(final EntityLinkDesc desc) {
        if (attached.containsKey(desc.getSource())) {
            attached.get(desc.getSource()).remove(desc.getSourceName(), desc.getTargets());
            if (attached.get(desc.getSource()).isEmpty()) {
                attached.remove(desc.getSource());
            }
        }
    }

    /**
     * Detaches all links.
     * <p>
     * Use this method to clean the link context.
     */
    public void detachAll() {
        attached.clear();
    }

    /**
     * Gets attached link iterator.
     *
     * @return attached link iterator.
     */
    @Override
    public Iterator<EntityLinkDesc> iterator() {
        final Set<EntityLinkDesc> res = new HashSet<EntityLinkDesc>();

        for (Map.Entry<EntityTypeInvocationHandler, NavigationLinks> entry : attached.entrySet()) {
            for (String linkName : entry.getValue().getLinkNames()) {
                final NavigationLink attachedLink = entry.getValue().get(linkName);

                res.add(new EntityLinkDesc(
                        linkName,
                        entry.getKey(),
                        (Collection<EntityTypeInvocationHandler>) attachedLink.getLinkEntities(),
                        attachedLink.getLinkType()));
            }
        }

        return res.iterator();
    }
}
