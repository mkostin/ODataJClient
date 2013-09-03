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
package com.msopentech.odatajclient.proxy.utils;

import com.msopentech.odatajclient.engine.data.ODataCollectionValue;
import com.msopentech.odatajclient.engine.data.ODataComplexValue;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.proxy.api.annotations.ComplexType;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ODataItemUtils {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MetadataUtils.class);

    private ODataItemUtils() {
        // Empty private constructor for static utility classes
    }

    private static ODataValue getODataValue(final EdmMetadata metadata, final Object obj, final EdmType type) {
        final ODataValue value;

        if (type.isCollection()) {
            value = new ODataCollectionValue(type.getTypeExpression());
            final EdmType intType = new EdmType(metadata, type.getBaseType());
            for (Object collectionItem : (Collection) obj) {
                if (intType.isSimpleType()) {
                    ((ODataCollectionValue) value).add(getODataValue(metadata, collectionItem, intType).asPrimitive());
                } else if (intType.isComplexType()) {
                    ((ODataCollectionValue) value).add(getODataValue(metadata, collectionItem, intType).asComplex());
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
                            value.asComplex().add(
                                    getODataProperty(metadata, method.invoke(obj), complexPropertyAnn));
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

    private static ODataProperty getODataProperty(final EdmMetadata metadata, final Object obj, final Property prop) {
        final ODataProperty oprop;

        final EdmType type = new EdmType(metadata, prop.type());
        try {
            if (type.isCollection()) {
                // create collection property
                oprop = ODataFactory.newCollectionProperty(
                        prop.name(),
                        obj == null ? null : getODataValue(metadata, obj, type).asCollection());
            } else if (type.isSimpleType()) {
                // create a primitive property
                oprop = ODataFactory.newPrimitiveProperty(
                        prop.name(),
                        obj == null ? null : getODataValue(metadata, obj, type).asPrimitive());
            } else if (type.isComplexType()) {
                // create a complex property
                oprop = ODataFactory.newComplexProperty(
                        prop.name(),
                        obj == null ? null : getODataValue(metadata, obj, type).asComplex());
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

    public static void addProperties(
            final EdmMetadata metadata, final Map<Property, Object> changes, final ODataEntity entity) {

        for (Map.Entry<Property, Object> property : changes.entrySet()) {
            // if the getter exists and it is annotated as expected then get name/value and add a new property
            final ODataProperty odataProperty = entity.getProperty(property.getKey().name());
            if (odataProperty != null) {
                entity.removeProperty(odataProperty);
            }

            entity.addProperty(getODataProperty(metadata, property.getValue(), property.getKey()));
        }
    }

    @SuppressWarnings("unchecked")
    private static void setPropertyValue(final Object bean, final Method getter, final Object value)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // Assumption: setter is always prefixed by 'set' word
        final String setterName = getter.getName().replaceFirst("get", "set");
        bean.getClass().getMethod(setterName, getter.getReturnType()).invoke(bean, value);
    }

    @SuppressWarnings("unchecked")
    public static void populate(final EdmMetadata metadata, final Object bean, final Iterator<ODataProperty> propItor) {
        while (propItor.hasNext()) {
            final ODataProperty property = propItor.next();

            final Method getter = ClassUtils.findGetterByAnnotatedName(bean.getClass(), property.getName());
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
                        populate(metadata, complex, property.getComplexValue().iterator());
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
                                populate(metadata, collItem, value.asComplex().iterator());
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
}
