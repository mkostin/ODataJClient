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

import com.msopentech.odatajclient.proxy.api.AttachedEntities;
import com.msopentech.odatajclient.proxy.api.impl.EntityTypeInvocationHandler;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EntityContext {

    private final Map<EntityTypeDesc, AttachedEntities> attached = new HashMap<EntityTypeDesc, AttachedEntities>();

    private final Map<EntityTypeInvocationHandler, AttachedEntityStatus> entities =
            new LinkedHashMap<EntityTypeInvocationHandler, AttachedEntityStatus>();

    public void attachNew(final EntityTypeInvocationHandler entity) {
        if (entities.containsKey(entity)) {
            throw new IllegalStateException("An entity with the same key has already been attached");
        }
        entities.put(entity, AttachedEntityStatus.NEW);
    }

    public void attach(final EntityTypeInvocationHandler entity) {
        attach(entity, AttachedEntityStatus.ATTACHED);
    }

    public void attach(final EntityTypeInvocationHandler entity, final AttachedEntityStatus status) {
        if (entities.containsKey(entity)) {
            throw new IllegalStateException("An entity with the same key has already been attached");
        }

        entities.put(entity, status);

        if (entity.getKey() != null) {
            final EntityTypeDesc desc = entity.getDescription();

            final AttachedEntities attachedEntities;
            if (attached.containsKey(desc)) {
                attachedEntities = attached.get(desc);
            } else {
                attachedEntities = new AttachedEntities();
                attached.put(desc, attachedEntities);
            }

            if (attachedEntities.contains(entity.getKey())) {
                throw new IllegalStateException("An entity with the same key has already been attached");
            }

            attachedEntities.add(entity.getKey(), entity);
        }
    }

    public void detach(final EntityTypeInvocationHandler entity) {

        if (attached.containsKey(entity.getDescription())) {
            final AttachedEntities attachedEntities = attached.get(entity.getDescription());
            attachedEntities.remove(entity.getKey());
            if(attachedEntities.isEmpty()){
                attached.remove(entity.getDescription());
            }
        }

        entities.remove(entity);
    }

    public EntityTypeInvocationHandler getEntity(final Object key) {
        final Set<EntityTypeInvocationHandler> res = new HashSet<EntityTypeInvocationHandler>();
        for (AttachedEntities attachedEntities : attached.values()) {
            if (attachedEntities.contains(key)) {
                res.add(attachedEntities.getEntity(key));
            }
        }

        if (res.size() > 1) {
            throw new IllegalStateException(
                    "More than one entities with the specified key have been found in the context");
        }

        return res.isEmpty() ? null : res.iterator().next();
    }

    public AttachedEntityStatus getStatus(final EntityTypeInvocationHandler entity) {
        if (!isAttached(entity)) {
            throw new IllegalStateException("Entity is not in the context");
        }

        return entities.get(entity);
    }

    public void setStatus(final EntityTypeInvocationHandler entity, final AttachedEntityStatus status) {
        if (!isAttached(entity)) {
            throw new IllegalStateException("Entity is not in the context");
        }

        final AttachedEntityStatus current = entities.get(entity);

        // Previously deleted object cannot be modified anymore.
        if (current == AttachedEntityStatus.DELETED) {
            throw new IllegalStateException("Entity has been previously deleted");
        }

        if (status == AttachedEntityStatus.NEW || status == AttachedEntityStatus.ATTACHED) {
            throw new IllegalStateException("Entity status has already been initialized");
        }

        if ((status == AttachedEntityStatus.LINKED && current == AttachedEntityStatus.ATTACHED)
                || (status == AttachedEntityStatus.CHANGED && current == AttachedEntityStatus.ATTACHED)
                || (status == AttachedEntityStatus.CHANGED && current == AttachedEntityStatus.LINKED)
                || (status == AttachedEntityStatus.DELETED)) {
            entities.put(entity, status);
        }
    }

    public boolean isAttached(final EntityTypeInvocationHandler entity) {
        return entities.containsKey(entity);
    }
}
