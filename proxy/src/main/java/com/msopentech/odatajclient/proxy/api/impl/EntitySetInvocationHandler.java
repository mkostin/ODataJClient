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

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.context.EntityContext;
import com.msopentech.odatajclient.proxy.api.context.EntityUUID;
import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EntitySetInvocationHandler<
        T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>>
        extends AbstractInvocationHandler
        implements AbstractEntitySet<T, KEY, EC> {

    private static final long serialVersionUID = 2629912294765040027L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntitySetInvocationHandler.class);

    private final Class<T> typeRef;

    private final Class<KEY> keyRef;

    private final Class<EC> typeCollectionRef;

    private final String entitySetName;

    private final EntityContainerInvocationHandler container;

    private final URI uri;

    static <T extends Serializable, KEY extends Serializable, EC extends AbstractEntityCollection<T>> EntitySetInvocationHandler<T, KEY, EC> getInstance(
            final Class<?> ref,
            final EntityContainerInvocationHandler container) {

        return new EntitySetInvocationHandler<T, KEY, EC>(ref, container);
    }

    @SuppressWarnings("unchecked")
    private EntitySetInvocationHandler(
            final Class<?> ref,
            final EntityContainerInvocationHandler container) {

        super(container.factory);

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
        this.typeCollectionRef = (Class<EC>) abstractEntitySetParams[2];

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(container.factory.getServiceRoot());

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

    public Class<EC> getTypeCollectionRef() {
        return typeCollectionRef;
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
        if (isSelfMethod(method, args)) {
            return invokeSelfMethod(method, args);
        } else if (method.getName().startsWith("new")) {
            if (method.getName().endsWith("Collection")) {
                return newEntityCollection(method.getReturnType());
            } else {
                return newEntity(method.getReturnType());
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @SuppressWarnings("unchecked")
    private <NE> NE newEntity(final Class<NE> reference) {
        final ODataEntity entity =
                ODataFactory.newEntity(container.getSchemaName() + "." + ClassUtils.getEntityTypeName(reference));

        final EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(entity, this);
        EntityContainerFactory.getContext().entityContext().attachNew(handler);

        return (NE) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {reference}, handler);
    }

    @SuppressWarnings("unchecked")
    private <NEC> NEC newEntityCollection(final Class<NEC> reference) {
        return (NEC) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {reference},
                new EntityCollectionInvocationHandler<T>(new ArrayList<T>(), factory));
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

    private LinkedHashMap<String, Object> getCompoundKey(final Object key) {
        final Set<CompoundKeyElementWrapper> elements = new TreeSet<CompoundKeyElementWrapper>();

        for (Method method : key.getClass().getMethods()) {
            final Annotation annotation = method.getAnnotation(CompoundKeyElement.class);
            if (annotation instanceof CompoundKeyElement) {
                elements.add(new CompoundKeyElementWrapper(
                        ((CompoundKeyElement) annotation).name(), method, ((CompoundKeyElement) annotation).position()));
            }
        }

        final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        for (CompoundKeyElementWrapper element : elements) {
            try {
                map.put(element.getName(), element.getMethod().invoke(key));
            } catch (Exception e) {
                LOG.warn("Error retrieving compound key element '{}' value", element.getName(), e);
            }
        }

        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final KEY key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }

        final EntityUUID uuid = new EntityUUID(
                ClassUtils.getNamespace(typeRef),
                container.getEntityContainerName(),
                entitySetName,
                ClassUtils.getNamespace(typeRef) + "." + ClassUtils.getEntityTypeName(typeRef),
                key);

        EntityTypeInvocationHandler handler =
                EntityContainerFactory.getContext().entityContext().getEntity(uuid);

        if (handler == null) {
            // not yet attached: search against the service
            try {
                final ODataURIBuilder uriBuilder = new ODataURIBuilder(this.uri.toASCIIString());

                if (key.getClass().getAnnotation(CompoundKey.class) == null) {
                    uriBuilder.appendKeySegment(key);
                } else {
                    uriBuilder.appendKeySegment(getCompoundKey(key));
                }

                handler = EntityTypeInvocationHandler.getInstance(
                        ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build()).execute().getBody(), this);

            } catch (Exception e) {
                LOG.info("Entity '" + uuid + "' not found", e);
            }
        } else if (isDeleted(handler)) {
            // object deleted
            handler = null;
        }

        return handler == null ? null : (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {typeRef},
                handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public EC getAll() {
        final ODataEntitySet entitySet = ODataRetrieveRequestFactory.getEntitySetRequest(this.uri).execute().getBody();

        final List<T> items = new ArrayList<T>(entitySet.getEntities().size());
        for (ODataEntity entity : entitySet.getEntities()) {
            final EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(entity, this);

            final EntityTypeInvocationHandler handlerInTheContext =
                    EntityContainerFactory.getContext().entityContext().getEntity(handler.getUUID());
            items.add((T) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[] {typeRef},
                    handlerInTheContext == null ? handler : handlerInTheContext));
        }

        return (EC) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {typeCollectionRef},
                new EntityCollectionInvocationHandler<T>(items, container.factory));
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
        final EntityContext entityContext = EntityContainerFactory.getContext().entityContext();

        EntityTypeInvocationHandler entity = entityContext.getEntity(new EntityUUID(
                ClassUtils.getNamespace(typeRef),
                container.getEntityContainerName(),
                entitySetName,
                ClassUtils.getNamespace(typeRef) + "." + ClassUtils.getEntityTypeName(typeRef),
                key));

        if (entity == null) {
            // search for entity
            final T searched = get(key);
            entity = (EntityTypeInvocationHandler) Proxy.getInvocationHandler(searched);
            entityContext.attach(entity, AttachedEntityStatus.DELETED);
        } else {
            entityContext.setStatus(entity, AttachedEntityStatus.DELETED);
        }
    }

    @Override
    public void delete(final Iterable<T> entities) {
        final EntityContext entityContext = EntityContainerFactory.getContext().entityContext();

        for (T en : entities) {
            final EntityTypeInvocationHandler entity = (EntityTypeInvocationHandler) Proxy.getInvocationHandler(en);
            if (entityContext.isAttached(entity)) {
                entityContext.setStatus(entity, AttachedEntityStatus.DELETED);
            } else {
                entityContext.attach(entity, AttachedEntityStatus.DELETED);
            }
        }
    }

    private boolean isDeleted(final EntityTypeInvocationHandler handler) {
        return EntityContainerFactory.getContext().entityContext().getStatus(handler) == AttachedEntityStatus.DELETED;
    }
}
