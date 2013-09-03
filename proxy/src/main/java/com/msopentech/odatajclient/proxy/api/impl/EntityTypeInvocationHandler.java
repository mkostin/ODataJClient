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

import com.msopentech.odatajclient.proxy.utils.MetadataUtils;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
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
import com.msopentech.odatajclient.proxy.api.context.EntityLinkDesc;
import com.msopentech.odatajclient.proxy.api.context.EntityUUID;
import com.msopentech.odatajclient.proxy.api.context.LinkContext;
import com.msopentech.odatajclient.proxy.utils.ClassUtils;
import com.msopentech.odatajclient.proxy.utils.ODataItemUtils;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityTypeInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = 2629912294765040037L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntityTypeInvocationHandler.class);

    private ODataEntity entity;

    private Map<Property, Object> changes = new HashMap<Property, Object>();

    private final Class<?> typeRef;

    private EntityUUID uuid;

    private final EntityContext entityContext = EntityContainerFactory.getContext().entityContext();

    private final LinkContext linkContext = EntityContainerFactory.getContext().linkContext();

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
                MetadataUtils.getNamespace(typeRef),
                entityContainerName,
                entitySetName,
                entity.getName(),
                MetadataUtils.getKey(typeRef, entity));
    }

    public void setEntity(final ODataEntity entity) {
        this.entity = entity;

        this.uuid = new EntityUUID(
                getUUID().getSchemaName(),
                getUUID().getContainerName(),
                getUUID().getEntitySetName(),
                getUUID().getName(),
                MetadataUtils.getKey(typeRef, entity));
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

    public Map<Property, Object> getChanges() {
        return changes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // Assumption: for each getter will always exist a setter and viceversa.
        if ("equals".equals(method.getName()) && !(ArrayUtils.isEmpty(args)) && args.length == 1) {
            return this.equals(args[0]);
        } else if ("hashCode".equals(method.getName()) && (ArrayUtils.isEmpty(args))) {
            return this.hashCode();
        } else if ("toString".equals(method.getName()) && (ArrayUtils.isEmpty(args))) {
            return this.toString();
        } else if (method.getName().startsWith("get")) {
            // get method annotation and check if it exists as expected

            final Method getter = typeRef.getMethod(method.getName());

            final Property property = ClassUtils.getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navProp = ClassUtils.getAnnotation(NavigationProperty.class, getter);
                if (navProp == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... navigate and follow link if necessary
                    return getNavigationPropertyValue(navProp, getter);
                }
            } else {
                // if the getter refers to a property .... get property from wrapped entity
                return getPropertyValue(property, getter.getGenericReturnType());
            }
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

                    setNavigationPropertyValue(args[0], navProp.name());
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

        for (ODataEntity entity : entitySet.getEntities()) {
            items.add((T) getEntityProxy(entity, entityContainerName, entitySet.getName(), typeRef, checkInTheContext));
        }

        return (EC) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {typeCollectionRef},
                new EntityCollectionInvocationHandler<T>(items, factory));
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable, EC extends AbstractEntityCollection<T>> EC getEntityCollection(
            final Collection<EntityTypeInvocationHandler> handlers, final Class<?> typeCollectionRef) {

        final List<T> items = new ArrayList<T>();

        if (handlers != null) {
            for (EntityTypeInvocationHandler handler : handlers) {
                items.add((T) Proxy.newProxyInstance(
                        Thread.currentThread().getContextClassLoader(),
                        new Class<?>[] {(Class<?>) handler.getTypeRef()},
                        handler));
            }
        }

        return (EC) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {typeCollectionRef},
                new EntityCollectionInvocationHandler<T>(items, factory));
    }

    @SuppressWarnings("unchecked")
    private <T> T getEntityProxy(final Collection<EntityTypeInvocationHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return null;
        } else {
            final EntityTypeInvocationHandler handler = handlers.iterator().next();
            return (T) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[] {(Class<?>) handler.getTypeRef()},
                    handler);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getEntityProxy(
            final ODataEntity entity,
            final String entityContainerName,
            final String entitySetName,
            final Class<?> type,
            final boolean checkInTheContext) {

        EntityTypeInvocationHandler handler = EntityTypeInvocationHandler.getInstance(
                entity, entityContainerName, entitySetName, type, factory);

        if (checkInTheContext && EntityContainerFactory.getContext().entityContext().isAttached(handler)) {
            handler = EntityContainerFactory.getContext().entityContext().getEntity(handler.getUUID());
        }

        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {type},
                handler);
    }

    private Object getNavigationPropertyValue(final NavigationProperty property, final Method getter) {
        Object res = null;

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
        final Schema schema = metadata.getSchema(MetadataUtils.getNamespace(typeRef));

        // 1) get association
        final Association association = MetadataUtils.getAssociation(schema, property.relationship());

        // 2) get entity container and association set
        final Map.Entry<EntityContainer, AssociationSet> associationSet =
                MetadataUtils.getAssociationSet(association, schema.getNamespace(), metadata);

        // 3) get entitySet
        final String targetEntitySetName = MetadataUtils.getEntitySetName(associationSet.getValue(), property.toRole());

        // retrieve ODataLink in the wrapped object.
        // If link exists then:
        // * maybe linked object is inline;
        // * maybe the current instance in not new (already persisted);
        // * maybe a merge between context and service is needed.
        // Otherwise:
        // * the link is not inline for sure
        // * the link is new (skip query on the service)
        // * the link should be present in the context
        final ODataLink link = MetadataUtils.getNavigationLink(property.name(), entity);

        if (link == null) {
            if (AbstractEntityCollection.class.isAssignableFrom(type)) {
                res = getEntityCollection(linkContext.getLinkedEntities(this, property.name()), type);
            } else {
                res = getEntityProxy(linkContext.getLinkedEntities(this, property.name()));
            }
        } else {
            if (link instanceof ODataInlineEntity) {
                // return entity
                res = getEntityProxy(
                        ((ODataInlineEntity) link).getEntity(),
                        associationSet.getKey().getName(),
                        targetEntitySetName,
                        type,
                        false);
            } else if (link instanceof ODataInlineEntitySet) {
                // return entity set
                res = getEntityCollection(
                        collItemType,
                        type,
                        associationSet.getKey().getName(),
                        ((ODataInlineEntitySet) link).getEntitySet(),
                        false);
            } else {
                if (linkContext.isAttached(this, property.name())) {
                    if (AbstractEntityCollection.class.isAssignableFrom(type)) {
                        res = getEntityCollection(
                                linkContext.getLinkedEntities(this, property.name()), type);
                    } else {
                        res = getEntityProxy(
                                linkContext.getLinkedEntities(this, property.name()));
                    }
                } else {
                    // navigate
                    final URI uri = URIUtils.getURI(factory.getServiceRoot(), link.getLink().toASCIIString());

                    if (AbstractEntityCollection.class.isAssignableFrom(type)) {
                        res = getEntityCollection(
                                collItemType,
                                type,
                                associationSet.getKey().getName(),
                                ODataRetrieveRequestFactory.getEntitySetRequest(uri).execute().getBody(),
                                true);
                    } else {
                        res = getEntityProxy(
                                ODataRetrieveRequestFactory.getEntityRequest(uri).execute().getBody(),
                                associationSet.getKey().getName(),
                                targetEntitySetName,
                                type,
                                true);
                    }
                }
            }
        }

        return res;
    }

    private Object getPropertyValue(final Property property, final Type type) {
        try {
            return changes.containsKey(property)
                    ? changes.get(property)
                    : getValueFromProperty(entity.getProperty(property.name()), type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error getting value for property '" + property.name() + "'", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getValueFromProperty(final ODataProperty property, final Type type)
            throws InstantiationException, IllegalAccessException {

        final Object res;

        if (property == null || property.hasNullValue()) {
            res = null;
        } else if (property.hasCollectionValue()) {
            res = new ArrayList();

            final ParameterizedType collType = (ParameterizedType) type;
            final Class<?> collItemClass = (Class<?>) collType.getActualTypeArguments()[0];

            final Iterator<ODataValue> collPropItor = property.getCollectionValue().iterator();
            while (collPropItor.hasNext()) {
                final ODataValue value = collPropItor.next();
                if (value.isPrimitive()) {
                    ((Collection) res).add(value.asPrimitive().toValue());
                }
                if (value.isComplex()) {
                    final Object collItem = collItemClass.newInstance();
                    ODataItemUtils.populate(factory.getMetadata(), collItem, value.asComplex().iterator());
                    ((Collection) res).add(collItem);
                }
            }
        } else if (property.hasPrimitiveValue()) {
            res = property.getPrimitiveValue().toValue();
        } else if (property.hasComplexValue()) {
            res = ((Class<?>) type).newInstance();
            ODataItemUtils.populate(factory.getMetadata(), res, property.getComplexValue().iterator());
        } else {
            throw new IllegalArgumentException("Invalid property " + property);
        }

        return res;
    }

    private void setNavigationPropertyValue(final Object arg, final String propName) {
        // 1) attach source entity
        final AttachedEntityStatus sourceStatus;
        if (entityContext.isAttached(this)) {
            sourceStatus = entityContext.getStatus(this);
        } else {
            sourceStatus = AttachedEntityStatus.CHANGED;
            entityContext.attach(this, sourceStatus);
        }

        // 2) check if args is a collection/entitySet
        final Collection toBeLinked;
        final boolean isCollection;
        // check for collection before
        if (AbstractEntityCollection.class.isAssignableFrom(arg.getClass())) {
            toBeLinked = (AbstractEntityCollection) arg;
            isCollection = true;
        } else {
            toBeLinked = Collections.singleton(arg);
            isCollection = false;
        }

        // 3) get the target entity handlers
        final List<EntityTypeInvocationHandler> targets = new ArrayList<EntityTypeInvocationHandler>();

        for (Object link : toBeLinked) {
            final InvocationHandler linkIH = Proxy.getInvocationHandler(link);
            if (!(linkIH instanceof EntityTypeInvocationHandler)) {
                throw new IllegalArgumentException("Invalid argument type");
            }

            final EntityTypeInvocationHandler handler = (EntityTypeInvocationHandler) linkIH;
            if (!handler.getTypeRef().isAnnotationPresent(EntityType.class)) {
                throw new IllegalArgumentException(
                        "Invalid argument type " + handler.getTypeRef().getSimpleName());
            }

            targets.add(handler);
        }

        // replace the link
        final ODataLink link = MetadataUtils.getNavigationLink(propName, entity);
        if (link != null) {
            entity.removeLink(link);
        }

        // be sure to replace previous changes in the link context
        if (linkContext.isAttached(this, propName)) {
            linkContext.detach(this, propName);
        }

        // 4) attach target entities and create links
        for (EntityTypeInvocationHandler target : targets) {
            // attach target entity
            if (!entityContext.isAttached(target)) {
                entityContext.attach(target, AttachedEntityStatus.LINKED);
            }

            // create the link
            linkContext.attach(new EntityLinkDesc(
                    propName, this, target, isCollection
                    ? ODataLinkType.ENTITY_SET_NAVIGATION : ODataLinkType.ENTITY_NAVIGATION));
        }
    }

    private void setPropertyValue(final Property key, final Object value) {
        changes.put(key, value);

        if (entityContext.isAttached(this)) {
            entityContext.setStatus(this, AttachedEntityStatus.CHANGED);
        } else {
            entityContext.attach(this, AttachedEntityStatus.CHANGED);
        }
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
