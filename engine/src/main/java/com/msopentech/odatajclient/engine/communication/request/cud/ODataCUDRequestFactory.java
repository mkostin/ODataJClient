/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.net.URI;

/**
 * OData request factory class.
 */
public final class ODataCUDRequestFactory {

    /**
     * Private constructor.
     */
    private ODataCUDRequestFactory() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new entity.
     *
     * @param targetURI entity set URI.
     * @param entity entity to be created.
     * @return new ODataEntityCreateRequest instance.
     */
    public static ODataEntityCreateRequest getEntityCreateRequest(final URI targetURI, final ODataEntity entity) {
        return new ODataEntityCreateRequest(targetURI, entity);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param type type of update to be performed.
     * @param changes changes to be applied.
     * @return new ODataEntityUpdateRequest instance.
     */
    public static ODataEntityUpdateRequest getEntityUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataEntity changes) {

        final ODataEntityUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataEntityUpdateRequest(HttpMethod.POST, targetURI, changes);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataEntityUpdateRequest(type.getMethod(), targetURI, changes);
        }

        return req;
    }

    /**
     * Gets an update request object instance; uses entity's edit link as endpoint.
     *
     * @param type type of update to be performed.
     * @param entity changes to be applied.
     * @return new ODataEntityUpdateRequest instance.
     */
    public static ODataEntityUpdateRequest getEntityUpdateRequest(final UpdateType type, final ODataEntity entity) {
        if (entity.getEditLink() == null) {
            throw new IllegalArgumentException("No edit link found");
        }

        final ODataEntityUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataEntityUpdateRequest(HttpMethod.POST, entity.getEditLink(), entity);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataEntityUpdateRequest(type.getMethod(), entity.getEditLink(), entity);
        }

        return req;
    }

    /**
     * Gets a create request object instance.
     * <br/>
     * Use this kind of request to create a new value (e.g. http://Northwind.svc/Customer(1)/Picture/$value).
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param value value to be created.
     * @return new ODataValueUpdateRequest instance.
     */
    public static ODataValueUpdateRequest getValueUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataPrimitiveValue value) {

        final ODataValueUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataValueUpdateRequest(HttpMethod.POST, targetURI, value);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataValueUpdateRequest(type.getMethod(), targetURI, value);
        }

        return req;
    }

    /**
     * Gets an update request object instance.
     * <br/>
     * Use this kind of request to update a primitive property value.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param property value to be update.
     * @return new ODataPropertyUpdateRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyPrimitiveValueUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        if (!property.hasPrimitiveValue()) {
            throw new IllegalArgumentException("A primitive value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(HttpMethod.POST, targetURI, property);
            req.setXHTTPMethod(HttpMethod.PUT.name());
        } else {
            req = new ODataPropertyUpdateRequest(HttpMethod.PUT, targetURI, property);
        }

        return req;
    }

    /**
     * Gets an update request object instance.
     * <br/>
     * Use this kind of request to update a complex property value.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param property value to be update.
     * @return new ODataPropertyUpdateRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyComplexValueUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataProperty property) {

        if (!property.hasComplexValue()) {
            throw new IllegalArgumentException("A complex value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(HttpMethod.POST, targetURI, property);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataPropertyUpdateRequest(type.getMethod(), targetURI, property);
        }

        return req;
    }

    /**
     * Gets an update request object instance.
     * <br/>
     * Use this kind of request to update a collection property value.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param property value to be update.
     * @return new ODataPropertyUpdateRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyCollectionValueUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        if (!property.hasCollectionValue()) {
            throw new IllegalArgumentException("A collection value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(HttpMethod.POST, targetURI, property);
            req.setXHTTPMethod(HttpMethod.PUT.name());
        } else {
            req = new ODataPropertyUpdateRequest(HttpMethod.PUT, targetURI, property);
        }

        return req;
    }

    /**
     * Gets an add link request object instance.
     * <p>
     * Use this kind of request to create a navigation link between existing entities.
     *
     * @param targetURI navigation property's link collection.
     * @param link navigation link to be added.
     * @return new ODataLinkCreateRequest instance.
     */
    public static ODataLinkCreateRequest getLinkCreateRequest(final URI targetURI, final ODataLink link) {
        return new ODataLinkCreateRequest(targetURI, link);
    }

    /**
     * Gets a link update request object instance.
     * <p>
     * Use this kind of request to update a navigation link between existing entities.
     * <p>
     * In case of the old navigation link doesn't exist the new one will be added as well.
     *
     * @param targetURI navigation property's link collection.
     * @param type type of update to be performed.
     * @param link URL that identifies the entity to be linked.
     * @return new ODataLinkUpdateRequest instance.
     */
    public static ODataLinkUpdateRequest getLinkUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataLink link) {

        final ODataLinkUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataLinkUpdateRequest(HttpMethod.POST, targetURI, link);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataLinkUpdateRequest(type.getMethod(), targetURI, link);
        }

        return req;
    }

    /**
     * Gets a delete request object instance.
     * <p>
     * Use this kind of request to delete an entity and media entity as well.
     *
     * @param targetURI edit link of the object to be removed.
     * @return new ODataDeleteRequest instance.
     */
    public static ODataDeleteRequest getDeleteRequest(final URI targetURI) {
        final ODataDeleteRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataDeleteRequest(HttpMethod.POST, targetURI);
            req.setXHTTPMethod(HttpMethod.DELETE.name());
        } else {
            req = new ODataDeleteRequest(HttpMethod.DELETE, targetURI);
        }

        return req;
    }
}
