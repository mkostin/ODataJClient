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

import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.annotations.ComplexType;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractInvocationHandler implements InvocationHandler {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractInvocationHandler.class);

    private static final long serialVersionUID = 358520026931462958L;

    private final EntityContainerFactory factory;

    public AbstractInvocationHandler(final EntityContainerFactory factory) {
        this.factory = factory;
    }

    public EntityContainerFactory getFactory() {
        return factory;
    }

    protected Method findGetterByAnnotatedName(final Class<?> clazz, final String name) {
        final Method[] methods = clazz.getMethods();

        Method result = null;
        for (int i = 0; i < methods.length && result == null; i++) {
            final Annotation annotation = methods[i].getAnnotation(Property.class);
            if ((annotation instanceof Property)
                    && methods[i].getName().startsWith("get") // Assumption: getter is always prefixed by 'get' word 
                    && name.equals(((Property) annotation).name())) {
                result = methods[i];
            }
        }

        return result;
    }

    protected ODataValue getODataValue(final Object obj, final EdmType type) {
        final ODataValue value;

        if (obj == null) {
            value = null;
        } else if (type.isCollection()) {
            value = new ODataCollectionValue(type.getTypeExpression());
            final EdmType intType = new EdmType(factory.getMetadata(), type.getBaseType());
            for (Object collectionItem : (Collection) obj) {
                if (intType.isSimpleType()) {
                    ((ODataCollectionValue) value).add((ODataPrimitiveValue) getODataValue(collectionItem, intType));
                } else if (intType.isComplexType()) {
                    ((ODataCollectionValue) value).add((ODataComplexValue) getODataValue(collectionItem, intType));
                } else if (intType.isEnumType()) {
                    // TODO: manage enum types
                    throw new UnsupportedOperationException("Usupported enum type " + intType.getTypeExpression());
                } else {
                    throw new UnsupportedOperationException("Usupported object type " + intType.getTypeExpression());
                }
            }
        } else if (type.isComplexType()) {
            value = new ODataComplexValue(type.getBaseType());
            if (obj.getClass().getAnnotation(ComplexType.class) != null) {
                for (Method method : obj.getClass().getMethods()) {
                    final Property complexPropertyAnn = method.getAnnotation(Property.class);
                    try {
                        if (complexPropertyAnn != null) {
                            ((ODataComplexValue) value).add(getODataProperty(method.invoke(obj), complexPropertyAnn));
                        }
                    } catch (Exception ignore) {
                        // ignore name
                        LOG.warn("Error attaching complex field '{}'", complexPropertyAnn.name(), ignore);
                    }
                }
            } else {
                throw new IllegalArgumentException(
                        "Object '" + obj.getClass().getSimpleName() + "' is not a complex value");
            }
        } else if (type.isEnumType()) {
            // TODO: manage enum types
            throw new UnsupportedOperationException("Usupported enum type " + type.getTypeExpression());
        } else {
            value = new ODataPrimitiveValue.Builder().setValue(obj).
                    setType(EdmSimpleType.fromValue(type.getTypeExpression())).build();
        }

        return value;
    }

    protected ODataProperty getODataProperty(final Object obj, final Property prop) {

        final ODataProperty oprop;

        final EdmType type = new EdmType(factory.getMetadata(), prop.type());
        try {
            if (type.isCollection()) {
                // create collection property
                oprop = ODataFactory.newCollectionProperty(
                        prop.name(),
                        (ODataCollectionValue) getODataValue(obj, type));
            } else if (type.isSimpleType()) {
                // create a primitive property
                oprop = ODataFactory.newPrimitiveProperty(
                        prop.name(),
                        (ODataPrimitiveValue) getODataValue(obj, type));
            } else if (type.isComplexType()) {
                // create a complex property
                oprop = ODataFactory.newComplexProperty(
                        prop.name(),
                        (ODataComplexValue) getODataValue(obj, type));
            } else if (type.isEnumType()) {
                // TODO: manage enum types
                throw new UnsupportedOperationException("Usupported enum type " + type.getTypeExpression());
            } else {
                throw new UnsupportedOperationException("Usupported object type " + type.getTypeExpression());
            }

            return oprop;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected void addProperties(final Map<Property, Object> changes, final ODataEntity entity) {

        for (Map.Entry<Property, Object> property : changes.entrySet()) {
            // if the getter exists and it is annotated as expected then get name/value and add a new property
            final ODataProperty odataProperty = entity.getProperty(property.getKey().name());
            if (odataProperty != null) {
                entity.removeProperty(odataProperty);
            }

            entity.addProperty(getODataProperty(property.getValue(), property.getKey()));
        }
    }

    @SuppressWarnings("unchecked")
    protected void populate(final Object bean, final Iterator<ODataProperty> propItor) {
        while (propItor.hasNext()) {
            final ODataProperty property = propItor.next();

            final Method getter = findGetterByAnnotatedName(bean.getClass(), property.getName());
            if (getter == null) {
                LOG.warn("Could not find any property annotated as {} in {}",
                        property.getName(), bean.getClass().getName());
            } else {
                try {
                    if (property.hasNullValue()) {
                        setPropertyValue(bean, getter, null);
                    }
                    if (property.hasPrimitiveValue()) {
                        setPropertyValue(bean, getter, property.getPrimitiveValue().toValue());
                    }
                    if (property.hasComplexValue()) {
                        final Object complex = getter.getReturnType().newInstance();
                        populate(complex, property.getComplexValue().iterator());
                        setPropertyValue(bean, getter, complex);
                    }
                    if (property.hasCollectionValue()) {
                        final ParameterizedType collType = (ParameterizedType) getter.getGenericReturnType();
                        final Class<?> collItemClass = (Class<?>) collType.getActualTypeArguments()[0];

                        Collection collection = (Collection) getter.invoke(bean);
                        if (collection == null) {
                            collection = new ArrayList();
                            setPropertyValue(bean, getter, collection);
                        }

                        final Iterator<ODataValue> collPropItor = property.getCollectionValue().iterator();
                        while (collPropItor.hasNext()) {
                            final ODataValue value = collPropItor.next();
                            if (value.isPrimitive()) {
                                collection.add(value.asPrimitive().toValue());
                            }
                            if (value.isComplex()) {
                                final Object collItem = collItemClass.newInstance();
                                populate(collItem, value.asComplex().iterator());
                                collection.add(collItem);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Could not set property {} on {}", getter, bean, e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void setPropertyValue(final Object bean, final Method getter, final Object value)
            throws Exception {

        // Assumprion: setter is always prefixed by 'set' word
        final String setterName = getter.getName().replaceFirst("get", "set");
        bean.getClass().getMethod(setterName, getter.getReturnType()).invoke(bean, value);
    }

    protected static class CompoundKeyElementWrapper implements Comparable<CompoundKeyElementWrapper> {

        private final String name;

        private final Method method;

        private final int position;

        protected CompoundKeyElementWrapper(final String name, final Method method, final int position) {
            this.name = name;
            this.method = method;
            this.position = position;
        }

        public String getName() {
            return name;
        }

        public Method getMethod() {
            return method;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public int compareTo(final CompoundKeyElementWrapper other) {
            if (other == null) {
                return 1;
            } else {
                return getPosition() > other.getPosition() ? 1 : getPosition() == other.getPosition() ? 0 : -1;
            }
        }
    }

    protected LinkedHashMap<String, Object> getCompoundKey(final Object key) {
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

    @SuppressWarnings("unchecked")
    protected <ANN extends Annotation> ANN getAnnotation(final Class<ANN> reference, final AccessibleObject obj) {
        final Annotation ann = obj.getAnnotation(reference);
        return ann == null ? null : (ANN) ann;
    }

    @SuppressWarnings("unchecked")
    protected <T> Collection<T> getEntityProxies(
            final ParameterizedType type, final String entityContainerName, final ODataEntitySet entitySet) {
        return getEntityProxies(type, entityContainerName, entitySet, false);
    }

    @SuppressWarnings("unchecked")
    protected <T> Collection<T> getEntityProxies(
            final ParameterizedType type,
            final String entityContainerName,
            final ODataEntitySet entitySet,
            final boolean checkInTheContext) {

        final Collection<T> res = new HashSet<T>();
        final Class<?> enType = (Class<?>) type.getActualTypeArguments()[0];

        for (ODataEntity entity : entitySet.getEntities()) {
            res.add((T) getEntityProxy(entity, entityContainerName, entitySet.getName(), enType, checkInTheContext));
        }

        return Collections.unmodifiableCollection(res);
    }

    @SuppressWarnings("unchecked")
    protected <T> Collection<T> getEntityProxies(final Collection<EntityTypeInvocationHandler> handlers) {
        final Collection<T> res = new HashSet<T>();

        if (handlers != null) {
            for (EntityTypeInvocationHandler handler : handlers) {
                res.add((T) Proxy.newProxyInstance(
                        handler.getClass().getClassLoader(),
                        new Class<?>[] {(Class<?>) handler.getTypeRef()},
                        handler));
            }
        }

        return Collections.unmodifiableCollection(res);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getEntityProxy(final Collection<EntityTypeInvocationHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return null;
        } else {
            final EntityTypeInvocationHandler handler = handlers.iterator().next();
            return (T) Proxy.newProxyInstance(
                    handler.getClass().getClassLoader(),
                    new Class<?>[] {(Class<?>) handler.getTypeRef()},
                    handler);
        }
    }

    protected <T> T getEntityProxy(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Type type) {

        return getEntityProxy(entity, entityContainerName, entitySetName, type, false);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getEntityProxy(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Type type,
            final boolean checkInTheContext) {

        EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(
                entity, entityContainerName, entitySetName, (Class<?>) type, getFactory());

        if (checkInTheContext) {
            if (EntityContainerFactory.getContext().entityContext().isAttached(handler)) {
                handler = EntityContainerFactory.getContext().entityContext().getEntity(handler.getUUID());
            }
        }
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class<?>[] {(Class<?>) type},
                handler);
    }
}
