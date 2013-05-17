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

import com.msopentech.odatajclient.data.AbstractEntity;
import com.msopentech.odatajclient.data.Entity;
import com.msopentech.odatajclient.data.Link;
import com.msopentech.odatajclient.data.ODataURI;
import com.msopentech.odatajclient.types.ODataFormat;
import com.msopentech.odatajclient.utils.ODataSerializer;
import java.io.InputStream;

public class ODataCreateRequest extends ODataRequest {

    private final AbstractEntity entity;

    ODataCreateRequest(final ODataURI targetURI, final AbstractEntity entity) {
        // set method ...
        super(Method.POST);
        // set request body
        this.entity = entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getBody() {
        if (entity instanceof Link) {
            return entity.getLink().toStream();
        } else {
            return new ODataSerializer(header.getFormat()).serialize((Entity) entity);
        }
    }
}
