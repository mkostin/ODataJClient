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
package com.msopentech.odatajclient.proxy.api.impl;

import static com.msopentech.odatajclient.engine.data.ODataLinkType.ENTITY_NAVIGATION;
import static com.msopentech.odatajclient.engine.data.ODataLinkType.ENTITY_SET_NAVIGATION;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.CHANGED;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.DELETED;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.NEW;

import com.msopentech.odatajclient.engine.communication.header.ODataHeaderValues;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchResponseItem;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangesetResponseItem;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityUpdateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.proxy.api.AbstractContainer;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.annotations.EntityContainer;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntity;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.context.EntityLinkDesc;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import com.msopentech.odatajclient.proxy.utils.EngineUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityContainerInvocationHandler extends AbstractInvocationHandler implements AbstractContainer {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntityContainerInvocationHandler.class);

    protected final String schemaName;

    private final String entityContainerName;

    private final boolean defaultEntityContainer;

    public EntityContainerInvocationHandler(final Class<?> ref, final EntityContainerFactory factory) {
        super(factory);

        if (!ref.isInterface()) {
            throw new IllegalArgumentException(ref.getName() + " is not an interface");
        }

        final Annotation annotation = ref.getAnnotation(EntityContainer.class);
        if (!(annotation instanceof EntityContainer)) {
            throw new IllegalArgumentException(ref.getName()
                    + " is not annotated as @" + EntityContainer.class.getSimpleName());
        }
        this.entityContainerName = ((EntityContainer) annotation).name();
        this.defaultEntityContainer = ((EntityContainer) annotation).isDefaultEntityContainer();
        this.schemaName = ClassUtils.getNamespace(ref);
    }

    public boolean isDefaultEntityContainer() {
        return defaultEntityContainer;
    }

    public String getEntityContainerName() {
        return entityContainerName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (isSelfMethod(method, args)) {
            return invokeSelfMethod(method, args);
        } else {
            final Annotation[] methodAnnots = method.getAnnotations();
            // 1. access top-level entity sets
            if (methodAnnots.length == 0) {
                final Class<?> returnType = method.getReturnType();

                return Proxy.newProxyInstance(returnType.getClassLoader(), new Class<?>[] {returnType},
                        EntitySetInvocationHandler.getInstance(returnType, this));
            } // 2. invoke function imports
            else {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    }

    /**
     * Transactional changes commit.
     */
    @Override
    public void flush() {
        final ODataBatchRequest request = ODataBatchRequestFactory.getBatchRequest(factory.getServiceRoot());
        final ODataBatchRequest.BatchStreamManager streamManager = request.execute();
        final ODataChangeset changeset = streamManager.addChangeset();

        final TransactionItems items = new TransactionItems();
        final List<EntityLinkDesc> delayedUpdates = new ArrayList<EntityLinkDesc>();

        int pos = 0;

        for (AttachedEntity attachedEntity : EntityContainerFactory.getContext().entityContext()) {
            final AttachedEntityStatus status = attachedEntity.getStatus();
            if (((status != AttachedEntityStatus.ATTACHED
                    && status != AttachedEntityStatus.LINKED) || attachedEntity.getEntity().isChanged())
                    && !items.contains(attachedEntity.getEntity())) {
                pos++;
                pos = processEntityContext(attachedEntity.getEntity(), pos, items, delayedUpdates, changeset);
            }
        }

        for (EntityLinkDesc delayedUpdate : delayedUpdates) {
            pos++;
            items.put(delayedUpdate.getSource(), pos);

            final ODataEntity changes = ODataFactory.newEntity(delayedUpdate.getSource().getEntity().getName());

            AttachedEntityStatus status =
                    EntityContainerFactory.getContext().entityContext().getStatus(delayedUpdate.getSource());

            final URI sourceURI;
            if (status == AttachedEntityStatus.CHANGED) {
                sourceURI = URIUtils.getURI(
                        factory.getServiceRoot(),
                        delayedUpdate.getSource().getEntity().getEditLink().toASCIIString());
            } else {
                int sourcePos = items.get(delayedUpdate.getSource());
                sourceURI = URI.create("$" + sourcePos);
            }


            for (EntityTypeInvocationHandler target : delayedUpdate.getTargets()) {
                status = EntityContainerFactory.getContext().entityContext().getStatus(target);

                final URI targetURI;
                if (status == AttachedEntityStatus.CHANGED) {
                    targetURI = URIUtils.getURI(
                            factory.getServiceRoot(), target.getEntity().getEditLink().toASCIIString());
                } else {
                    int targetPos = items.get(target);
                    targetURI = URI.create("$" + targetPos);
                }

                changes.addLink(delayedUpdate.getType() == ODataLinkType.ENTITY_NAVIGATION ? ODataFactory.
                        newEntityNavigationLink(delayedUpdate.getSourceName(), targetURI)
                        : ODataFactory.newFeedNavigationLink(delayedUpdate.getSourceName(), targetURI));

                LOG.debug("'{}' from {} to {}", new Object[] {
                    delayedUpdate.getType().name(), sourceURI, targetURI});
            }

            batchUpdate(sourceURI, changes, changeset);
        }

        final ODataBatchResponse response = streamManager.getResponse();

        if (response.getStatusCode() != 202) {
            throw new IllegalStateException("Operation failed");
        }

        final Iterator<ODataBatchResponseItem> iter = response.getBody();

        if (!items.isEmpty()) {
            if (!iter.hasNext()) {
                throw new IllegalStateException("Unexpected operation result");
            }

            final ODataBatchResponseItem item = iter.next();
            if (!(item instanceof ODataChangesetResponseItem)) {
                throw new IllegalStateException("Unexpected batch response item " + item.getClass().getSimpleName());
            }

            final ODataChangesetResponseItem chgres = (ODataChangesetResponseItem) item;

            for (Integer changesetItemId : items.sortedValues()) {
                LOG.debug("Expected changeset item {}", changesetItemId);
                final ODataResponse res = chgres.next();
                if (res.getStatusCode() >= 400) {
                    throw new IllegalStateException("Transaction failed: " + res.getStatusMessage());
                }

                final EntityTypeInvocationHandler handler = items.get(changesetItemId);

                if (res instanceof ODataEntityCreateResponse) {
                    LOG.debug("Upgrade created object '{}'", handler);
                    handler.setEntity(((ODataEntityCreateResponse) res).getBody());
                } else if (res instanceof ODataEntityUpdateResponse) {
                    LOG.debug("Upgrade updated object '{}'", handler);
                    handler.setEntity(((ODataEntityUpdateResponse) res).getBody());
                }
            }
        }

        EntityContainerFactory.getContext().detachAll();
    }

    private void batch(
            final EntityTypeInvocationHandler handler, final ODataEntity entity, final ODataChangeset changeset) {

        switch (EntityContainerFactory.getContext().entityContext().getStatus(handler)) {
            case NEW:
                batchCreate(handler, entity, changeset);
                break;

            case CHANGED:
                batchUpdate(entity, changeset);
                break;

            case DELETED:
                batchDelete(handler, entity, changeset);
                break;

            default:
                if (handler.isChanged()) {
                    batchUpdate(entity, changeset);
                }
        }
    }

    private void batchCreate(
            final EntityTypeInvocationHandler handler, final ODataEntity entity, final ODataChangeset changeset) {
        LOG.debug("Create '{}'", handler);
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(handler.factory.getServiceRoot());
        uriBuilder.appendEntitySetSegment(handler.getEntitySetName());
        changeset.addRequest(ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), entity));
    }

    private void batchUpdate(final ODataEntity changes, final ODataChangeset changeset) {
        LOG.debug("Update '{}'", changes.getEditLink());
        final ODataEntityUpdateRequest req =
                ODataCUDRequestFactory.getEntityUpdateRequest(UpdateType.PATCH, changes);
        req.setPrefer(ODataHeaderValues.preferReturnContent);
        changeset.addRequest(req);
    }

    private void batchUpdate(final URI uri, final ODataEntity changes, final ODataChangeset changeset) {
        LOG.debug("Update '{}'", uri);
        final ODataEntityUpdateRequest req =
                ODataCUDRequestFactory.getEntityUpdateRequest(uri, UpdateType.PATCH, changes);
        req.setPrefer(ODataHeaderValues.preferReturnContent);
        changeset.addRequest(req);
    }

    private void batchDelete(
            final EntityTypeInvocationHandler handler, final ODataEntity entity, final ODataChangeset changeset) {

        LOG.debug("Delete '{}'", entity.getEditLink());
        changeset.addRequest(ODataCUDRequestFactory.getDeleteRequest(URIUtils.getURI(
                handler.factory.getServiceRoot(), entity.getEditLink().toASCIIString())));
    }

    private int processEntityContext(
            final EntityTypeInvocationHandler handler,
            int pos,
            final TransactionItems items,
            final List<EntityLinkDesc> delayedUpdates,
            final ODataChangeset changeset) {

        LOG.debug("Process '{}'", handler);

        items.put(handler, null);

        final ODataEntity entity = SerializationUtils.clone(handler.getEntity());
        entity.getNavigationLinks().clear();

        if (AttachedEntityStatus.DELETED != EntityContainerFactory.getContext().entityContext().getStatus(handler)) {
            entity.getProperties().clear();
            EngineUtils.addProperties(factory.getMetadata(), handler.getPropertyChanges(), entity);
        }

        for (Map.Entry<NavigationProperty, Object> property : handler.getLinkChanges().entrySet()) {
            final ODataLinkType type = Collection.class.isAssignableFrom(property.getValue().getClass())
                    ? ODataLinkType.ENTITY_SET_NAVIGATION
                    : ODataLinkType.ENTITY_NAVIGATION;

            final Set<EntityTypeInvocationHandler> toBeLinked = new HashSet<EntityTypeInvocationHandler>();
            final String serviceRoot = factory.getServiceRoot();

            for (Object proxy : type == ODataLinkType.ENTITY_SET_NAVIGATION
                    ? (Collection) property.getValue() : Collections.singleton(property.getValue())) {

                final EntityTypeInvocationHandler target =
                        (EntityTypeInvocationHandler) Proxy.getInvocationHandler(proxy);

                final AttachedEntityStatus status =
                        EntityContainerFactory.getContext().entityContext().getStatus(target);

                final URI editLink = target.getEntity().getEditLink();

                if ((status == AttachedEntityStatus.ATTACHED || status == AttachedEntityStatus.LINKED)
                        && !target.isChanged()) {
                    entity.addLink(buildNavigationLink(
                            property.getKey().name(),
                            URIUtils.getURI(serviceRoot, editLink.toASCIIString()), type));
                } else {
                    if (!items.contains(target)) {
                        pos = processEntityContext(target, pos, items, delayedUpdates, changeset);
                        pos++;
                    }

                    final Integer targetPos = items.get(target);
                    if (targetPos == null) {
                        // schedule update for the current object
                        LOG.debug("Schedule '{}' from '{}' to '{}'", new Object[] {type.name(), handler, target});
                        toBeLinked.add(target);
                    } else if (status == AttachedEntityStatus.CHANGED) {
                        entity.addLink(buildNavigationLink(
                                property.getKey().name(),
                                URIUtils.getURI(serviceRoot, editLink.toASCIIString()), type));
                    } else {
                        // create the link for the current object
                        LOG.debug("'{}' from '{}' to (${}) '{}'",
                                new Object[] {type.name(), handler, targetPos, target});

                        entity.addLink(
                                buildNavigationLink(property.getKey().name(), URI.create("$" + targetPos), type));
                    }
                }
            }

            if (!toBeLinked.isEmpty()) {
                delayedUpdates.add(new EntityLinkDesc(property.getKey().name(), handler, toBeLinked, type));
            }
        }

        // insert into the batch
        LOG.debug("{}: Insert '{}' into the batch", pos, handler);
        batch(handler, entity, changeset);

        items.put(handler, pos);

        return pos;
    }

    private ODataLink buildNavigationLink(final String name, final URI uri, final ODataLinkType type) {
        switch (type) {
            case ENTITY_NAVIGATION:
                return ODataFactory.newEntityNavigationLink(name, uri);

            case ENTITY_SET_NAVIGATION:
                return ODataFactory.newFeedNavigationLink(name, uri);

            default:
                throw new IllegalArgumentException("Invalid link type " + type.name());
        }
    }

    private class TransactionItems {

        private final List<EntityTypeInvocationHandler> keys = new ArrayList<EntityTypeInvocationHandler>();

        private final List<Integer> values = new ArrayList<Integer>();

        public EntityTypeInvocationHandler get(final Integer value) {
            if (value != null && values.contains(value)) {
                return keys.get(values.indexOf(value));
            } else {
                return null;
            }
        }

        public Integer get(final EntityTypeInvocationHandler key) {
            if (key != null && keys.contains(key)) {
                return values.get(keys.indexOf(key));
            } else {
                return null;
            }
        }

        public void remove(final EntityTypeInvocationHandler key) {
            if (keys.contains(key)) {
                values.remove(keys.indexOf(key));
                keys.remove(key);
            }
        }

        public void put(final EntityTypeInvocationHandler key, final Integer value) {
            // replace just in case of null current value; otherwise add the new entry
            if (keys.contains(key) && values.get(keys.indexOf(key)) == null) {
                remove(key);
            }
            keys.add(key);
            values.add(value);
        }

        public List<Integer> sortedValues() {
            final List<Integer> sortedValues = new ArrayList<Integer>(values);
            Collections.<Integer>sort(sortedValues);
            return sortedValues;
        }

        public boolean contains(final EntityTypeInvocationHandler key) {
            return keys.contains(key);
        }

        public int size() {
            return keys.size();
        }

        public boolean isEmpty() {
            return keys.isEmpty();
        }
    }
}
