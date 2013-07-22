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
package com.msopentech.odatajclient.engine.format;

import org.apache.http.entity.ContentType;

/**
 * Available formats for the service document.
 */
public enum ODataServiceDocumentFormat {

    /**
     * JSON format with no metadata.
     */
    JSON_NO_METADATA(ContentType.APPLICATION_JSON.getMimeType() + ";odata=nometadata"),
    /**
     * JSON format with minimal metadata (default).
     * In this case, minimalmetadata is equivalent to fullmetadata.
     */
    JSON(ContentType.APPLICATION_JSON.getMimeType() + ";odata=minimalmetadata"),
    /**
     * XML format.
     */
    XML(ContentType.APPLICATION_XML.getMimeType());

    private final String format;

    ODataServiceDocumentFormat(final String format) {
        this.format = format;
    }

    /**
     * Gets format as a string.
     *
     * @return format as a string.
     */
    @Override
    public String toString() {
        return format;
    }

    /**
     * Gets format from its string representation.
     *
     * @param format string representation of the format.
     * @return OData format.
     */
    public static ODataServiceDocumentFormat fromString(final String format) {
        ODataServiceDocumentFormat result = null;

        for (ODataServiceDocumentFormat value : values()) {
            if (format.equals(value.toString())) {
                result = value;
            }
        }

        if (result == null) {
            throw new IllegalArgumentException(format);
        }

        return result;
    }
}
