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
package com.msopentech.odatajclient.engine.client;

import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.request.batch.V3BatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.V3CUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.invoke.V3InvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.V3RetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.V3StreamedRequestFactory;
import com.msopentech.odatajclient.engine.data.ODataV3Binder;
import com.msopentech.odatajclient.engine.data.ODataV3Reader;
import com.msopentech.odatajclient.engine.data.ODataV3Writer;
import com.msopentech.odatajclient.engine.uri.V3URIBuilder;
import com.msopentech.odatajclient.engine.uri.filter.V3FilterFactory;
import com.msopentech.odatajclient.engine.utils.ODataConstants;

public class ODataV3Client implements ODataClient {

    private final V3Configuration configuration = new V3Configuration();

    private final V3FilterFactory filterFactory = new V3FilterFactory();

    @Override
    public ODataHeaders getVersionHeaders() {
        final ODataHeaders odataHeaders = new ODataHeaders();
        odataHeaders.setHeader(ODataHeaders.HeaderName.minDataServiceVersion, ODataConstants.Version.V3.toString());
        odataHeaders.setHeader(ODataHeaders.HeaderName.maxDataServiceVersion, ODataConstants.Version.V3.toString());
        odataHeaders.setHeader(ODataHeaders.HeaderName.dataServiceVersion, ODataConstants.Version.V3.toString());
        return odataHeaders;
    }

    @Override
    public V3Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public V3URIBuilder getURIBuilder(final String serviceRoot) {
        return new V3URIBuilder(configuration, serviceRoot);
    }

    @Override
    public V3FilterFactory getFilterFactory() {
        return filterFactory;
    }

    @Override
    public ODataV3Reader getODataReader() {
        return ODataV3Reader.getInstance(this);
    }

    @Override
    public ODataV3Writer getODataWriter() {
        return ODataV3Writer.getInstance(this);
    }

    @Override
    public ODataV3Binder getODataBinder() {
        return ODataV3Binder.getInstance(this);
    }

    @Override
    public V3RetrieveRequestFactory getRetrieveRequestFactory() {
        return V3RetrieveRequestFactory.getInstance(this);
    }

    @Override
    public V3CUDRequestFactory getCUDRequestFactory() {
        return V3CUDRequestFactory.getInstance(this);
    }

    @Override
    public V3StreamedRequestFactory getStreamedRequestFactory() {
        return V3StreamedRequestFactory.getInstance(this);
    }

    @Override
    public V3InvokeRequestFactory getInvokeRequestFactory() {
        return V3InvokeRequestFactory.getInstance(this);
    }

    @Override
    public V3BatchRequestFactory getBatchRequestFactory() {
        return V3BatchRequestFactory.getInstance(this);
    }

}
