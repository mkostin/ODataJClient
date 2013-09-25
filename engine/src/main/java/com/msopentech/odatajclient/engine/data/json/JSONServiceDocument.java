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

import com.msopentech.odatajclient.engine.data.AbstractPayloadObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.ServiceDocumentResource;
import com.msopentech.odatajclient.engine.uri.SegmentType;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service document, represented via JSON.
 */
public class JSONServiceDocument extends AbstractPayloadObject implements ServiceDocumentResource {

    /**
     * Static representation of a top-level entity set.
     */
    static class JSONToplevelEntitySet extends AbstractPayloadObject {

        private static final long serialVersionUID = -972079849037041158L;

        private String name;

        private String url;

        /**
         * Gets name.
         *
         * @return name.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name.
         *
         * @param name name.
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Gets URL.
         *
         * @return URL.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets URL.
         *
         * @param url URL.
         */
        public void setUrl(final String url) {
            this.url = url;
        }
    }

    private static final long serialVersionUID = 4195734928526398830L;

    @JsonProperty(value = "odata.metadata", required = false)
    private URI metadata;

    @JsonProperty("value")
    private final List<JSONToplevelEntitySet> entitySets;

    /**
     * Constructor.
     */
    public JSONServiceDocument() {
        super();
        entitySets = new ArrayList<JSONToplevelEntitySet>();
    }

    @JsonIgnore
    @Override
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
     * Gets top-level entity sets.
     *
     * @return top-level entity sets.
     */
    public List<JSONToplevelEntitySet> getEntitySets() {
        return entitySets;
    }

    /**
     * Sets top-level entity sets.
     *
     * @param entitySets top-level entity sets.
     */
    public void setEntitySets(final List<JSONToplevelEntitySet> entitySets) {
        this.entitySets.clear();
        if (entitySets != null && !entitySets.isEmpty()) {
            this.entitySets.addAll(entitySets);
        }
    }

    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public Map<String, String> getToplevelEntitySets() {
        final Map<String, String> result = new HashMap<String, String>(entitySets.size());
        for (JSONToplevelEntitySet entitySet : entitySets) {
            result.put(entitySet.name, entitySet.url);
        }
        return result;
    }
}
