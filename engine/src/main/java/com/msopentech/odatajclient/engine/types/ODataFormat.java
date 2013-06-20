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

import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import javax.ws.rs.core.MediaType;

/**
 * Exchanged data format.
 */
public enum ODataFormat {

    /**
     * JSON format with no metadata.
     */
    JSON_NO_METADATA(MediaType.APPLICATION_JSON + ";odata=nometadata", JSONEntry.class),
    /**
     * JSON format with minimal metadata (default).
     */
    JSON(MediaType.APPLICATION_JSON + ";odata=minimalmetadata", JSONEntry.class),
    /**
     * JSON format with no metadata.
     */
    JSON_FULL_METADATA(MediaType.APPLICATION_JSON + ";odata=fullmetadata", JSONEntry.class),
    /**
     * Atom format.
     */
    ATOM(MediaType.APPLICATION_ATOM_XML, AtomEntry.class);

    private final String format;

    private final Class<? extends EntryResource> entryClass;

    ODataFormat(final String format, final Class<? extends EntryResource> entryClass) {
        this.format = format;
        this.entryClass = entryClass;
    }

    public Class<? extends EntryResource> getEntryClass() {
        return entryClass;
    }

    @Override
    public String toString() {
        return format;
    }
}
