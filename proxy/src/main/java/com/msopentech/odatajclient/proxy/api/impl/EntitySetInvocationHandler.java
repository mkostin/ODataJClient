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
import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
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

    private final String entitySetName;

    private final URI uri;

    static <T extends Serializable, KEY extends Serializable> EntitySetInvocationHandler<T, KEY> getInstance(
            final Class<T> typeRef,
            final Class<KEY> keyRef,
            final String entitySetName,
            final EntityContainerInvocationHandler container) {

        return new EntitySetInvocationHandler<T, KEY>(typeRef, entitySetName, container);
    }

    private EntitySetInvocationHandler(
            final Class<T> typeRef,
            final String entitySetName,
            final EntityContainerInvocationHandler container) {
        super(container);
        this.typeRef = typeRef;
        this.entitySetName = entitySetName;
        this.uri = new ODataURIBuilder(container.getServiceRoot()).appendEntitySetSegment(entitySetName).build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if ("count".equals(method.getName())) {
            return count();
        } else if ("exists".equals(method.getName())) {
            return exists((KEY) args[0]);
        } else if ("get".equals(method.getName())) {
            return get((KEY) args[0]);
        } else if ("getAll".equals(method.getName())) {
            return getAll();
        } else if ("newEntity".equals(method.getName())) {
            return newEntity();
        }

        throw new UnsupportedOperationException("Not supported yet.");
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

        final ODataURIBuilder uriBuilder = new ODataURIBuilder(this.uri.toASCIIString());

        if (key.getClass().getAnnotation(CompoundKey.class) == null) {
            uriBuilder.appendKeySegment(key.toString());
        } else {
            uriBuilder.appendKeySegment(getCompoundKey(key));
        }

        final ODataEntity entity =
                ODataRetrieveRequestFactory.getEntityRequest(uriBuilder.build()).execute().getBody();

        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class<?>[] {typeRef},
                EntityTypeInvocationHandler.getInstance(typeRef, entity, entitySetName, container));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<T> getAll() {
        final Annotation annotation = typeRef.getAnnotation(EntityType.class);
        if (!(annotation instanceof EntityType)) {
            throw new IllegalArgumentException("Invalid entity set '" + typeRef.getSimpleName() + "'");
        }

        final ODataEntitySet entitySet = ODataRetrieveRequestFactory.getEntitySetRequest(this.uri).execute().getBody();

        final List<T> beans = new ArrayList<T>(entitySet.getEntities().size());
        for (ODataEntity entity : entitySet.getEntities()) {
            beans.add(
                    (T) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class<?>[] {typeRef},
                    EntityTypeInvocationHandler.getInstance(typeRef, entity, entitySetName, container)));
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(final Iterable<T> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public T newEntity() {
        final ODataEntity entity = ODataFactory.newEntity(container.getSchemaName() + "." + getEntityName(typeRef));

        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class<?>[] {typeRef},
                EntityTypeInvocationHandler.getInstance(typeRef, entity, entitySetName, container));
    }
}
