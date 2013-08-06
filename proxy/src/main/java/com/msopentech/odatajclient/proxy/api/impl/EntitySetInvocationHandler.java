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

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import com.msopentech.odatajclient.engine.format.ODataValueFormat;
import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.query.EntityQuery;
import com.msopentech.odatajclient.proxy.api.query.Query;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EntitySetInvocationHandler<T extends Serializable, KEY extends Serializable>
        implements InvocationHandler, AbstractEntitySet<T, KEY> {

    private static final long serialVersionUID = 2629912294765040027L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntitySetInvocationHandler.class);

    private final Class<T> typeRef;

    private final String entitySetName;

    private final ODataURIBuilder uriBuilder;

    static <T extends Serializable, KEY extends Serializable> EntitySetInvocationHandler<T, KEY> getInstance(
            final Class<T> typeRef, final Class<KEY> keyRef, final String serviceRoot, final String entitySetName) {

        return new EntitySetInvocationHandler<T, KEY>(typeRef, serviceRoot, entitySetName);
    }

    private EntitySetInvocationHandler(final Class<T> typeRef, final String serviceRoot, final String entitySetName) {
        this.typeRef = typeRef;
        this.entitySetName = entitySetName;
        this.uriBuilder = new ODataURIBuilder(serviceRoot).appendEntitySetSegment(entitySetName);
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
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long count() {
        final ODataValueRequest req = ODataRetrieveRequestFactory.
                getValueRequest(this.uriBuilder.appendCountSegment().build());
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

    private String findPropertyByAnnotatedName(final Class<?> clazz, final String name) {
        final Field[] fields = clazz.getDeclaredFields();

        String result = null;
        for (int i = 0; i < fields.length && result == null; i++) {
            final Annotation annotation = fields[i].getAnnotation(Property.class);
            if ((annotation instanceof Property) && name.equals(((Property) annotation).name())) {
                result = fields[i].getName();
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private void populate(final Object bean, final Iterator<ODataProperty> propItor) {
        while (propItor.hasNext()) {
            final ODataProperty property = propItor.next();

            final String beanPropName = findPropertyByAnnotatedName(bean.getClass(), property.getName());
            if (beanPropName == null) {
                LOG.warn("Could not find any property annotated as {} in {}",
                        property.getName(), bean.getClass().getName());
            } else {
                try {
                    if (property.hasNullValue()) {
                        PropertyUtils.setProperty(bean, beanPropName, null);
                    }
                    if (property.hasPrimitiveValue()) {
                        BeanUtils.setProperty(bean, beanPropName, property.getPrimitiveValue().toValue());
                    }
                    if (property.hasComplexValue()) {
                        final Object complex = bean.getClass().getDeclaredField(beanPropName).getType().newInstance();
                        populate(complex, property.getComplexValue().iterator());
                        BeanUtils.setProperty(bean, beanPropName, complex);
                    }
                    if (property.hasCollectionValue()) {
                        final ParameterizedType collType =
                                (ParameterizedType) bean.getClass().getDeclaredField(beanPropName).getGenericType();
                        final Class<?> collItemClass = (Class<?>) collType.getActualTypeArguments()[0];

                        final Method collGetter =
                                PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(bean, beanPropName));

                        final Iterator<ODataValue> collPropItor = property.getCollectionValue().iterator();
                        while (collPropItor.hasNext()) {
                            final ODataValue value = collPropItor.next();
                            if (value.isPrimitive()) {
                                ((Collection) collGetter.invoke(bean)).add(value.asPrimitive().toValue());
                            }
                            if (value.isComplex()) {
                                final Object collItem = collItemClass.newInstance();
                                populate(collItem, value.asComplex().iterator());
                                ((Collection) collGetter.invoke(bean)).add(collItem);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Could not set property {} on {}", beanPropName, bean, e);
                }
            }
        }
    }

    private T entity2Bean(final ODataEntity entity) {
        T bean;

        try {
            bean = typeRef.newInstance();
        } catch (Exception e) {
            LOG.error("While creating new instance of {}", typeRef.getName(), e);
            throw new IllegalArgumentException("While creating new instance of " + typeRef.getName(), e);
        }

        populate(bean, entity.getProperties().iterator());

        return bean;
    }

    @Override
    public T get(final KEY key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }

        final ODataEntityRequest req = ODataRetrieveRequestFactory.
                getEntityRequest(this.uriBuilder.appendKeySegment(key.toString()).build());
        // TODO: keep this until #102 is fixed
        req.setFormat(ODataPubFormat.ATOM);
        final ODataEntity entity = req.execute().getBody();

        return entity2Bean(entity);
    }

    @Override
    public Iterable<T> getAll() {
        final ODataEntitySetRequest req = ODataRetrieveRequestFactory.
                getEntitySetRequest(this.uriBuilder.build());
        // TODO: keep this until #102 is fixed
        req.setFormat(ODataPubFormat.ATOM);
        final ODataEntitySet entitySet = req.execute().getBody();

        final List<T> beans = new ArrayList<T>(entitySet.getEntities().size());
        for (ODataEntity entity : entitySet.getEntities()) {
            beans.add(entity2Bean(entity));
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
    public T save(final T entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T saveAndFlush(final T entity) {
        final T saved = save(entity);
        flush();
        return saved;
    }

    @Override
    public Iterable<T> save(final Iterable<T> entities) {
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
}
