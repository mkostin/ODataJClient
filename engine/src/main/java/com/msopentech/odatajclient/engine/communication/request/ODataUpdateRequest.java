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

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.utils.ODataSerializer;
import java.io.InputStream;

/**
 * This class implements an OData update request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getUpdateRequest(com.msopentech.odatajclient.data.ODataURI,
 * com.msopentech.odatajclient.data.ODataEntity, com.msopentech.odatajclient.communication.request.UpdateType).
 */
public class ODataUpdateRequest extends ODataRequest {

    /**
     * Changes to be applied.
     */
    private final ODataEntity entity;

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be updated.
     * @param entity changes to be applied.
     * @param type update type.
     */
    ODataUpdateRequest(final ODataURI uri, final ODataEntity entity, final UpdateType type) {
        // set method .... If cofigured X-HTTP-METHOD header will be used.
        super(type.getMethod());
        // set request body ...
        this.entity = entity;
        // set uri ...
        this.uri = uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        return new ODataSerializer(header.getFormat()).serialize(entity);
    }
}
