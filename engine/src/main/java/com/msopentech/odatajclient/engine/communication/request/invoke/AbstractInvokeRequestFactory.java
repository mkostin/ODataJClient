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
package com.msopentech.odatajclient.engine.communication.request.invoke;

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataInvokeResult;
import com.msopentech.odatajclient.engine.data.ODataNoContent;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.FunctionImport;
import java.net.URI;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * OData request factory class.
 */
public abstract class AbstractInvokeRequestFactory {

    protected final ODataClient client;

    protected AbstractInvokeRequestFactory(final ODataClient client) {
        this.client = client;
    }

    /**
     * Gets an invoke request instance.
     *
     * @param <T> OData domain object result, derived from return type defined in the function import
     * @param uri URI that identifies the function import
     * @param metadata Edm metadata
     * @param functionImport function import to be invoked
     * @return new ODataInvokeRequest instance.
     */
    @SuppressWarnings("unchecked")
    public <T extends ODataInvokeResult> ODataInvokeRequest<T> getInvokeRequest(
            final URI uri, final EdmMetadata metadata, final FunctionImport functionImport) {

        HttpMethod method = null;
        if (HttpMethod.GET.name().equals(functionImport.getHttpMethod())) {
            method = HttpMethod.GET;
        } else if (HttpMethod.POST.name().equals(functionImport.getHttpMethod())) {
            method = HttpMethod.POST;
        } else if (functionImport.getHttpMethod() == null) {
            if (functionImport.isSideEffecting()) {
                method = HttpMethod.POST;
            } else {
                method = HttpMethod.GET;
            }
        }

        ODataInvokeRequest<T> result;
        if (StringUtils.isBlank(functionImport.getReturnType())) {
            result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataNoContent>(
                    client, ODataNoContent.class, method, uri);
        } else {
            final EdmType returnType = new EdmType(metadata, functionImport.getReturnType());

            if (returnType.isCollection() && returnType.isEntityType()) {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataEntitySet>(
                        client, ODataEntitySet.class, method, uri);
            } else if (!returnType.isCollection() && returnType.isEntityType()) {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataEntity>(
                        client, ODataEntity.class, method, uri);
            } else {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataProperty>(
                        client, ODataProperty.class, method, uri);
            }
        }

        return result;
    }

    /**
     * Gets an invoke request instance.
     *
     * @param <T> OData domain object result, derived from return type defined in the function import
     * @param uri URI that identifies the function import
     * @param metadata Edm metadata
     * @param functionImport function import to be invoked
     * @param parameters parameters to pass to function import invocation
     * @return new ODataInvokeRequest instance.
     */
    @SuppressWarnings("unchecked")
    public <T extends ODataInvokeResult> ODataInvokeRequest<T> getInvokeRequest(
            final URI uri, final EdmMetadata metadata, final FunctionImport functionImport,
            final Map<String, ODataValue> parameters) {

        final ODataInvokeRequest<T> result = getInvokeRequest(uri, metadata, functionImport);
        result.setParameters(parameters);

        return result;
    }
}
