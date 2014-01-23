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
package com.msopentech.odatajclient.engine.data.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.EntryResource;

/**
 * List of entries, represented via JSON.
 *
 * @see JSONV3Entry
 */
public class JSONV3Feed extends AbstractJSONFeed {

    private static final long serialVersionUID = -3576372289800799417L;

    @JsonProperty(value = "@odata.context", required = false)
    protected String context;

    @JsonProperty("value")
    private final List<JSONV3Entry> entries;

    /**
     * Constructor.
     */
    public JSONV3Feed() {
        super();
        entries = new ArrayList<JSONV3Entry>();
    }

    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public void setEntries(final List<EntryResource> entries) {
        this.entries.clear();
        for (EntryResource entry : entries) {
            if (entry instanceof JSONV3Entry) {
                this.entries.add((JSONV3Entry) entry);
            }
        }
    }

    @Override
    public List<JSONV3Entry> getEntries() {
        return entries;
    }

    @Override
    public boolean addEntry(AbstractJSONEntry<?> entry) {
        if (entry instanceof JSONV3Entry) {
            return entries.add((JSONV3Entry)entry);
        }

        return false;
    }
}
