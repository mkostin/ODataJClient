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
import com.msopentech.odatajclient.engine.data.AbstractEntity;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURI;
import java.util.Map;

/**
 * OData request factory class.
 */
public class ODataRequestFactory {

    /**
     * Gets a create request object instance. Use this kind of request for either entities and links.
     *
     * @param targetURI entity set or link attribute URI.
     * @param entity entity or relationships to be created.
     * @return ODataCreateRequest instance.
     */
    public static <T extends AbstractEntity> ODataRequest getCreateRequest(final ODataURI targetURI, final T entity) {
        return new ODataCreateRequest(targetURI, entity);
    }

    /**
     * Gets a delete request object instance.Use this kind of request for either entities and links.
     *
     * @param targetURI edit link of the object to be removed.
     * @return ODataDeleteRequest instance.
     */
    public static ODataRequest getDeleteRequest(final ODataURI targetURI) {
        return new ODataDeleteRequest(targetURI);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of upate to be performed.
     * @return ODataUpdateRequest instance.
     */
    public static ODataRequest getUpdateRequest(final ODataURI targetURI, final ODataEntity entity,
            final UpdateType type) {
        return new ODataUpdateRequest(targetURI, entity, type);
    }

    /**
     * Gets a query request object instance.
     *
     * @param query query to be performed.
     * @return ODataQueryRequest instance.
     */
    public static ODataRequest getQueryRequest(final ODataURI query) {
        return new ODataQueryRequest(query);
    }

    /**
     * Gets a batch request object instance.
     *
     * @return ODataBatchRequest instance.
     */
    public static ODataRequest getBatchRequest() {
        return new ODataBatchRequest();
    }

    /**
     * Gets a changeset request instance. This request can be submitted only embedded in batch request.
     *
     * @return ODataChangesetRequest instance.
     */
    public ODataRequest getChangesetRequest() {
        return new ODataChangesetRequest();
    }

    /**
     * Gets an invoke action request instance.
     *
     * @param uri URI that identifies the action.
     * @param parameters required input parameters.
     * @return ODataInvokeRequest instance.
     */
    public ODataRequest getInvokeActionRequest(final ODataURI uri, final Map<String, Object> parameters) {
        return new ODataInvokeRequest(Method.POST, uri, OperationType.ACTION, parameters);
    }

    /**
     * Gets an invoke function request instance.
     *
     * @param uri URI that identifies the function.
     * @return ODataInvokeRequest instance.
     */
    public ODataRequest getInvokeFunctionRequest(final ODataURI uri) {
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
    public ODataRequest getInvokeLegacyRequest(
            final Method method, final ODataURI uri, final Map<String, Object> parameters) {
        return new ODataInvokeRequest(method, uri, OperationType.LEGACY, parameters);
    }
}
