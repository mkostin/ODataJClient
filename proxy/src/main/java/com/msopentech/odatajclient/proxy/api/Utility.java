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

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet.End;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyRef;
import com.msopentech.odatajclient.proxy.api.annotations.Namespace;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

    public static Map.Entry<EntityContainer, AssociationSet> getAssociationSet(
            final Association association, final String associationNamespace, final EdmMetadata metadata) {

        final StringBuilder associationName = new StringBuilder();
        associationName.append(associationNamespace).append('.').append(association.getName());

        for (Schema schema : metadata.getSchemas()) {
            for (EntityContainer container : schema.getEntityContainers()) {
                AssociationSet associationSet = getAssociationSet(associationName.toString(), container);
                if (associationSet != null) {
                    return new AbstractMap.SimpleEntry<EntityContainer, AssociationSet>(container, associationSet);
                }
            }
        }

        throw new IllegalStateException("Association set not found");
    }

    public static Association getAssociation(final Schema schema, final String relationship) {
        return schema.getAssociation(relationship.substring(relationship.lastIndexOf('.') + 1));
    }

    public static AssociationSet getAssociationSet(
            final String association,
            final EntityContainer container) {
        LOG.debug("Search for association set {}", association);

        for (AssociationSet associationSet : container.getAssociationSets()) {
            LOG.debug("Retrieved association set '{}:{}'", associationSet.getName(), associationSet.getAssociation());
            if (associationSet.getAssociation().equals(association)) {
                return associationSet;
            }
        }

        return null;
    }

    public static String getEntitySetName(final AssociationSet associationSet, final String role) {
        for (End end : associationSet.getEnd()) {
            if (end.getRole().equals(role)) {
                return end.getEntitySet();
            }
        }
        return null;
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
            T res = compoundKeyRef.newInstance();

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
                res = entity.getProperty(firstValidEntityKey(entityTypeRef));
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

            if (keyGetters.size() != 1) {
                throw new IllegalStateException(entityTypeRef.getSimpleName() + "'s key reference not found");
            } else {
                res = keyGetters.iterator().next().getReturnType();
            }
        }

        return res;
    }

    public static String getNamespace(final Class<?> ref) {
        final Annotation annotation = ref.getPackage().getAnnotation(Namespace.class);
        if (!(annotation instanceof Namespace)) {
            throw new IllegalArgumentException(ref.getPackage().getName()
                    + " is not annotated as @" + Namespace.class.getSimpleName());
        }
        return ((Namespace) annotation).value();
    }

    public static NavigationProperty getNavigationProperty(final Class<?> entityTypeRef, final String relationship) {
        NavigationProperty res = null;
        final Method[] methods = entityTypeRef.getClass().getDeclaredMethods();

        for (int i = 0; i < methods.length && res == null; i++) {
            final Annotation ann = methods[i].getAnnotation(NavigationProperty.class);
            if ((ann instanceof NavigationProperty)
                    && ((NavigationProperty) ann).relationship().equalsIgnoreCase(relationship)) {
                res = (NavigationProperty) ann;
            }
        }

        return res;
    }

    public static ODataLink getNavigationLink(final String name, final ODataEntity entity) {
        ODataLink res = null;
        final List<ODataLink> links = entity.getNavigationLinks();

        for (int i = 0; i < links.size() && res == null; i++) {
            if (links.get(i).getName().equalsIgnoreCase(name)) {
                res = links.get(i);
            }
        }
        return res;
    }
}
