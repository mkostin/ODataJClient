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
package com.msopentech.odatajclient.communication.request;

import com.msopentech.odatajclient.data.Entity;
import com.msopentech.odatajclient.data.ODataURI;
import com.msopentech.odatajclient.types.ODataFormat;
import com.msopentech.odatajclient.utils.ODataSerializer;
import java.io.InputStream;

public class ODataUpdateRequest extends ODataRequest {

    private final Entity entity;

    ODataUpdateRequest(final ODataURI uri, final Entity entity, final UpdateType type) {
        // set method ...
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
