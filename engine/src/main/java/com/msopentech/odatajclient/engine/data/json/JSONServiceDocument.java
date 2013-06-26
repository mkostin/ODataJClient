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
import com.msopentech.odatajclient.engine.data.ServiceDocumentResource;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service document, represented via JSON.
 */
public class JSONServiceDocument extends AbstractJSONMetadataObject implements ServiceDocumentResource {

    static class JSONToplevelEntitySet extends AbstractJSONObject {

        private static final long serialVersionUID = -972079849037041158L;

        private String name;

        private String url;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(final String url) {
            this.url = url;
        }
    }

    private static final long serialVersionUID = 4195734928526398830L;

    @JsonProperty(value = "odata.metadata", required = false)
    private URI metadata;

    @JsonProperty("value")
    private final List<JSONToplevelEntitySet> entitySets;

    public JSONServiceDocument() {
        super();
        entitySets = new ArrayList<JSONToplevelEntitySet>();
    }

    @Override
    public URI getMetadata() {
        return metadata;
    }

    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    public List<JSONToplevelEntitySet> getEntitySets() {
        return entitySets;
    }

    public void setEntitySets(final List<JSONToplevelEntitySet> entitySets) {
        this.entitySets.clear();
        if (entitySets != null && !entitySets.isEmpty()) {
            this.entitySets.addAll(entitySets);
        }
    }

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
