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
package com.msopentech.odatajclient.engine.client.request;

import com.msopentech.odatajclient.engine.communication.request.*;
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
     * @param entity entity to be created.
     * @return ODataCreateEntityRequest instance.
     */
    public static ODataCreateEntityRequest getCreateEntityRequest(final URI targetURI, final ODataEntity entity) {
        return new ODataCreateEntityRequestImpl(targetURI, entity);
    }

    /**
     * Gets a media entity create request object instance.
     * <p>
     * Use this kind of request to create a new media entity.
     *
     * @param targetURI entity set URI.
     * @param entity entity blob to be created.
     * @return ODataMediaEntityCreateRequest instance.
     */
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(final URI targetURI,
            final InputStream entity) {
        return new ODataMediaEntityCreateRequestImpl(targetURI, entity);
    }

    /**
     * Gets a media entity update request object instance.
     * <p>
     * Use this kind of request to update a media entity.
     *
     * @param editURI media entity edit link URI.
     * @param entity entity blob to be updated.
     * @return ODataMediaEntityUpdateRequest instance.
     */
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(final URI editURI, final InputStream entity) {
        return new ODataMediaEntityUpdateRequestImpl(editURI, entity);
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new value (e.g. http://Northwind.svc/Customer(1)/Picture/$value).
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param value value to be created.
     * @return ODataCreatePrimitiveRequest instance.
     */
    public static ODataCreatePrimitiveRequest getCreatePrimitiveRequest(final URI targetURI,
            final ODataPrimitiveValue value) {
        return new ODataCreatePrimitiveRequestImpl(targetURI, value);
    }

    /**
     * Gets an add link request object instance.
     * <p>
     * Use this kind of request to create a navigation link between existing entities.
     *
     * @param targetURI navigation property's link collection.
     * @param entityToBeAdded navigation link to be added.
     * @return ODataAddLinkRequest instance.
     */
    public static ODataAddLinkRequest getAddLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        return new ODataAddLinkRequestImpl(targetURI, entityToBeAdded);
    }

    /**
     * Gets a remove link request object instance.
     * <p>
     * Use this kind of request to remove a navigation link between existing entities.
     *
     * @param linkToBeRemoved navigation link to be removed.
     * @return ODataRemovedLinkRequest instance.
     */
    public static ODataRemoveLinkRequest getRemoveLinkRequest(final URI linkToBeRemoved) {
        return new ODataRemoveLinkRequestImpl(linkToBeRemoved);
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
     * @return ODataSetLinkRequest instance.
     */
    public static ODataSetLinkRequest getSetLinkRequest(final URI targetURI, final URI linkToBeRemoved,
            final ODataLink entityToBeAdded) {
        return new ODataSetLinkRequestImpl(targetURI, linkToBeRemoved, entityToBeAdded);
    }

    /**
     * Gets a delete request object instance.
     * <p>
     * Use this kind of request to delete an entity and media entity as well.
     *
     * @param targetURI edit link of the object to be removed.
     * @return ODataDeleteRequest instance.
     */
    public static ODataDeleteRequest getDeleteRequest(final URI targetURI) {
        return new ODataDeleteRequestImpl(targetURI);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of update to be performed.
     * @return ODataUpdateEntityRequest instance.
     */
    public static ODataUpdateEntityRequest getUpdateEntityRequest(
            final URI targetURI, final ODataEntity entity, final UpdateType type) {
        return new ODataUpdateEntityRequestImpl(targetURI, entity, type);
    }

    /**
     * Gets a query request object instance.
     *
     * @param query query to be performed.
     * @return ODataQueryRequest instance.
     */
    public static ODataQueryRequest getQueryRequest(final URI query) {
        return new ODataQueryRequestImpl(query);
    }

    /**
     * Gets a query request returning a single result item.
     *
     * @param query query to be performed.
     * @return ODataQueryRequest instance.
     */
    public static ODataSingleResultRequest getSingleResultRequest(final URI query) {
        return new ODataSingleResultRequestImpl(query);
    }

    /**
     * Gets a batch request object instance.
     *
     * @return ODataBatchRequest instance.
     */
    public static ODataBatchRequest getBatchRequest() {
        return new ODataBatchRequestImpl();
    }

    /**
     * Gets a changeset batch item instance.
     * A changeset can be submitted embedded into a batch request only.
     *
     * @return ODataChangeset instance.
     */
    public static ODataChangeset getChangesetBatchItem() {
        return new ODataChangesetImpl();
    }

    /**
     * Gets a retrieve batch item instance.
     * A retrieve item can be submitted embedded into a batch request only.
     *
     * @return ODataRetrieve instance.
     */
    public static ODataRetrieve getRetrieveBatchItem(final ODataQueryRequest request) {
        return new ODataRetrieveImpl(request);
    }

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeActionRequest(final URI uri,
            final Map<String, ODataValue> parameters) {
        return new ODataInvokeRequestImpl(Method.POST, uri, OperationType.ACTION, parameters);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeFunctionRequest(final URI uri) {
        return new ODataInvokeRequestImpl(Method.GET, uri, OperationType.FUNCTION);
    }

    /**
     * Gets an invoke legacy operation request instance.
     *
     * @param method HTTP method of the request.
     * @param uri URI that identifies the function.
     * @param parameters required input parameters.
     * @return ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeLegacyRequest(
            final Method method, final URI uri, final Map<String, ODataValue> parameters) {
        return new ODataInvokeRequestImpl(method, uri, OperationType.LEGACY, parameters);
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        return new ODataMetadataRequestImpl(new ODataURIBuilder(serviceRoot).appendMetadataSegment().build());
    }
}
