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
package com.msopentech.odatajclient.engine.data.metadata;

import com.msopentech.odatajclient.engine.data.metadata.edm.TSchema;
import com.msopentech.odatajclient.engine.data.metadata.edmx.TDataServices;
import com.msopentech.odatajclient.engine.data.metadata.edmx.TEdmx;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.StringUtils;

/**
 * Entry point for access information about EDM metadata.
 */
public class EdmMetadata implements Serializable {

    private static final long serialVersionUID = -1214173426671503187L;

    private TDataServices dataservices;

    private Map<String, TSchema> schemaByNsOrAlias;

    public EdmMetadata(final InputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TEdmx.class);
        @SuppressWarnings("unchecked")
        TEdmx edmx = ((JAXBElement<TEdmx>) context.createUnmarshaller().unmarshal(inputStream)).getValue();

        for (JAXBElement<?> edmxContent : edmx.getContent()) {
            if (TDataServices.class.equals(edmxContent.getDeclaredType())) {
                this.dataservices = (TDataServices) edmxContent.getValue();
            }
        }
        if (this.dataservices == null) {
            throw new IllegalArgumentException("No <DataServices/> element found");
        }

        this.schemaByNsOrAlias = new HashMap<String, TSchema>();
        for (TSchema schema : this.dataservices.getSchema()) {
            this.schemaByNsOrAlias.put(schema.getNamespace(), schema);
            if (StringUtils.isNotBlank(schema.getAlias())) {
                this.schemaByNsOrAlias.put(schema.getAlias(), schema);
            }
        }
    }

    /**
     * Checks whether the given key is a valid namespace or alias in the EdM metadata document.
     * @param key namespace or alias
     * @return true if key is valid namespace or alias
     */
    public boolean isNsOrAlias(final String key) {
        return this.schemaByNsOrAlias.keySet().contains(key);
    }

    /**
     * Returns the TSchema at the specified position in the EdM metadata document.
     *
     * @param index index of the TSchema to return
     * @return the TSchema at the specified position in the EdM metadata document
     */
    public TSchema getSchema(final int index) {
        return this.dataservices.getSchema().get(index);
    }

    /**
     * Returns the TSchema with the specified key (namespace or alias) in the EdM metadata document.
     *
     * @param key namespace or alias
     * @return the TSchema with the specified key in the EdM metadata document
     */
    public TSchema getSchema(final String key) {
        return this.schemaByNsOrAlias.get(key);
    }

    /**
     * Returns all TSchema objects defined in the EdM metadata document.
     *
     * @return all TSchema objects defined in the EdM metadata document
     */
    public List<TSchema> getSchemas() {
        return this.dataservices.getSchema();
    }
}
