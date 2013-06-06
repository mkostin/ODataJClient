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

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.data.ODataValue;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

/**
 * OData request factory class.
 */
public class ODataRequestFactory {

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new entity.
     *
     * @param targetURI entity set URI.
     * @return new ODataCreateEntityRequest instance.
     */
    public static ODataEntityCreateRequest getEntityCreateRequest(final URI targetURI, final ODataEntity entity) {
        return new ODataEntityCreateRequest(targetURI, entity);
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
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(final URI targetURI, final InputStream media) {
        return new ODataMediaEntityCreateRequest(targetURI, media);
    }

    /**
     * Gets a stream create request object instance.
     * <p>
     * Use this kind of request to create a named stream property.
     *
     * @param targetURI target URI.
     * @param stream stream to be created.
     * @return new ODataCreateStreamRequest instance.
     */
    public static ODataStreamCreateRequest getStreamCreateRequest(final URI targetURI, final InputStream stream) {
        return new ODataStreamCreateRequest(targetURI, stream);
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
        return new ODataStreamUpdateRequest(targetURI, stream);
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
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(final URI editURI, final InputStream media) {
        return new ODataMediaEntityUpdateRequest(editURI, media);
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new value (e.g. http://Northwind.svc/Customer(1)/Picture/$value).
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param value value to be created.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPrimitiveCreateRequest getPrimitiveCreateRequest(
            final URI targetURI, final ODataPrimitiveValue value) {
        return new ODataPrimitiveCreateRequest(targetURI, value);
    }

    /**
     * Gets an add link request object instance.
     * <p>
     * Use this kind of request to create a navigation link between existing entities.
     *
     * @param targetURI navigation property's link collection.
     * @param entityToBeAdded navigation link to be added.
     * @return new ODataAddLinkRequest instance.
     */
    public static ODataAddLinkRequest getAddLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        return new ODataAddLinkRequest(targetURI, entityToBeAdded);
    }

    /**
     * Gets a remove link request object instance.
     * <p>
     * Use this kind of request to remove a navigation link between existing entities.
     *
     * @param linkToBeRemoved navigation link to be removed.
     * @return new ODataRemovedLinkRequest instance.
     */
    public static ODataRemoveLinkRequest getRemoveLinkRequest(final URI linkToBeRemoved) {
        return new ODataRemoveLinkRequest(linkToBeRemoved);
    }

    /**
     * Gets a link update request object instance.
     * <p>
     * Use this kind of request to update a navigation link between existing entities.
     * <p>
     * In case of the old navigation link doesn't exist the new one will be added as well.
     *
     * @param targetURI navigation property's link collection.
     * @param linkToBeRemoved navigation link to be removed.
     * @param entityToBeAdded URL that identifies the entity to be linked.
     * @return new ODataSetLinkRequest instance.
     */
    public static ODataSetLinkRequest getSetLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        return new ODataSetLinkRequest(targetURI, entityToBeAdded);
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
        return new ODataDeleteRequest(targetURI);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of update to be performed.
     * @return new ODataUpdateEntityRequest instance.
     */
    public static ODataUpdateEntityRequest getUpdateEntityRequest(
            final URI targetURI, final UpdateType type, final ODataEntity changes) {
        return new ODataUpdateEntityRequest(targetURI, type, changes);
    }

    /**
     * Gets a query request object instance.
     *
     * @param query query to be performed.
     * @return new ODataQueryRequest instance.
     */
    public static ODataQueryRequest getQueryRequest(final URI query) {
        return new ODataQueryRequest(query);
    }

    /**
     * Gets a query request returning a single result item.
     *
     * @param query query to be performed.
     * @return new ODataQueryRequest instance.
     */
    public static ODataSingleResultRequest getSingleResultRequest(final URI query) {
        return new ODataSingleResultRequest(query);
    }

    /**
     * Gets a batch request object instance.
     *
     * @return new ODataBatchRequest instance.
     */
    public static ODataBatchRequest getBatchRequest(final String serviceRoot) {
        return new ODataBatchRequest(new ODataURIBuilder(serviceRoot).appendBatchSegment().build());
    }

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeActionRequest(final URI uri, Map<String, ODataValue> parameters) {
        return new ODataInvokeRequest(Method.POST, uri, OperationType.ACTION);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeFunctionRequest(final URI uri) {
        return new ODataInvokeRequest(Method.GET, uri, OperationType.FUNCTION);
    }

    /**
     * Gets an invoke legacy operation request instance.
     *
     * @param method HTTP method of the request.
     * @param uri URI that identifies the function.
     * @param parameters required input parameters.
     * @return new ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeLegacyRequest(final Method method, final URI uri) {
        return new ODataInvokeRequest(method, uri, OperationType.LEGACY);
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return new ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        return new ODataMetadataRequest(new ODataURIBuilder(serviceRoot).appendMetadataSegment().build());
    }
}
