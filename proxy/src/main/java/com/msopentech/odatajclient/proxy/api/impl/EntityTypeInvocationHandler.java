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
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.proxy.api.AbstractEntityCollection;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.context.EntityContext;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.context.EntityUUID;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import com.msopentech.odatajclient.proxy.utils.EngineUtils;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class EntityTypeInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = 2629912294765040037L;

    private ODataEntity entity;

    private Map<Property, Object> propertyChanges = new HashMap<Property, Object>();

    private Map<NavigationProperty, Object> linkChanges = new HashMap<NavigationProperty, Object>();

    private final Class<?> typeRef;

    private EntityUUID uuid;

    private final EntityContext entityContext = EntityContainerFactory.getContext().entityContext();

    private int propertiesTag;

    private int linksTag;

    static EntityTypeInvocationHandler getInstance(
            final ODataEntity entity,
            final EntitySetInvocationHandler entitySet) {

        return getInstance(
                entity,
                entitySet.getContainer().getEntityContainerName(),
                entitySet.getEntitySetName(),
                entitySet.getTypeRef(),
                entitySet.getContainer().factory);
    }

    static EntityTypeInvocationHandler getInstance(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Class<?> typeRef,
            final EntityContainerFactory factory) {

        return new EntityTypeInvocationHandler(entity, entityContainerName, entitySetName, typeRef, factory);
    }

    private EntityTypeInvocationHandler(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Class<?> typeRef,
            final EntityContainerFactory factory) {

        super(factory);
        this.entity = entity;
        this.typeRef = typeRef;

        this.uuid = new EntityUUID(
                ClassUtils.getNamespace(typeRef),
                entityContainerName,
                entitySetName,
                entity.getName(),
                EngineUtils.getKey(factory.getMetadata(), typeRef, entity));

        this.propertiesTag = 0;
        this.linksTag = 0;
    }

    public void setEntity(final ODataEntity entity) {
        this.entity = entity;

        this.uuid = new EntityUUID(
                getUUID().getSchemaName(),
                getUUID().getContainerName(),
                getUUID().getEntitySetName(),
                getUUID().getName(),
                EngineUtils.getKey(factory.getMetadata(), typeRef, entity));

        this.propertyChanges.clear();
        this.linkChanges.clear();
        this.propertiesTag = 0;
        this.linksTag = 0;
    }

    public EntityUUID getUUID() {
        return uuid;
    }

    public String getName() {
        return this.entity.getName();
    }

    public String getEntityContainerName() {
        return uuid.getContainerName();
    }

    public String getEntitySetName() {
        return uuid.getEntitySetName();
    }

    public Class<?> getTypeRef() {
        return typeRef;
    }

    public ODataEntity getEntity() {
        return entity;
    }

    public Map<Property, Object> getPropertyChanges() {
        return propertyChanges;
    }

    public Map<NavigationProperty, Object> getLinkChanges() {
        return linkChanges;
    }

    /**
     * Gets the current ETag defined into the wrapped entity.
     *
     * @return
     */
    public String getETag() {
        return this.entity.getETag();
    }

    /**
     * Overrides ETag value defined into the wrapped entity.
     *
     * @param eTag ETag.
     */
    public void setETag(final String eTag) {
        this.entity.setETag(eTag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // Assumption: for each getter will always exist a setter and viceversa.
        if (isSelfMethod(method, args)) {
            return invokeSelfMethod(method, args);
        } else if (method.getName().startsWith("get")) {
            // get method annotation and check if it exists as expected
            final Object res;

            final Method getter = typeRef.getMethod(method.getName());

            final Property property = ClassUtils.getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navProp = ClassUtils.getAnnotation(NavigationProperty.class, getter);
                if (navProp == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... navigate and follow link if necessary
                    res = getNavigationPropertyValue(navProp, getter);
                }
            } else {
                // if the getter refers to a property .... get property from wrapped entity
                res = getPropertyValue(property, getter.getGenericReturnType());
            }

            // attach the current handler
            if (!entityContext.isAttached(this)) {
                entityContext.attach(this, AttachedEntityStatus.ATTACHED);
            }

            return res;
        } else if (method.getName().startsWith("set")) {
            // get the corresponding getter method (see assumption above)
            final String getterName = method.getName().replaceFirst("set", "get");
            final Method getter = typeRef.getMethod(getterName);

            final Property property = ClassUtils.getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navProp = ClassUtils.getAnnotation(NavigationProperty.class, getter);
                if (navProp == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... 
                    if (ArrayUtils.isEmpty(args) || args.length != 1) {
                        throw new IllegalArgumentException("Invalid argument");
                    }

                    setNavigationPropertyValue(navProp, args[0]);
                }
            } else {
                setPropertyValue(property, args[0]);
            }

            final Constructor<Void> voidConstructor = Void.class.getDeclaredConstructor();
            voidConstructor.setAccessible(true);
            return voidConstructor.newInstance();
        } else {
            throw new UnsupportedOperationException("Unsupported method " + method.getName());
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Serializable, EC extends AbstractEntityCollection<T>> EC getEntityCollection(
            final Class<?> typeRef,
            final Class<?> typeCollectionRef,
            final String entityContainerName,
            final ODataEntitySet entitySet,
            final boolean checkInTheContext) {

        final List<T> items = new ArrayList<T>();

        for (ODataEntity entityFromSet : entitySet.getEntities()) {
            items.add((T) getEntityProxy(
                    entityFromSet, entityContainerName, entitySet.getName(), typeRef, checkInTheContext));
        }

        return (EC) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {typeCollectionRef},
                new EntityCollectionInvocationHandler<T>(items, factory));
    }

    private <T> T getEntityProxy(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Class<?> type,
            final boolean checkInTheContext) {
        return getEntityProxy(entity, entityContainerName, entitySetName, type, null, checkInTheContext);
    }

    @SuppressWarnings("unchecked")
    private <T> T getEntityProxy(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Class<?> type,
            final String eTag,
            final boolean checkInTheContext) {

        EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(
                entity, entityContainerName, entitySetName, type, factory);

        if (StringUtils.isNotBlank(eTag)) {
            // override ETag into the wrapped object.
            handler.setETag(eTag);
        }

        if (checkInTheContext && EntityContainerFactory.getContext().entityContext().isAttached(handler)) {
            handler = EntityContainerFactory.getContext().entityContext().getEntity(handler.getUUID());
        }

        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {type},
                handler);
    }

    private Object getNavigationPropertyValue(final NavigationProperty property, final Method getter) {
        final Class<?> type = getter.getReturnType();
        final Class<?> collItemType;
        if (AbstractEntityCollection.class.isAssignableFrom(type)) {
            final Type[] entityCollectionParams =
                    ((ParameterizedType) type.getGenericInterfaces()[0]).getActualTypeArguments();
            collItemType = (Class<?>) entityCollectionParams[0];
        } else {
            collItemType = type;
        }

        final EdmMetadata metadata = factory.getMetadata();
        final Schema schema = metadata.getSchema(ClassUtils.getNamespace(typeRef));

        // 1) get association
        final Association association = EngineUtils.getAssociation(schema, property.relationship());

        // 2) get entity container and association set
        final Map.Entry<EntityContainer, AssociationSet> associationSet =
                EngineUtils.getAssociationSet(association, schema.getNamespace(), metadata);

        // 3) get entitySet
        final String targetEntitySetName = EngineUtils.getEntitySetName(associationSet.getValue(), property.toRole());

        final Object navPropValue;

        if (linkChanges.containsKey(property)) {
            navPropValue = linkChanges.get(property);
        } else {
            final ODataLink link = EngineUtils.getNavigationLink(property.name(), entity);
            if (link instanceof ODataInlineEntity) {
                // return entity
                navPropValue = getEntityProxy(
                        ((ODataInlineEntity) link).getEntity(),
                        associationSet.getKey().getName(),
                        targetEntitySetName,
                        type,
                        false);
            } else if (link instanceof ODataInlineEntitySet) {
                // return entity set
                navPropValue = getEntityCollection(
                        collItemType,
                        type,
                        associationSet.getKey().getName(),
                        ((ODataInlineEntitySet) link).getEntitySet(),
                        false);
            } else {
                // navigate
                final URI uri = URIUtils.getURI(factory.getServiceRoot(), link.getLink().toASCIIString());

                if (AbstractEntityCollection.class.isAssignableFrom(type)) {
                    navPropValue = getEntityCollection(
                            collItemType,
                            type,
                            associationSet.getKey().getName(),
                            ODataRetrieveRequestFactory.getEntitySetRequest(uri).execute().getBody(),
                            true);
                } else {
                    final ODataRetrieveResponse<ODataEntity> res =
                            ODataRetrieveRequestFactory.getEntityRequest(uri).execute();

                    navPropValue = getEntityProxy(
                            res.getBody(),
                            associationSet.getKey().getName(),
                            targetEntitySetName,
                            type,
                            res.getEtag(),
                            true);
                }
            }

            if (navPropValue != null) {
                int checkpoint = linkChanges.hashCode();
                linkChanges.put(property, navPropValue);
                updateLinksTag(checkpoint);
            }
        }

        return navPropValue;
    }

    private Object getPropertyValue(final Property property, final Type type) {
        try {
            final Object res;

            if (propertyChanges.containsKey(property)) {
                res = propertyChanges.get(property);
            } else {
                res = EngineUtils.getValueFromProperty(
                        factory.getMetadata(), entity.getProperty(property.name()), type);

                if (res != null) {
                    int checkpoint = propertyChanges.hashCode();
                    propertyChanges.put(property, res);
                    updatePropertiesTag(checkpoint);
                }
            }

            return res;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error getting value for property '" + property.name() + "'", e);
        }
    }

    private void setNavigationPropertyValue(final NavigationProperty property, final Object value) {
        // 1) attach source entity
        if (!entityContext.isAttached(this)) {
            entityContext.attach(this, AttachedEntityStatus.CHANGED);
        }

        // 2) attach the target entity handlers
        for (Object link : AbstractEntityCollection.class.isAssignableFrom(value.getClass())
                ? (AbstractEntityCollection) value : Collections.singleton(value)) {

            final InvocationHandler etih = Proxy.getInvocationHandler(link);
            if (!(etih instanceof EntityTypeInvocationHandler)) {
                throw new IllegalArgumentException("Invalid argument type");
            }

            final EntityTypeInvocationHandler handler = (EntityTypeInvocationHandler) etih;
            if (!handler.getTypeRef().isAnnotationPresent(EntityType.class)) {
                throw new IllegalArgumentException(
                        "Invalid argument type " + handler.getTypeRef().getSimpleName());
            }

            if (!entityContext.isAttached(handler)) {
                entityContext.attach(handler, AttachedEntityStatus.LINKED);
            }
        }

        // 3) add links
        linkChanges.put(property, value);
    }

    private void setPropertyValue(final Property property, final Object value) {
        propertyChanges.put(property, value);

        if (entityContext.isAttached(this)) {
            entityContext.setStatus(this, AttachedEntityStatus.CHANGED);
        } else {
            entityContext.attach(this, AttachedEntityStatus.CHANGED);
        }
    }

    private void updatePropertiesTag(final int checkpoint) {
        if (checkpoint == propertiesTag) {
            propertiesTag = propertyChanges.hashCode();
        }
    }

    private void updateLinksTag(final int checkpoint) {
        if (checkpoint == linksTag) {
            linksTag = linkChanges.hashCode();
        }
    }

    public boolean isChanged() {
        return linkChanges.hashCode() != linksTag || propertyChanges.hashCode() != propertiesTag;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof EntityTypeInvocationHandler
                && ((EntityTypeInvocationHandler) obj).getUUID().equals(uuid);
    }
}
