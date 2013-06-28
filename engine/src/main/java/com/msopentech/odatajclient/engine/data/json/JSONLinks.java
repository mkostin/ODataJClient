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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Link from an entry, represented via JSON.
 */
public class JSONLinks extends AbstractJSONMetadataObject {

    private static final long serialVersionUID = -5006368367235783907L;

    static class JSONLinkURL extends AbstractJSONObject {

        private static final long serialVersionUID = 5365055617973271468L;

        private URI url;

        public URI getUrl() {
            return url;
        }

        public void setUrl(final URI url) {
            this.url = url;
        }
    }

    @JsonProperty(value = "odata.metadata", required = false)
    private URI metadata;

    @JsonProperty(required = false)
    private URI url;

    @JsonProperty(value = "value", required = false)
    private final List<JSONLinkURL> links = new ArrayList<JSONLinkURL>();

    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    @Override
    public URI getMetadata() {
        return this.metadata;
    }

    /**
     * Smart management of different JSON format produced by OData services when
     * <tt>$links</tt> is a single or a collection property.
     *
     * @return list of URIs for <tt>$links</tt>
     */
    @JsonIgnore
    public List<URI> getLinks() {
        final List<URI> result = new ArrayList<URI>();

        if (this.url == null) {
            for (JSONLinkURL link : links) {
                result.add(link.getUrl());
            }
        } else {
            result.add(this.url);
        }

        return result;
    }
}
