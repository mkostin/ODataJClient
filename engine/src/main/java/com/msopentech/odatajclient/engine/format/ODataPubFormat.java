/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.format;

import org.apache.http.entity.ContentType;

/**
 * Available formats for AtomPub exchange.
 */
public enum ODataPubFormat {

    /**
     * JSON format with no metadata.
     */
    JSON_NO_METADATA(ContentType.APPLICATION_JSON.getMimeType() + ";odata=nometadata"),
    /**
     * JSON format with minimal metadata (default).
     */
    JSON(ContentType.APPLICATION_JSON.getMimeType() + ";odata=minimalmetadata"),
    /**
     * JSON format with no metadata.
     */
    JSON_FULL_METADATA(ContentType.APPLICATION_JSON.getMimeType() + ";odata=fullmetadata"),
    /**
     * Atom format.
     */
    ATOM(ContentType.APPLICATION_ATOM_XML.getMimeType());

    private final String format;

    ODataPubFormat(final String format) {
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
     * Gets OData format from its string representation.
     *
     * @param format string representation of the format.
     * @return OData format.
     */
    public static ODataPubFormat fromString(final String format) {
        ODataPubFormat result = null;

        for (ODataPubFormat value : values()) {
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
