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

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;

/**
 * List of entries, represented via JSON.
 * 
 * @see JSONV3Entry
 */
public class JSONV4Feed extends AbstractJSONFeed implements FeedResource {

    private static final long serialVersionUID = -7196495866753867452L;

    @JsonProperty(value = "@odata.context", required = false)
    private String context;

    public JSONV4Feed() {
        super();
    }

    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public void setEntries(final List<EntryResource> entries) {
        this.entries.clear();
        for (EntryResource entry : entries) {
            if (entry instanceof JSONV4Entry) {
                this.entries.add((JSONV4Entry) entry);
            }
        }
    }

    /**
     * Gets URI to context of current entries list.
     * 
     * @return Context URI.
     */
    @JsonIgnore
    public URI getContext() {
        return context == null ? null : URI.create(context);
    }

    /**
     * Sets URI to context of current entries list.
     * 
     * @param context Context URI.
     */
    @JsonIgnore
    public void setContext(final URI context) {
        this.context = context.toASCIIString();
    }
}
