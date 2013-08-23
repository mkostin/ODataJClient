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
import com.msopentech.odatajclient.engine.data.ODataFactory;
import com.msopentech.odatajclient.engine.data.ODataInlineEntity;
import com.msopentech.odatajclient.engine.data.ODataInlineEntitySet;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.AssociationSet;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import com.msopentech.odatajclient.proxy.api.context.AttachedEntityStatus;
import com.msopentech.odatajclient.proxy.api.EntityContainerFactory;
import com.msopentech.odatajclient.proxy.api.context.EntityContext;
import com.msopentech.odatajclient.proxy.api.Utility;
import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.context.EntityLinkDesc;
import com.msopentech.odatajclient.proxy.api.context.EntityTypeDesc;
import com.msopentech.odatajclient.proxy.api.context.LinkContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityTypeInvocationHandler extends AbstractInvocationHandler {

    private static final long serialVersionUID = 2629912294765040037L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EntityTypeInvocationHandler.class);

    private final ODataEntity entity;

    private final Class<?> typeRef;

    private final EntityTypeDesc description;

    private final Object key;

    private final EntityContext entityContext = EntityContainerFactory.getContext().getEntityContext();

    private final LinkContext linkContext = EntityContainerFactory.getContext().getLinkContext();

    static EntityTypeInvocationHandler getInstance(
            final ODataEntity entity,
            final EntitySetInvocationHandler entitySet) {

        return getInstance(
                entity,
                entitySet.getContainer().getEntityContainerName(),
                entitySet.getEntitySetName(),
                entitySet.getTypeRef(),
                entitySet.getContainer().getFactory());
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

        this.description = new EntityTypeDesc(
                Utility.getNamespace(typeRef),
                entityContainerName,
                entitySetName,
                entity.getName());

        this.key = Utility.getKey(typeRef, entity);

    }

    public Object getKey() {
        return key;
    }

    public EntityTypeDesc getDescription() {
        return description;
    }

    public String getName() {
        return this.entity.getName();
    }

    public String getEntityContainerName() {
        return description.getContainerName();
    }

    public String getEntitySetName() {
        return description.getEntitySetName();
    }

    public Class<?> getTypeRef() {
        return typeRef;
    }

    public ODataEntity getEntity() {
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // Assumption: for each getter will always exist a setter and viceversa.
        if ("hashCode".equals(method.getName())) {
            return this.hashCode();
        }
        if ("toString".equals(method.getName())) {
            return this.toString();
        }
        if ("equals".equals(method.getName())) {
            return this.equals(args[0]);
        } else if (method.getName().startsWith("get")) {
            // get method annotation and check if it exists as expected

            final Method getter = typeRef.getMethod(method.getName());

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
            final Method getter = typeRef.getMethod(getterName);

            final Property property = getAnnotation(Property.class, getter);
            if (property == null) {
                final NavigationProperty navProperty = getAnnotation(NavigationProperty.class, getter);
                if (navProperty == null) {
                    throw new UnsupportedOperationException("Unsupported method " + method.getName());
                } else {
                    // if the getter refers to a navigation property ... 
                    if (args == null || args.length != 1 || args[0] == null) {
                        throw new IllegalArgumentException("Invalid argument");
                    }

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
                    if (Collection.class.isAssignableFrom(args[0].getClass())) {
                        toBeLinked = (Collection) args[0];
                        isCollection = true;
                    } else {
                        toBeLinked = Collections.singleton(args[0]);
                        isCollection = false;
                    }

                    // 3) get the target entity handlers
                    final List<EntityTypeInvocationHandler> targets = new ArrayList<EntityTypeInvocationHandler>();

                    for (Object link : toBeLinked) {
                        if (!(Proxy.getInvocationHandler(link) instanceof EntityTypeInvocationHandler)) {
                            throw new IllegalArgumentException("Invalid argument type");
                        }

                        final EntityTypeInvocationHandler handler =
                                (EntityTypeInvocationHandler) Proxy.getInvocationHandler(link);

                        if (!handler.getTypeRef().isAnnotationPresent(EntityType.class)) {
                            throw new IllegalArgumentException(
                                    "Invalid argument type " + handler.getTypeRef().getSimpleName());
                        }

                        targets.add(handler);
                    }

                    // 4) attach target entities and create links
                    for (EntityTypeInvocationHandler target : targets) {
                        // attach target entity
                        final AttachedEntityStatus targetStatus;
                        if (entityContext.isAttached(target)) {
                            targetStatus = entityContext.getStatus(target);
                        } else {
                            targetStatus = AttachedEntityStatus.LINKED;
                            entityContext.attach(target, targetStatus);
                        }

                        // create the link
                        if (!isCollection && targets.size() == 1
                                && targetStatus == AttachedEntityStatus.NEW
                                && sourceStatus == AttachedEntityStatus.NEW) {
                            // if target and source are new make the link inline
                            // (see EntityCreateTestITCase.createWithInlineAs...())

                            // detach target (new target instance will be created inline)
                            entityContext.detach(target);
                            // add target inline
                            entity.addLink(ODataFactory.newInlineEntity(navProperty.name(), null, target.getEntity()));
                        } else {
                            // if target or source are not new then add a new link into the context
                            // (see EntityCreateTestITCase.createWithNavigationLink())
                            final NavigationProperty targetNavProperty =
                                    Utility.getNavigationProperty(target.getTypeRef(), navProperty.relationship());

                            linkContext.attach(new EntityLinkDesc(
                                    navProperty.name(),
                                    this,
                                    targetNavProperty == null ? null : targetNavProperty.name(),
                                    target));
                        }
                    }
                }
            } else {
                // if the getter exists and it is annotated as expected then get name/value and add a new property
                entity.addProperty(getODataProperty(args[0], property));

                if (entityContext.isAttached(this)) {
                    entityContext.setStatus(this, AttachedEntityStatus.CHANGED);
                } else {
                    entityContext.attach(this, AttachedEntityStatus.CHANGED);
                }
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

        final EdmMetadata metadata = getFactory().getMetadata();
        final Schema schema = metadata.getSchema(Utility.getNamespace(typeRef));

        // 1) get association
        final Association association = Utility.getAssociation(schema, property.relationship());

        // 2) get entity container and association set
        final Map.Entry<EntityContainer, AssociationSet> associationSet =
                Utility.getAssociationSet(association, schema.getNamespace(), metadata);

        // 3) get entitySet
        final String targetEntitySetName = Utility.getEntitySetName(associationSet.getValue(), property.toRole());

        // retrieve ODataLink in the wrapped object.
        // If link exists then:
        // * maybe linked object is inline;
        // * maybe the current instance in not new (already persisted);
        // * maybe a merge between context and service is needed.
        // Otherwise:
        // * the link is not inline for sure
        // * the link is new (skip query on the service)
        // * the link should be present in the context
        final ODataLink link = Utility.getNavigationLink(property.name(), entity);

        if (link == null) {
            // search for link in the context
            res = linkContext.getLinkedEntities(this, property.name());
        } else {
            if (link instanceof ODataInlineEntity) {
                // return entity
                res = getEntityProxy(
                        ((ODataInlineEntity) link).getEntity(),
                        associationSet.getKey().getName(),
                        targetEntitySetName,
                        (Class<?>) type);

            } else if (link instanceof ODataInlineEntitySet) {
                // return entity set
                res = getEntityProxies(
                        ((ParameterizedType) type),
                        associationSet.getKey().getName(),
                        ((ODataInlineEntitySet) link).getEntitySet());
            } else {
                if (linkContext.isAttached(this, property.name())) {
                    res = linkContext.getLinkedEntities(this, property.name());
                } else {
                    // navigate
                    final URI uri = URIUtils.getURI(getFactory().getServiceRoot(), link.getLink().toASCIIString());

                    if (getter.getReturnType().isAssignableFrom(Collection.class)) {
                        res = getEntityProxies(
                                ((ParameterizedType) type),
                                associationSet.getKey().getName(),
                                ODataRetrieveRequestFactory.getEntitySetRequest(uri).execute().getBody(),
                                true);
                    } else {

                        res = getEntityProxy(
                                ODataRetrieveRequestFactory.getEntityRequest(uri).execute().getBody(),
                                associationSet.getKey().getName(),
                                targetEntitySetName,
                                (Class<?>) type,
                                true);
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

    @Override
    public String toString() {
        return description + "(" + key + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityTypeInvocationHandler
                && ((EntityTypeInvocationHandler) obj).getDescription().equals(description)
                && ((EntityTypeInvocationHandler) obj).getKey() != null
                && ((EntityTypeInvocationHandler) obj).getKey().equals(key);
    }
}
