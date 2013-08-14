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
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EntityTypeInvocationHandler<T extends Serializable> extends AbstractInvocationHandler {

    private static final long serialVersionUID = 2629912294765040037L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntityTypeInvocationHandler.class);

    private final Class<?> entityType;

    private final ODataEntity entity;

    private final URI uri;

    private final String entitySetName;

    static <T extends Serializable> EntityTypeInvocationHandler<T> getInstance(
            final Class<?> entityType,
            final ODataEntity entity,
            final String entitySetName,
            final EntityContainerInvocationHandler container) {

        return new EntityTypeInvocationHandler<T>(entityType, entity, entitySetName, container);
    }

    private EntityTypeInvocationHandler(
            final Class<?> entityType,
            final ODataEntity entity,
            final String entitySetName,
            final EntityContainerInvocationHandler container) {
        super(container);
        this.entitySetName = entitySetName;
        this.entityType = entityType;
        this.entity = entity;
        this.uri = new ODataURIBuilder(container.getServiceRoot()).appendEntitySetSegment(entitySetName).build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        // Assumption: for each getter will always exist a setter and viceversa.
        if (method.getName().startsWith("get")) {
            // get method annotation and check if it exists as expected

            final Method getter = entityType.getMethod(method.getName());

            final Property property = getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navigationProperty = getAnnotation(NavigationProperty.class, getter);
                if (navigationProperty == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... navigate and follow link if necessary
                    return getNavigationPropertyValue(navigationProperty, getter);
                }
            } else {
                // if the getter refers to a property .... get property from wrapped entity
                return getPropertyValue(property, getter.getGenericReturnType());
            }

        } else if (method.getName().startsWith("set")) {
            // get the corresponding getter method (see assumption above)
            final String getterName = method.getName().replaceFirst("set", "get");
            final Method getter = entityType.getMethod(getterName);

            final Property property = getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navigationProperty = getAnnotation(NavigationProperty.class, getter);
                if (navigationProperty == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... change the context
                    // TODO: manage object into the context
                }
            } else {
                // if the getter exists and it is annotated as expected then get name/value and add a new property
                entity.addProperty(getODataProperty(args[0], property));
            }

            final Constructor<Void> cv = Void.class.getDeclaredConstructor();
            cv.setAccessible(true);
            return cv.newInstance();
        } else {
            throw new UnsupportedOperationException("Unsupported method " + method.getName());
        }
    }

    private Object getNavigationPropertyValue(final NavigationProperty property, final Method getter) {
        Object res = null;

        Type type = getter.getGenericReturnType();

        for (ODataLink link : entity.getNavigationLinks()) {
            if (link.getName().equals(property.name())) {
                if (link instanceof ODataInlineEntity) {
                    // return entity
                    res = getEntity(((ODataInlineEntity) link).getEntity(), type, entitySetName);

                } else if (link instanceof ODataInlineEntitySet) {
                    // return entity set
                    res = getEntities(((ODataInlineEntitySet) link).getEntitySet(), ((ParameterizedType) type));
                } else {
                    // navigate
                    final URI targetURI = URIUtils.getURI(container.getServiceRoot(), link.getLink().toASCIIString());

                    if (getter.getReturnType().isAssignableFrom(Collection.class)) {
                        res = getEntities(
                                ODataRetrieveRequestFactory.getEntitySetRequest(targetURI).execute().getBody(),
                                ((ParameterizedType) type));
                    } else {
                        res = getEntity(
                                ODataRetrieveRequestFactory.getEntityRequest(targetURI).execute().getBody(),
                                type,
                                entitySetName);
                    }
                }
            }
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    private Object getPropertyValue(final Property property, final Type type) {
        final Object res;

        try {
            final ODataProperty oproperty = entity.getProperty(property.name());
            if (oproperty == null || oproperty.hasNullValue()) {
                res = null;
            } else if (oproperty.hasCollectionValue()) {
                res = new ArrayList();

                final ParameterizedType collType = (ParameterizedType) type;
                final Class<?> collItemClass = (Class<?>) collType.getActualTypeArguments()[0];

                final Iterator<ODataValue> collPropItor = oproperty.getCollectionValue().iterator();
                while (collPropItor.hasNext()) {
                    final ODataValue value = collPropItor.next();
                    if (value.isPrimitive()) {
                        ((Collection) res).add(value.asPrimitive().toValue());
                    }
                    if (value.isComplex()) {
                        final Object collItem = collItemClass.newInstance();
                        populate(collItem, value.asComplex().iterator());
                        ((Collection) res).add(collItem);
                    }
                }
            } else if (oproperty.hasPrimitiveValue()) {
                res = oproperty.getPrimitiveValue().toValue();
            } else if (oproperty.hasComplexValue()) {
                res = ((Class<?>) type).newInstance();
                populate(res, oproperty.getComplexValue().iterator());
            } else {
                throw new IllegalArgumentException("Invalid property " + oproperty);
            }

            return res;
        } catch (Exception e) {
            LOG.error("Error getting value for property '" + property.name() + "'");
            throw new IllegalArgumentException(e);
        }
    }
}
