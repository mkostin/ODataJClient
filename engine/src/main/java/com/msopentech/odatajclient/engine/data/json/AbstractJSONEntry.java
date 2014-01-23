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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msopentech.odatajclient.engine.data.AbstractEntryResource;
import com.msopentech.odatajclient.engine.data.AbstractLinkResource;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.uri.SegmentType;
import java.net.URI;

/**
 * A single entry, represented via JSON.
 */
public abstract class AbstractJSONEntry<ALR extends AbstractLinkResource<? extends EntryResource, ? extends FeedResource>> extends AbstractEntryResource<ALR> {

    private static final long serialVersionUID = -5275365545400797758L;

    private URI metadata;

    private String mediaETag;

    @JsonIgnore
    public URI getBaseURI() {
        URI baseURI = null;
        if (metadata != null) {
            final String metadataURI = getMetadata().toASCIIString();
            baseURI = URI.create(metadataURI.substring(0, metadataURI.indexOf(SegmentType.METADATA.getValue())));
        }

        return baseURI;
    }

    /**
     * Gets the metadata URI.
     *
     * @return the metadata URI
     */
    public URI getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata URI.
     *
     * @param metadata metadata URI.
     */
    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    /**
     * The odata.mediaEtag annotation MAY be included; its value MUST be the ETag of the binary stream represented by
     * this media entity or named stream property.
     *
     * @return odata.mediaEtag annotation value.
     */
    public String getMediaETag() {
        return mediaETag;
    }

    /**
     * The odata.mediaEtag annotation MAY be included; its value MUST be the ETag of the binary stream represented by
     * this media entity or named stream property.
     *
     * @param eTag odata.mediaEtag annotation value.
     */
    public void setMediaETag(String eTag) {
        this.mediaETag = eTag;
    }
}
