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
import com.msopentech.odatajclient.engine.communication.request.batch.AbstractBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.AbstractCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.invoke.AbstractInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.AbstractRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.AbstractStreamedRequestFactory;
import com.msopentech.odatajclient.engine.data.AbstractODataBinder;
import com.msopentech.odatajclient.engine.data.AbstractODataReader;
import com.msopentech.odatajclient.engine.data.AbstractODataWriter;
import com.msopentech.odatajclient.engine.uri.AbstractURIBuilder;
import com.msopentech.odatajclient.engine.uri.filter.AbstractFilterFactory;

public interface ODataClient {

    public abstract ODataHeaders getVersionHeaders();

    public abstract AbstractConfiguration getConfiguration();

    public abstract AbstractURIBuilder getURIBuilder(String serviceRoot);

    public abstract AbstractFilterFactory getFilterFactory();

    public abstract AbstractODataReader getODataReader();

    public abstract AbstractODataWriter getODataWriter();

    public abstract AbstractODataBinder getODataBinder();

    public abstract AbstractRetrieveRequestFactory getRetrieveRequestFactory();

    public abstract AbstractCUDRequestFactory getCUDRequestFactory();

    public abstract AbstractStreamedRequestFactory getStreamedRequestFactory();

    public abstract AbstractInvokeRequestFactory getInvokeRequestFactory();

    public abstract AbstractBatchRequestFactory getBatchRequestFactory();
}
