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

import com.msopentech.odatajclient.engine.communication.response.ODataLinkOperationResponse;
import com.msopentech.odatajclient.engine.data.ODataLink;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.concurrent.Future;

/**
 * This class implements a link set OData request.
 * It encapsulates two different request: the former remove link request and the latter add link request.
 * <p>
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getSetLinkRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataURI, com.msopentech.odatajclient.engine.data.ODataLink)
 */
public class ODataSetLinkRequest extends ODataBasicRequestImpl<ODataLinkOperationResponse>
        implements ODataBatchableRequest {

    /**
     * Entity to be linked.
     */
    private final ODataLink entityToBeAdded;

    /**
     * Constructor.
     *
     * @param targetURI entity URI.
     * @param linkToBeRemoved link to be removed.
     * @param entityToBeAdded entity to be linked.
     */
    ODataSetLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.PUT);
        // set target uri
        this.uri = targetURI;
        // set request body
        this.entityToBeAdded = entityToBeAdded;
    }

    @Override
    public ODataLinkOperationResponse execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<ODataLinkOperationResponse> asyncExecute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected byte[] getPayload() {
        try {
            return entityToBeAdded.getLink().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
