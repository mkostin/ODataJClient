/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.engine.communication.request.invoke;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.data.ODataInvokeResult;
import com.msopentech.odatajclient.engine.data.ODataNoContent;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.FunctionImport;
import java.net.URI;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * OData request factory class.
 */
public class ODataInvokeRequestFactory {

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
    public static <T extends ODataInvokeResult> ODataInvokeRequest<T> getInvokeRequest(
            final URI uri, final EdmMetadata metadata, final FunctionImport functionImport) {

        Method method = null;
        if (Method.GET.name().equals(functionImport.getHttpMethod())) {
            method = Method.GET;
        } else if (Method.POST.name().equals(functionImport.getHttpMethod())) {
            method = Method.POST;
        } else if (functionImport.getHttpMethod() == null) {
            if (functionImport.isIsSideEffecting()) {
                method = Method.POST;
            } else {
                method = Method.GET;
            }
        }

        ODataInvokeRequest<T> result;
        if (StringUtils.isBlank(functionImport.getReturnType())) {
            result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataNoContent>(ODataNoContent.class, method);
        } else {
            final EdmType returnType = new EdmType(metadata, functionImport.getReturnType());

            if (returnType.isCollection() && returnType.isEntityType()) {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataEntitySet>(ODataEntitySet.class, method);
            } else if (!returnType.isCollection() && returnType.isEntityType()) {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataEntity>(ODataEntity.class, method);
            } else {
                result = (ODataInvokeRequest<T>) new ODataInvokeRequest<ODataProperty>(ODataProperty.class, method);
            }
        }
        result.setURI(uri);

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
    public static <T extends ODataInvokeResult> ODataInvokeRequest<T> getInvokeRequest(
            final URI uri, final EdmMetadata metadata, final FunctionImport functionImport,
            final Map<String, ODataValue> parameters) {

        final ODataInvokeRequest<T> result = getInvokeRequest(uri, metadata, functionImport);
        result.setParameters(parameters);

        return result;
    }
}
