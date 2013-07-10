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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.InputStream;
import java.net.URI;

/**
 * OData request factory class.
 */
public final class ODataCUDRequestFactory {

    private ODataCUDRequestFactory() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new entity.
     *
     * @param targetURI entity set URI.
     * @return new ODataCreateEntityRequest instance.
     */
    public static ODataEntityCreateRequest getEntityCreateRequest(final URI targetURI, final ODataEntity entity) {
        final ODataEntityCreateRequest req = new ODataEntityCreateRequest(entity);
        req.setURI(targetURI);
        return req;
    }

    /**
     * Gets a media entity create request object instance.
     * <p>
     * Use this kind of request to create a new media entity.
     *
     * @param targetURI entity set URI.
     * @param entity entity blob to be created.
     * @return new ODataMediaEntityCreateRequest instance.
     */
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(
            final URI targetURI, final InputStream media) {

        final ODataMediaEntityCreateRequest req = new ODataMediaEntityCreateRequest(media);
        req.setURI(targetURI);
        return req;
    }

    /**
     * Gets a stream update request object instance.
     * <p>
     * Use this kind of request to update a named stream property.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     * @return new ODataUpdateStreamRequest instance.
     */
    public static ODataStreamUpdateRequest getStreamUpdateRequest(final URI targetURI, final InputStream stream) {
        final ODataStreamUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataStreamUpdateRequest(Method.POST, stream);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataStreamUpdateRequest(Method.PUT, stream);
        }
        req.setURI(targetURI);

        return req;
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param changes changes to be applied.
     * @param type type of update to be performed.
     * @return new ODataUpdateEntityRequest instance.
     */
    public static ODataEntityUpdateRequest getEntityUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataEntity changes) {

        final ODataEntityUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataEntityUpdateRequest(Method.POST, changes);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataEntityUpdateRequest(type.getMethod(), changes);
        }
        req.setURI(targetURI);

        return req;
    }

    /**
     * Gets a media entity update request object instance.
     * <p>
     * Use this kind of request to update a media entity.
     *
     * @param editURI media entity edit link URI.
     * @param entity entity blob to be updated.
     * @return new ODataMediaEntityUpdateRequest instance.
     */
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(
            final URI editURI, final InputStream media) {

        final ODataMediaEntityUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataMediaEntityUpdateRequest(Method.POST, media);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataMediaEntityUpdateRequest(Method.PUT, media);
        }
        req.setURI(editURI);

        return req;
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new value (e.g. http://Northwind.svc/Customer(1)/Picture/$value).
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param value value to be created.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataValueUpdateRequest getValueUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataPrimitiveValue value) {

        final ODataValueUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataValueUpdateRequest(Method.POST, value);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataValueUpdateRequest(type.getMethod(), value);
        }
        req.setURI(targetURI);

        return req;
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a primitive property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyPrimitiveValueUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        if (!property.hasPrimitiveValue()) {
            throw new IllegalArgumentException("A primitive value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(Method.POST, property);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataPropertyUpdateRequest(Method.PUT, property);
        }
        req.setURI(targetURI);

        return req;
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a complex property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyComplexValueUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataProperty property) {

        if (!property.hasComplexValue()) {
            throw new IllegalArgumentException("A complex value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(Method.POST, property);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataPropertyUpdateRequest(type.getMethod(), property);
        }
        req.setURI(targetURI);

        return req;
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a collection property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getPropertyCollectionValueUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        if (!property.hasCollectionValue()) {
            throw new IllegalArgumentException("A collection value is required");
        }

        final ODataPropertyUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataPropertyUpdateRequest(Method.POST, property);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataPropertyUpdateRequest(Method.PUT, property);
        }
        req.setURI(targetURI);

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
        final ODataLinkCreateRequest req = new ODataLinkCreateRequest(link);
        req.setURI(targetURI);
        return req;
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
     * @param linkToBeRemoved navigation link to be removed.
     * @param link URL that identifies the entity to be linked.
     * @return new ODataLinkUpdateRequest instance.
     */
    public static ODataLinkUpdateRequest getLinkUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataLink link) {

        final ODataLinkUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataLinkUpdateRequest(Method.POST, link);
            req.setXHTTPMethod(type.getMethod().name());
        } else {
            req = new ODataLinkUpdateRequest(type.getMethod(), link);
        }
        req.setURI(targetURI);

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
            req = new ODataDeleteRequest(Method.POST);
            req.setXHTTPMethod(Method.DELETE.name());
        } else {
            req = new ODataDeleteRequest(Method.DELETE);
        }
        req.setURI(targetURI);

        return req;
    }
}
