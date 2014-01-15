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
package com.msopentech.odatajclient.engine.data.metadata;

import com.msopentech.odatajclient.engine.client.ODataClient;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import com.msopentech.odatajclient.engine.data.metadata.edm.DataServices;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractEdmx;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Entry point for access information about EDM metadata.
 */
public class EdmMetadata implements Serializable {

    private static final long serialVersionUID = -1214173426671503187L;

    private final DataServices dataservices;

    private final Map<String, Schema> schemaByNsOrAlias;

    /**
     * Constructor.
     *
     * @param client OData client
     * @param inputStream source stream.
     */
    @SuppressWarnings("unchecked")
    public EdmMetadata(final ODataClient client, final InputStream inputStream) {
        final AbstractEdmx edmx = client.getDeserializer().toMetadata(inputStream);
        this.dataservices = edmx.getDataServices();

        this.schemaByNsOrAlias = new HashMap<String, Schema>();
        for (Schema schema : this.dataservices.getSchemas()) {
            this.schemaByNsOrAlias.put(schema.getNamespace(), schema);
            if (StringUtils.isNotBlank(schema.getAlias())) {
                this.schemaByNsOrAlias.put(schema.getAlias(), schema);
            }
        }
    }

    /**
     * Checks whether the given key is a valid namespace or alias in the EdM metadata document.
     *
     * @param key namespace or alias
     * @return true if key is valid namespace or alias
     */
    public boolean isNsOrAlias(final String key) {
        return this.schemaByNsOrAlias.keySet().contains(key);
    }

    /**
     * Returns the Schema at the specified position in the EdM metadata document.
     *
     * @param index index of the Schema to return
     * @return the Schema at the specified position in the EdM metadata document
     */
    public Schema getSchema(final int index) {
        return this.dataservices.getSchemas().get(index);
    }

    /**
     * Returns the Schema with the specified key (namespace or alias) in the EdM metadata document.
     *
     * @param key namespace or alias
     * @return the Schema with the specified key in the EdM metadata document
     */
    public Schema getSchema(final String key) {
        return this.schemaByNsOrAlias.get(key);
    }

    /**
     * Returns all Schema objects defined in the EdM metadata document.
     *
     * @return all Schema objects defined in the EdM metadata document
     */
    public List<Schema> getSchemas() {
        return this.dataservices.getSchemas();
    }
}
