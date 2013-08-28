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

import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.CHANGED;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.DELETED;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.DELETED_IN_BATCH;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.NEW;
import static com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus.NEW_IN_BATCH;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequest;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchResponseItem;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangeset;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataChangesetResponseItem;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataBatchResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataEntityCreateResponse;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.AttachedEntity;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.Utility;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.context.EntityContext;
import com.msopentech.odatajclient.proxy.api.context.EntityLinkDesc;
import com.msopentech.odatajclient.proxy.api.context.EntityUUID;
import com.msopentech.odatajclient.proxy.api.context.LinkContext;
import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EntitySetInvocationHandler<T extends Serializable, KEY extends Serializable> extends AbstractInvocationHandler
        implements AbstractEntitySet<T, KEY> {

    private static final long serialVersionUID = 2629912294765040027L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntitySetInvocationHandler.class);

    private final Class<T> typeRef;

    private final Class<KEY> keyRef;

    private final String entitySetName;

    private final EntityContainerInvocationHandler container;

    private final URI uri;

    static <T extends Serializable, KEY extends Serializable> EntitySetInvocationHandler<T, KEY> getInstance(
            final Class<?> ref,
            final EntityContainerInvocationHandler container) {
        return new EntitySetInvocationHandler<T, KEY>(ref, container);
    }

    @SuppressWarnings("unchecked")
    private EntitySetInvocationHandler(
            final Class<?> ref,
            final EntityContainerInvocationHandler container) {
        super(container.getFactory());

        this.container = container;

        final Annotation annotation = ref.getAnnotation(EntitySet.class);
        if (!(annotation instanceof EntitySet)) {
            throw new IllegalArgumentException("Return type " + ref.getName()
                    + " is not annotated as @" + EntitySet.class.getSimpleName());
        }

        this.entitySetName = ((EntitySet) annotation).name();

        final Type[] abstractEntitySetParams =
                ((ParameterizedType) ref.getGenericInterfaces()[0]).getActualTypeArguments();

        this.typeRef = (Class<T>) abstractEntitySetParams[0];

        if (typeRef.getAnnotation(EntityType.class) == null) {
            throw new IllegalArgumentException("Invalid entity '" + typeRef.getSimpleName() + "'");
        }

        this.keyRef = (Class<KEY>) abstractEntitySetParams[1];

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(container.getFactory().getServiceRoot());

        if (!container.isDefaultEntityContainer()) {
            uriBuilder.appendStructuralSegment(container.getEntityContainerName()).appendStructuralSegment(".");
        }

        uriBuilder.appendEntitySetSegment(entitySetName);
        this.uri = uriBuilder.build();
    }

    public Class<T> getTypeRef() {
        return typeRef;
    }

    public Class<KEY> getKeyRef() {
        return keyRef;
    }

    public String getEntitySetName() {
        return entitySetName;
    }

    public EntityContainerInvocationHandler getContainer() {
        return container;
    }

    public URI getUri() {
        return uri;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if ("delete".equals(method.getName())) {
            if (args[0] instanceof Collection) {
                delete((Collection<T>) args[0]);
            } else {
                delete((KEY) args[0]);
            }
        } else if ("count".equals(method.getName())) {
            return count();
        } else if ("exists".equals(method.getName())) {
            return exists((KEY) args[0]);
        } else if ("get".equals(method.getName())) {
            return get((KEY) args[0]);
        } else if ("getAll".equals(method.getName())) {
            return getAll();
        } else if ("newEntity".equals(method.getName())) {
            return newEntity();
        } else if ("flush".equals(method.getName())) {
            flush();
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        final Constructor<Void> cv = Void.class.getDeclaredConstructor();
        cv.setAccessible(true);
        return cv.newInstance();
    }

    @Override
    public Long count() {
        final ODataValueRequest req = ODataRetrieveRequestFactory.
                getValueRequest(new ODataURIBuilder(this.uri.toASCIIString()).appendCountSegment().build());
        req.setFormat(ODataValueFormat.TEXT);
        return Long.valueOf(req.execute().getBody().asPrimitive().toString());
    }

    @Override
    public Boolean exists(final KEY key) throws IllegalArgumentException {
        boolean result = false;

        try {
            result = get(key) != null;
        } catch (Exception e) {
            LOG.error("Could not check existence of {}({})", this.entitySetName, key, e);
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final KEY key) throws IllegalArgumentException {

        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }

        final EntityUUID uuid = new EntityUUID(
                Utility.getNamespace(typeRef),
                container.getEntityContainerName(),
                entitySetName,
                Utility.getNamespace(typeRef) + "." + Utility.getEntityTypeName(typeRef),
                key);

        EntityTypeInvocationHandler handler = EntityContainerFactory.getContext().getEntityContext().getEntity(uuid);

        try {
            if (handler == null) {
                final ODataURIBuilder uriBuilder = new ODataURIBuilder(this.uri.toASCIIString());

                if (key.getClass().getAnnotation(CompoundKey.class) == null) {
                    uriBuilder.appendKeySegment(key);
                } else {
                    uriBuilder.appendKeySegment(getCompoundKey(key));
                }

                handler = EntityTypeInvocationHandler.getInstance(
                        ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build()).execute().getBody(), this);
            }

            return (T) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class<?>[] {typeRef},
                    handler);
        } catch (Exception e) {
            LOG.info("Entity '" + uuid + "'not found");
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<T> getAll() {
        final ODataEntitySet entitySet = ODataRetrieveRequestFactory.getEntitySetRequest(this.uri).execute().getBody();

        final List<T> beans = new ArrayList<T>(entitySet.getEntities().size());
        for (ODataEntity entity : entitySet.getEntities()) {
            EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(entity, this);

            EntityTypeInvocationHandler handlerInTheContext =
                    EntityContainerFactory.getContext().getEntityContext().getEntity(handler.getUUID());

            beans.add((T) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class<?>[] {typeRef},
                    handlerInTheContext == null ? handler : handlerInTheContext));
        }

        return beans;
    }

    @Override
    public Query createQuery() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Serializable> EntityQuery<E> createQuery(final Class<E> entityClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final KEY key) throws IllegalArgumentException {
        final EntityContext entityContext = EntityContainerFactory.getContext().getEntityContext();

        EntityTypeInvocationHandler entity = entityContext.getEntity(new EntityUUID(
                Utility.getNamespace(typeRef),
                container.getEntityContainerName(),
                entitySetName,
                Utility.getEntityTypeName(typeRef),
                key));

        if (entity == null) {
            // search for entity
            final T en = get(key);
            entity = (EntityTypeInvocationHandler) Proxy.getInvocationHandler(en);
            entityContext.attach(entity, AttachedEntityStatus.DELETED);
        } else {
            entityContext.setStatus(entity, AttachedEntityStatus.DELETED);
        }
    }

    @Override
    public void delete(final Iterable<T> entities) {
        final EntityContext entityContext = EntityContainerFactory.getContext().getEntityContext();

        for (T en : entities) {
            final EntityTypeInvocationHandler entity = (EntityTypeInvocationHandler) Proxy.getInvocationHandler(en);
            if (entityContext.isAttached(entity)) {
                entityContext.setStatus(entity, AttachedEntityStatus.DELETED);
            } else {
                entityContext.attach(entity, AttachedEntityStatus.DELETED);
            }
        }
    }

    /**
     * Transactional changes commit.
     */
    @Override
    public void flush() {
        final ODataBatchRequest request = ODataBatchRequestFactory.getBatchRequest(getFactory().getServiceRoot());
        final ODataBatchRequest.BatchStreamManager streamManager = request.execute();
        final ODataChangeset changeset = streamManager.addChangeset();

        final EntityContext entityContext = EntityContainerFactory.getContext().getEntityContext();
        final LinkContext linkContext = EntityContainerFactory.getContext().getLinkContext();

        // process link context
        for (EntityLinkDesc link : linkContext) {
            if (!entityContext.isAttached(link.getSource())) {
                throw new IllegalStateException("Link source '" + link.getSource() + "' not attached");
            }

            for (EntityTypeInvocationHandler target : link.getTargets()) {
                final URI uri;

                // if the target is new then create it before and add the link to the source
                final AttachedEntityStatus status = entityContext.getStatus(target);
                switch (status) {
                    case DELETED:
                    case DELETED_IN_BATCH:
                        // ignore the link
                        uri = null;
                        break;
                    case NEW:
                        entityContext.setStatus(target, AttachedEntityStatus.NEW_IN_BATCH);
                    // follow on with NEW_IN_BATCH case ...
                    case NEW_IN_BATCH:
                        // create target
                        flushCreate(target, changeset);
                        uri = URI.create("$" + changeset.getLastContentId());
                        break;
                    case LINKED:
                    case ATTACHED:
                        entityContext.detach(target);
                    default:
                        uri = URIUtils.getURI(
                                getFactory().getServiceRoot(),
                                target.getEntity().getEditLink().toASCIIString());

                }

                if (uri != null) {

                    final ODataLink odataLink = link.getType() == ODataLinkType.ENTITY_NAVIGATION
                            ? ODataFactory.newEntityNavigationLink(link.getSourceName(), uri)
                            : ODataFactory.newFeedNavigationLink(link.getSourceName(), uri);

                    // add link to the source
                    link.getSource().getEntity().addLink(odataLink);
                }
            }
        }

        // Keep the link context as is in order to be able to perform a second flush in case of errors.
        // Entity and link references into the context cannot be lost untill the successful "commit".

        // process entity context
        for (AttachedEntity attachedEntity : entityContext) {
            switch (attachedEntity.getStatus()) {
                case NEW:
                    flushCreate(attachedEntity.getEntity(), changeset);
                    break;
                case CHANGED:
                    flushUpdate(attachedEntity.getEntity(), changeset);
                    break;
                case DELETED:
                    flushDelete(attachedEntity.getEntity(), changeset);
                    break;
                case NEW_IN_BATCH:
                case CHANGED_IN_BATCH:
                case DELETED_IN_BATCH:
                    // do nothing (it should be processed before during linkContext scan)
                    break;
                default:
                    entityContext.detach(attachedEntity.getEntity());
            }
        }

        final ODataBatchResponse response = streamManager.getResponse();

        if (response.getStatusCode() != 202) {
            throw new IllegalStateException("Operation failed");
        }

        final Iterator<ODataBatchResponseItem> iter = response.getBody();

        if (!iter.hasNext()) {
            throw new IllegalStateException("Unexpected operation");
        }

        final ODataBatchResponseItem item = iter.next();
        if (!(item instanceof ODataChangesetResponseItem)) {
            throw new IllegalStateException("Unexpected batch response item " + item.getClass().getSimpleName());
        }

        final ODataChangesetResponseItem chgres = (ODataChangesetResponseItem) item;

        // update entities
        for (AttachedEntity attachedEntity : entityContext) {
            final ODataResponse res = chgres.next();
            if (res.getStatusCode() >= 400) {
                throw new IllegalStateException("Transaction failed: " + res.getStatusMessage());
            }

            switch (attachedEntity.getStatus()) {
                case NEW:
                case NEW_IN_BATCH:
                    if (res instanceof ODataEntityCreateResponse) {
                        // detach before updating
                        final EntityTypeInvocationHandler handler = attachedEntity.getEntity();
                        entityContext.detach(handler);
                        handler.setEntity(((ODataEntityCreateResponse) res).getBody());
                    } else {
                        LOG.error("Cannot update referenced entity " + attachedEntity.getEntity());
                    }
                    break;
                case CHANGED:
                case CHANGED_IN_BATCH:
                case DELETED:
                case DELETED_IN_BATCH:
                    // nothing to be updated just detach
                    entityContext.detach(attachedEntity.getEntity());
                    break;
                default:
                    // Maybe an error: default statuses have been removed before.
                    // Log and ignore anyway ...
                    LOG.warn("Unexpected attached entity status " + attachedEntity.getStatus());
            }
        }

        linkContext.detachAll();
        entityContext.detachAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T newEntity() {
        final ODataEntity entity =
                ODataFactory.newEntity(container.getSchemaName() + "." + Utility.getEntityTypeName(typeRef));

        final EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(entity, this);
        EntityContainerFactory.getContext().getEntityContext().attachNew(handler);

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] {typeRef}, handler);
    }

    private void flushCreate(final EntityTypeInvocationHandler entity, final ODataChangeset changeset) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(entity.getFactory().getServiceRoot());
        uriBuilder.appendEntitySetSegment(entity.getEntitySetName());
        changeset.addRequest(ODataCUDRequestFactory.getEntityCreateRequest(uriBuilder.build(), entity.getEntity()));
    }

    private void flushUpdate(final EntityTypeInvocationHandler entity, final ODataChangeset changeset) {
        changeset.addRequest(
                ODataCUDRequestFactory.getEntityUpdateRequest(UpdateType.REPLACE, entity.getEntity()));
    }

    private void flushDelete(final EntityTypeInvocationHandler entity, final ODataChangeset changeset) {
        changeset.addRequest(ODataCUDRequestFactory.getDeleteRequest(URIUtils.getURI(
                entity.getFactory().getServiceRoot(), entity.getEntity().getEditLink().toASCIIString())));
    }
}
