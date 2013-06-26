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
package com.msopentech.odatajclient.engine.data.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.LinkResource;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * List of entries, represented via JSON.
 *
 * @see JSONEntry
 */
public class JSONFeed extends AbstractJSONObject implements FeedResource {

    private static final long serialVersionUID = -3576372289800799417L;

    @JsonProperty(value = "odata.metadata", required = false)
    private URI metadata;

    @JsonProperty("value")
    private List<JSONEntry> entries;

    @JsonProperty(value = "odata.nextLink", required = false)
    private String next;

    public JSONFeed() {
        super();
        entries = new ArrayList<JSONEntry>();
    }

    public URI getMetadata() {
        return metadata;
    }

    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    @JsonIgnore
    @Override
    public URI getBaseURI() {
        URI baseURI = null;
        if (metadata != null) {
            String metadataURI = metadata.toASCIIString();
            baseURI = URI.create(
                    metadataURI.substring(0, metadataURI.indexOf(ODataURIBuilder.SegmentType.METADATA.getValue())));
        }

        return baseURI;
    }

    @Override
    public List<JSONEntry> getEntries() {
        return entries;
    }

    @JsonIgnore
    @Override
    public void setEntries(final List<EntryResource> entries) {
        this.entries.clear();
        for (EntryResource entry : entries) {
            if (entry instanceof JSONEntry) {
                this.entries.add((JSONEntry) entry);
            }
        }
    }

    @JsonIgnore
    @Override
    public void setNext(LinkResource next) {
        this.next = next.getHref();
    }

    @JsonIgnore
    @Override
    public JSONLink getNext() {
        return new JSONLink(null, ODataConstants.NEXT_LINK_REL, next);
    }
}
