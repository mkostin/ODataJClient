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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataUpdateEntityResponse;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * This class implements an OData update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getUpdateRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataEntity,
 * com.msopentech.odatajclient.engine.communication.request.UpdateType)
 */
public class ODataEntityUpdateRequest extends ODataBasicRequestImpl<ODataUpdateEntityResponse>
        implements ODataBatchableRequest {

    /**
     * Changes to be applied.
     */
    private final ODataEntity changes;

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be updated.
     * @param type update type.
     * @param changes changes to be applied.
     */
    ODataEntityUpdateRequest(final URI uri, final UpdateType type, final ODataEntity changes) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod());
        // set uri ...
        this.uri = uri;
        this.changes = changes;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataUpdateEntityResponse execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected byte[] getPayload() {
        try {
            return "ODataUpdateEntity payload ...".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
