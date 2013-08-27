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

import com.msopentech.odatajclient.proxy.api.NavigationLink;
import com.msopentech.odatajclient.proxy.api.NavigationLinks;
import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class LinkContext implements Iterable<EntityLinkDesc> {

    private final Map<EntityTypeInvocationHandler, NavigationLinks> attached =
            new LinkedHashMap<EntityTypeInvocationHandler, NavigationLinks>();

    public Set<EntityTypeInvocationHandler> getLinkedEntities(
            final EntityTypeInvocationHandler source, final String sourceName) {
        if (isAttached(source, sourceName)) {
            return attached.get(source).getLinkedEntities(sourceName);
        } else {
            return Collections.<EntityTypeInvocationHandler>emptySet();
        }
    }

    public boolean isAttached(
            final EntityTypeInvocationHandler source,
            final String sourceName,
            final EntityTypeInvocationHandler target) {
        return attached.containsKey(source)
                && attached.get(source).contains(sourceName)
                && attached.get(source).getLinkedEntities(sourceName).contains(target);
    }

    public boolean isAttached(
            final EntityTypeInvocationHandler source,
            final String sourceName) {
        return attached.containsKey(source) && attached.get(source).contains(sourceName);
    }

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

    public void detach(final EntityLinkDesc desc) {
        if (attached.containsKey(desc.getSource())) {
            attached.get(desc.getSource()).remove(desc.getSourceName(), desc.getTargets());
            if (attached.isEmpty()) {
                attached.remove(desc.getSource());
            }
        }
    }

    public void detachAll() {
        attached.clear();
    }

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
