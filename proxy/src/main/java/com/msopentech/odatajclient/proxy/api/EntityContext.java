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
        entities.put(entity, AttachedEntityStatus.ATTACHED);
    }

    public void attach(final EntityTypeInvocationHandler entity) {
        attach(entity, AttachedEntityStatus.ATTACHED);
    }

    public void attach(final EntityTypeInvocationHandler entity, final AttachedEntityStatus status) {
        if (entities.containsKey(entity)) {
            throw new IllegalStateException("An entity with the same key has already been attached");
        }

        entities.put(entity, status);

        final EntityTypeDesc desc = getEntityTypeDesc(entity);

        final AttachedEntities attachedEntities;
        if (attached.containsKey(desc)) {
            attachedEntities = attached.get(desc);
        } else {
            attachedEntities = new AttachedEntities();
            attached.put(desc, attachedEntities);
        }

//        final String key = entity.serializeEntityKey();
//
//        if (attachedEntities.contains(key)) {
//            throw new IllegalStateException("An entity with the same key has already been attached");
//        }
//
//        attachedEntities.add(key, entity);
    }

    public void detach(final EntityTypeInvocationHandler entity) {
        final EntityTypeDesc desc = getEntityTypeDesc(entity);

        if (attached.containsKey(desc)) {
//            attached.get(desc).remove(entity.serializeEntityKey());
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
                    "More than one entities with the specified keay have been found in the context");
        }

        return res.isEmpty() ? null : res.iterator().next();
    }

    public AttachedEntityStatus getStatus(final EntityTypeInvocationHandler entity) {
        if (!isAttached(entity)) {
            throw new IllegalStateException("Entity is not in the context");
        }

        return entities.get(entity);
    }

    public boolean isAttached(final EntityTypeInvocationHandler entity) {
        return entities.containsKey(entity);
    }

    private EntityTypeDesc getEntityTypeDesc(final EntityTypeInvocationHandler entity) {
        return new EntityTypeDesc(
                Utility.getSchemaName(entity.getTypeRef()),
                entity.getEntityContainerName(),
                entity.getEntitySetName(),
                entity.getName());
    }
}
