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

import com.msopentech.odatajclient.proxy.api.AttachedLinks;
import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class LinkContext {

    private final Map<EntityTypeInvocationHandler, AttachedLinks> attached =
            new HashMap<EntityTypeInvocationHandler, AttachedLinks>();

    public boolean isAttached(final EntityTypeInvocationHandler source, final String sourceName) {
        return attached.containsKey(source) && attached.get(source).contains(sourceName);
    }

    public Set<EntityTypeInvocationHandler> getLinkedEntities(
            final EntityTypeInvocationHandler source, final String sourceName) {
        if (isAttached(source, sourceName)) {
            return attached.get(source).getLinkedEntities(sourceName);
        } else {
            return Collections.<EntityTypeInvocationHandler>emptySet();
        }
    }

    public void attach(final EntityLinkDesc desc) {
        if (desc.getSource() == null || StringUtils.isBlank(desc.getSourceName()) || desc.getTarget() == null) {
            throw new IllegalArgumentException("Invalid link descriptor\n" + desc);
        }

        attach(desc.getSource(), desc.getSourceName(), desc.getTarget());

        if (StringUtils.isNotBlank(desc.getTargetName())) {
            attach(desc.getTarget(), desc.getTargetName(), desc.getSource());
        }
    }

    public void detach(final EntityLinkDesc desc) {
        detach(desc.getSource(), desc.getSourceName(), desc.getTarget());
        detach(desc.getTarget(), desc.getTargetName(), desc.getSource());
    }

    private void attach(
            final EntityTypeInvocationHandler source, final String name, final EntityTypeInvocationHandler target) {
        final AttachedLinks links;
        if (attached.containsKey(source)) {
            links = attached.get(source);
        } else {
            links = new AttachedLinks();
            attached.put(source, links);
        }

        links.add(name, target);
    }

    private void detach(
            final EntityTypeInvocationHandler source, final String name, final EntityTypeInvocationHandler target) {

        if (attached.containsKey(source)) {
            attached.get(source).remove(name, target);
            if (attached.isEmpty()) {
                attached.remove(source);
            }
        }
    }
}
