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

import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet.End;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.KeyRef;
import com.msopentech.odatajclient.proxy.api.annotations.Namespace;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.AbstractMap;
import java.util.HashSet;
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

    public static EntityContainer getContainerFromURI(final URI uri, final EdmMetadata metadata) {
        // Assumptions: 
        //      1. the name of a container cannot collide among several schemas
        //      2. schema name is never provided in the URI

        if (uri == null) {
            throw new IllegalArgumentException("Invalid URI " + uri);
        }

        final String ascii = uri.toASCIIString();
        final String lastPart = ascii.substring(ascii.lastIndexOf('/') + 1);

        EntityContainer defaultEntityContainer = null;
        EntityContainer res = null;

        int firstDotIdx = lastPart.indexOf('.');
        final String containerName = lastPart.substring(0, firstDotIdx > 0 ? firstDotIdx : 0);

        for (Schema schema : metadata.getSchemas()) {
            for (EntityContainer ec : schema.getEntityContainers()) {
                if (ec.isDefaultEntityContainer()) {
                    defaultEntityContainer = ec;
                }
                if (ec.getName().equals(containerName)) {
                    res = ec;
                }
            }
        }

        return res == null ? defaultEntityContainer : res;
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

    public static Class<?> getKeyRef(final Class<?> entityTypeRef) {
        if (entityTypeRef.getAnnotation(EntityType.class) == null) {
            throw new IllegalArgumentException("Invalid annotation for entity type " + entityTypeRef.getSimpleName());
        }
        final Annotation ann = entityTypeRef.getAnnotation(KeyRef.class);

        final Class<?> res;

        if (ann == null) {
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
        } else {
            res = ((KeyRef) ann).value();
        }

        return res;
    }

    public static String getSchemaName(final Class<?> ref) {
        final Annotation annotation = ref.getPackage().getAnnotation(Namespace.class);
        if (!(annotation instanceof Namespace)) {
            throw new IllegalArgumentException(ref.getPackage().getName()
                    + " is not annotated as @" + Namespace.class.getSimpleName());
        }
        return ((Namespace) annotation).value();
    }
}
