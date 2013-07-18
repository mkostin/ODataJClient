/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.msopentech.odatajclient.engine.data.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import java.net.URI;

/**
 * Abstract representation of a JSON metadata object.
 */
public abstract class AbstractJSONMetadataObject extends AbstractJSONObject {

    private static final long serialVersionUID = 8419977434428745822L;

    /**
     * Gets metadata URI.
     *
     * @return metadata URI.
     */
    public abstract URI getMetadata();

    /**
     * Gets base URI.
     *
     * @return base URI.
     */
    @JsonIgnore
    public URI getBaseURI() {
        URI baseURI = null;
        if (getMetadata() != null) {
            final String metadataURI = getMetadata().toASCIIString();
            baseURI = URI.create(
                    metadataURI.substring(0, metadataURI.indexOf(ODataURIBuilder.SegmentType.METADATA.getValue())));
        }

        return baseURI;
    }
}
