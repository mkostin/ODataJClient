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

import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataURI;
import com.msopentech.odatajclient.engine.data.ODataValue;
import com.msopentech.odatajclient.engine.utils.ODataWriter;
import java.io.InputStream;

/**
 * This class implements an OData create primitive request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getCreatePrimitiveRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataValue)
 */
public class ODataCreatePrimitiveRequest extends ODataRequest {

    /**
     * Value to be created.
     */
    private final ODataValue value;

    /**
     * Constructor.
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param value value to be created.
     */
    ODataCreatePrimitiveRequest(final ODataURI targetURI, final ODataPrimitiveValue value) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.POST);
        // set request body
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        return new ODataWriter(getFormat()).serialize(value);
    }
}
