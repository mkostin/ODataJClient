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
package com.msopentech.odatajclient.engine.types;

import javax.ws.rs.core.MediaType;

/**
 * OData property format.
 */
public enum ODataPropertyFormat {

    /**
     * JSON format with no metadata.
     */
    JSON_NO_METADATA(MediaType.APPLICATION_JSON + ";odata=nometadata"),
    /**
     * JSON format with minimal metadata (default).
     */
    JSON(MediaType.APPLICATION_JSON + ";odata=minimalmetadata"),
    /**
     * JSON format with no metadata.
     */
    JSON_FULL_METADATA(MediaType.APPLICATION_JSON + ";odata=fullmetadata"),
    /**
     * XML format.
     */
    XML(MediaType.APPLICATION_XML);

    private final String format;

    ODataPropertyFormat(final String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
