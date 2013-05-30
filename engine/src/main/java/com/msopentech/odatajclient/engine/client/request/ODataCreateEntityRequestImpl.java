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
package com.msopentech.odatajclient.engine.client.request;

import com.msopentech.odatajclient.engine.communication.request.*;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.utils.ODataWriter;
import java.io.InputStream;

/**
 * This class implements an OData create request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getCreateRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataEntity)
 */
class ODataCreateEntityRequestImpl extends ODataRequestImpl implements ODataCreateEntityRequest {

    /**
     * Entity to be created.
     */
    private final ODataEntity entity;

    /**
     * Constructor.
     *
     * @param targetURI entity set URI.
     * @param entity entity to be created.
     */
    ODataCreateEntityRequestImpl(final ODataURI targetURI, final ODataEntity entity) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.POST);
        // set request body
        this.entity = entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        return new ODataWriter(getFormat()).serialize((ODataEntity) entity);
    }
}
