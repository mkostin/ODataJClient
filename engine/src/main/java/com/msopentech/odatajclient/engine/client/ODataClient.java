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
import com.msopentech.odatajclient.engine.communication.request.batch.BatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.CUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.invoke.InvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.RetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.StreamedRequestFactory;
import com.msopentech.odatajclient.engine.data.Deserializer;
import com.msopentech.odatajclient.engine.data.ODataBinder;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.ODataWriter;
import com.msopentech.odatajclient.engine.data.ResourceFactory;
import com.msopentech.odatajclient.engine.data.Serializer;
import com.msopentech.odatajclient.engine.uri.URIBuilder;
import com.msopentech.odatajclient.engine.uri.filter.FilterFactory;
import com.msopentech.odatajclient.engine.utils.ODataConstants.Version;
import java.io.Serializable;

public interface ODataClient extends Serializable {

    Version getWorkingVersion();

    ODataHeaders getVersionHeaders();

    Configuration getConfiguration();

    URIBuilder getURIBuilder(String serviceRoot);

    FilterFactory getFilterFactory();

    Serializer getSerializer();

    Deserializer getDeserializer();

    ODataReader getODataReader();

    ODataWriter getODataWriter();

    ODataBinder getODataBinder();

    RetrieveRequestFactory getRetrieveRequestFactory();

    CUDRequestFactory getCUDRequestFactory();

    StreamedRequestFactory getStreamedRequestFactory();

    InvokeRequestFactory getInvokeRequestFactory();

    BatchRequestFactory getBatchRequestFactory();
    
    ResourceFactory getResourceFactory();
}
