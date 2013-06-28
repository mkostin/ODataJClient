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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.communication.request.cud.ODataLinkCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataDeleteRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataMediaEntityCreateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataMediaEntityUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataPropertyUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataStreamUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataLinkUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataValueUpdateRequest;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequest;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataServiceDocumentRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntityRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataEntitySetRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataLinkRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMediaRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataPropertyRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRawRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataValueRequest;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

/**
 * OData request factory class.
 */
public final class ODataRequestFactory {

    private ODataRequestFactory() {
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
        return ODataCUDRequestFactory.getEntityCreateRequest(targetURI, entity);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of update to be performed.
     * @return new ODataUpdateEntityRequest instance.
     */
    public static ODataEntityUpdateRequest getEntityUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataEntity changes) {

        return ODataCUDRequestFactory.getEntityUpdateRequest(targetURI, type, changes);
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
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(final URI targetURI,
            final InputStream media) {

        return ODataCUDRequestFactory.getMediaEntityCreateRequest(targetURI, media);
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
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(final URI editURI,
            final InputStream media) {

        return ODataCUDRequestFactory.getMediaEntityUpdateRequest(editURI, media);
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
        return ODataCUDRequestFactory.getStreamUpdateRequest(targetURI, stream);
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
        return ODataCUDRequestFactory.getValueUpdateRequest(targetURI, type, value);
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
    public static ODataPropertyUpdateRequest getComplexUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataProperty property) {

        return ODataCUDRequestFactory.getComplexUpdateRequest(targetURI, type, property);
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
    public static ODataPropertyUpdateRequest getPrimitiveUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        return ODataCUDRequestFactory.getPrimitiveUpdateRequest(targetURI, property);
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
    public static ODataPropertyUpdateRequest getCollectionUpdateRequest(
            final URI targetURI, final ODataProperty property) {

        return ODataCUDRequestFactory.getCollectionUpdateRequest(targetURI, property);
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
        return ODataCUDRequestFactory.getLinkCreateRequest(targetURI, link);
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

        return ODataCUDRequestFactory.getLinkUpdateRequest(targetURI, type, link);
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
        return ODataCUDRequestFactory.getDeleteRequest(targetURI);
    }

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeActionRequest(final URI uri, final Map<String, ODataValue> parameters) {
        return ODataInvokeRequestFactory.getInvokeActionRequest(uri, parameters);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeFunctionRequest(final URI uri) {
        return ODataInvokeRequestFactory.getInvokeFunctionRequest(uri);
    }

    /**
     * Gets an invoke legacy operation request instance.
     *
     * @param method HTTP method of the request.
     * @param uri URI that identifies the function.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeLegacyRequest(final ODataRequest.Method method, final URI uri) {
        return ODataInvokeRequestFactory.getInvokeLegacyRequest(method, uri);
    }

    /**
     * Gets a service document request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataServiceDocumentRequest instance.
     */
    public static ODataServiceDocumentRequest getServiceDocumentRequest(final String serviceRoot) {
        return ODataRetrieveRequestFactory.getServiceDocumentRequest(serviceRoot);
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        return ODataRetrieveRequestFactory.getMetadataRequest(serviceRoot);
    }

    /**
     * Gets a query request returning a set of one or more OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntitySetRequest instance.
     */
    public static ODataEntitySetRequest getEntitySetRequest(final URI query) {
        return ODataRetrieveRequestFactory.getEntitySetRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity.
     *
     * @param query query to be performed.
     * @return new ODataEntityRequest instance.
     */
    public static ODataEntityRequest getEntityRequest(final URI query) {
        return ODataRetrieveRequestFactory.getEntityRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity property.
     *
     * @param query query to be performed.
     * @return new ODataPropertyRequest instance.
     */
    public static ODataPropertyRequest getPropertyRequest(final URI query) {
        return ODataRetrieveRequestFactory.getPropertyRequest(query);
    }

    /**
     * Gets a query request returning a single OData entity property value.
     *
     * @param query query to be performed.
     * @return new ODataValueRequest instance.
     */
    public static ODataValueRequest getValueRequest(final URI query) {
        return ODataRetrieveRequestFactory.getValueRequest(query);
    }

    /**
     * Gets a query request returning a single OData link.
     *
     * @param query query to be performed.
     * @return new ODataLinkRequest instance.
     */
    public static ODataLinkRequest getLinkRequest(final URI query, final String linkName) {
        return ODataRetrieveRequestFactory.getLinkRequest(query, linkName);
    }

    /**
     * Gets a query request returning a media stream.
     *
     * @param query query to be performed.
     * @return new ODataMediaRequest instance.
     */
    public static ODataMediaRequest getMediaRequest(final URI query) {
        return ODataRetrieveRequestFactory.getMediaRequest(query);
    }

    /**
     * Implements a raw request returning a stream.
     *
     * @param query query to be performed.
     * @return new ODataRawRetrieveRequest instance.
     */
    public static ODataRawRequest getRawRetrieveRequest(final URI uri) {
        return ODataRetrieveRequestFactory.getRawRetrieveRequest(uri);

    }
}
