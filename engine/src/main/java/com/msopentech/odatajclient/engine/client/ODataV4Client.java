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
import com.msopentech.odatajclient.engine.communication.request.batch.V4BatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.V4CUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.invoke.V4InvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.V4RetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.V4StreamedRequestFactory;
import com.msopentech.odatajclient.engine.data.ODataV4Binder;
import com.msopentech.odatajclient.engine.data.ODataV4Reader;
import com.msopentech.odatajclient.engine.data.ODataV4Writer;
import com.msopentech.odatajclient.engine.uri.URIBuilder;
import com.msopentech.odatajclient.engine.uri.V4URIBuilder;
import com.msopentech.odatajclient.engine.uri.filter.V4FilterFactory;
import com.msopentech.odatajclient.engine.utils.ODataConstants;

public class ODataV4Client implements ODataClient {

    private final V4Configuration configuration = new V4Configuration();

    private final V4FilterFactory filterFactory = new V4FilterFactory();

    @Override
    public ODataHeaders getVersionHeaders() {
        final ODataHeaders odataHeaders = new ODataHeaders();
        odataHeaders.setHeader(ODataHeaders.HeaderName.maxDataServiceVersion, ODataConstants.Version.V4.toString());
        odataHeaders.setHeader(ODataHeaders.HeaderName.dataServiceVersion, ODataConstants.Version.V4.toString());
        return odataHeaders;
    }

    @Override
    public V4Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public URIBuilder getURIBuilder(final String serviceRoot) {
        return new V4URIBuilder(configuration, serviceRoot);
    }

    @Override
    public V4FilterFactory getFilterFactory() {
        return filterFactory;
    }

    @Override
    public ODataV4Reader getODataReader() {
        return ODataV4Reader.getInstance(this);
    }

    @Override
    public ODataV4Writer getODataWriter() {
        return ODataV4Writer.getInstance(this);
    }

    @Override
    public ODataV4Binder getODataBinder() {
        return ODataV4Binder.getInstance(this);
    }

    @Override
    public V4RetrieveRequestFactory getRetrieveRequestFactory() {
        return V4RetrieveRequestFactory.getInstance(this);
    }

    @Override
    public V4CUDRequestFactory getCUDRequestFactory() {
        return V4CUDRequestFactory.getInstance(this);
    }

    @Override
    public V4StreamedRequestFactory getStreamedRequestFactory() {
        return V4StreamedRequestFactory.getInstance(this);
    }

    @Override
    public V4InvokeRequestFactory getInvokeRequestFactory() {
        return V4InvokeRequestFactory.getInstance(this);
    }

    @Override
    public V4BatchRequestFactory getBatchRequestFactory() {
        return V4BatchRequestFactory.getInstance(this);
    }
}
