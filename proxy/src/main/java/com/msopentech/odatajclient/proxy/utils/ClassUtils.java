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

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyRef;
import com.msopentech.odatajclient.proxy.api.annotations.Namespace;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassUtils {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

    private ClassUtils() {
        // Empty private constructor for static utility classes
    }

    public static Method findGetterByAnnotatedName(final Class<?> clazz, final String name) {
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

    @SuppressWarnings("unchecked")
    public static <ANN extends Annotation> ANN getAnnotation(final Class<ANN> reference, final AccessibleObject obj) {
        final Annotation ann = obj.getAnnotation(reference);
        return ann == null ? null : (ANN) ann;
    }

    private static String firstValidEntityKey(final Class<?> entityTypeRef) {
        for (Method method : entityTypeRef.getDeclaredMethods()) {
            if (method.getAnnotation(Key.class) != null) {
                final Annotation ann = method.getAnnotation(Property.class);
                if (ann != null) {
                    return ((Property) ann).name();
                }
            }
        }
        return null;
    }

    public static Class<?> getCompoundKeyRef(final Class<?> entityTypeRef) {
        if (entityTypeRef.getAnnotation(EntityType.class) == null) {
            throw new IllegalArgumentException("Invalid annotation for entity type " + entityTypeRef);
        }

        final Annotation ann = entityTypeRef.getAnnotation(KeyRef.class);

        return ann == null || ((KeyRef) ann).value().getAnnotation(CompoundKey.class) == null
                ? null
                : ((KeyRef) ann).value();
    }

    private static <T> T populateCompoundKey(final Class<T> compoundKeyRef, final ODataEntity entity) {
        try {
            final T res = compoundKeyRef.newInstance();

            for (Method method : compoundKeyRef.getClass().getDeclaredMethods()) {
                final Annotation ann = method.getAnnotation(CompoundKeyElement.class);
                if (ann != null) {
                    try {
                        compoundKeyRef.getClass().getDeclaredMethod(
                                method.getName().replaceFirst("get", "set"), method.getReturnType()).invoke(
                                res,
                                entity.getProperty(((CompoundKeyElement) ann).name()));
                    } catch (Exception e) {
                        LOG.error("Error populating key element {}", ((CompoundKeyElement) ann).name(), e);
                    }
                }
            }
            return res;
        } catch (Exception e) {
            LOG.error("Error population compound key {}", compoundKeyRef.getSimpleName());
            throw new IllegalArgumentException("Cannot populate compound key");
        }
    }

    public static Object getKey(final Class<?> entityTypeRef, final ODataEntity entity) {
        final Object res;

        if (entity.getProperties().isEmpty()) {
            res = null;
        } else {
            final Class<?> keyRef = getCompoundKeyRef(entityTypeRef);
            if (keyRef == null) {
                final ODataProperty property = entity.getProperty(firstValidEntityKey(entityTypeRef));
                res = property == null || !property.hasPrimitiveValue()
                        ? null
                        : property.getPrimitiveValue().toValue();

            } else {
                res = populateCompoundKey(keyRef, entity);
            }
        }

        return res;
    }

    public static Class<?> getKeyRef(final Class<?> entityTypeRef) {
        Class<?> res = getCompoundKeyRef(entityTypeRef);

        if (res == null) {
            final Set<Method> keyGetters = new HashSet<Method>();

            for (Method method : entityTypeRef.getDeclaredMethods()) {
                if (method.getName().startsWith("get") && method.getAnnotation(Key.class) != null) {
                    keyGetters.add(method);
                }
            }

            if (keyGetters.size() == 1) {
                res = keyGetters.iterator().next().getReturnType();
            } else {
                throw new IllegalStateException(entityTypeRef.getSimpleName() + "'s key reference not found");
            }
        }

        return res;
    }

    public static String getEntityTypeName(final Class<?> ref) {
        final Annotation annotation = ref.getAnnotation(EntityType.class);
        if (!(annotation instanceof EntityType)) {
            throw new IllegalArgumentException(ref.getPackage().getName()
                    + " is not annotated as @" + EntityType.class.getSimpleName());
        }
        return ((EntityType) annotation).name();
    }

    public static String getNamespace(final Class<?> ref) {
        final Annotation annotation = ref.getPackage().getAnnotation(Namespace.class);
        if (!(annotation instanceof Namespace)) {
            throw new IllegalArgumentException(ref.getPackage().getName()
                    + " is not annotated as @" + Namespace.class.getSimpleName());
        }
        return ((Namespace) annotation).value();
    }
}
