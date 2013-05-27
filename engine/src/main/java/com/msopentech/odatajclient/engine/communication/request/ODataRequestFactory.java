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
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.data.ODataValue;
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
     * @return ODataCreateRequest instance.
     */
    public static ODataCreateRequest getCreateRequest(final ODataURI targetURI, final ODataEntity entity) {
        return new ODataCreateRequest(targetURI, entity);
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
    public static ODataCreatePrimitiveRequest getCreatePrimitiveRequest(final ODataURI targetURI, final ODataValue value) {
        return new ODataCreatePrimitiveRequest(targetURI, value);
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
    public static ODataAddLinkRequest getAddLinkRequest(final ODataURI targetURI, final ODataLink entityToBeAdded) {
        return new ODataAddLinkRequest(targetURI, entityToBeAdded);
    }

    /**
     * Gets a remove link request object instance.
     * <p>
     * Use this kind of request to remove a navigation link between existing entities.
     *
     * @param linkToBeRemoved navigation link to be removed.
     * @return ODataRemovedLinkRequest instance.
     */
    public static ODataRemoveLinkRequest getRemoveLinkRequest(final ODataURI linkToBeRemoved) {
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
     * @return ODataSetLinkRequest instance.
     */
    public static ODataSetLinkRequest getSetLinkRequest(
            final ODataURI targetURI, final ODataURI linkToBeRemoved, final ODataLink entityToBeAdded) {
        return new ODataSetLinkRequest(targetURI, linkToBeRemoved, entityToBeAdded);
    }

    /**
     * Gets a delete request object instance.
     * <p>
     * Use this kind of request to delete an entity.
     *
     * @param targetURI edit link of the object to be removed.
     * @return ODataDeleteRequest instance.
     */
    public static ODataDeleteRequest getDeleteRequest(final ODataURI targetURI) {
        return new ODataDeleteRequest(targetURI);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of update to be performed.
     * @return ODataUpdateRequest instance.
     */
    public static ODataUpdateRequest getUpdateRequest(final ODataURI targetURI, final ODataEntity entity,
            final UpdateType type) {
        return new ODataUpdateRequest(targetURI, entity, type);
    }

    /**
     * Gets a query request object instance.
     *
     * @param query query to be performed.
     * @return ODataQueryRequest instance.
     */
    public static ODataQueryRequest getQueryRequest(final ODataURI query) {
        return new ODataQueryRequest(query);
    }

    /**
     * Gets a batch request object instance.
     *
     * @return ODataBatchRequest instance.
     */
    public static ODataBatchRequest getBatchRequest() {
        return new ODataBatchRequest();
    }

    /**
     * Gets a changeset batch item instance.
     * A changeset can be submitted embedded into a batch request only.
     *
     * @return ODataChangeset instance.
     */
    public static ODataChangeset getChangesetBatchItem() {
        return new ODataChangeset();
    }

    /**
     * Gets a retrieve batch item instance.
     * A retrieve item can be submitted embedded into a batch request only.
     *
     * @return ODataRetrieve instance.
     */
    public static ODataRetrieve getRetrieveBatchItem(final ODataQueryRequest request) {
        return new ODataRetrieve(request);
    }

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeActionRequest(final ODataURI uri, final Map<String, ODataValue> parameters) {
        return new ODataInvokeRequest(Method.POST, uri, OperationType.ACTION, parameters);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return ODataInvokeRequest instance.
     */
    public static ODataInvokeRequest getInvokeFunctionRequest(final ODataURI uri) {
        return new ODataInvokeRequest(Method.GET, uri, OperationType.FUNCTION);
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
            final Method method, final ODataURI uri, final Map<String, ODataValue> parameters) {
        return new ODataInvokeRequest(method, uri, OperationType.LEGACY, parameters);
    }

    /**
     * Gets a metadata request instance.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     * @return ODataMetadataRequest instance.
     */
    public static ODataMetadataRequest getMetadataRequest(final String serviceRoot) {
        return new ODataMetadataRequest(new ODataURI(serviceRoot).appendMetadataSegment());
    }
}
