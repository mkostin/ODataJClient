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
package com.msopentech.odatajclient.engine.data;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of an OData service document.
 */
public class ODataServiceDocument {

    private final Map<String, URI> toplevelEntitySets = new HashMap<String, URI>();

    /**
     * Add entity set.
     *
     * @param name name.
     * @param uri URI.
     */
    public void addEntitySet(final String name, final URI uri) {
        toplevelEntitySets.put(name, uri);
    }

    /**
     * Removes entity set.
     *
     * @param name name.
     */
    public void removeEntitySet(final String name) {
        toplevelEntitySets.remove(name);
    }

    /**
     * Gets entity set names.
     *
     * @return entity set names.
     */
    public Collection<String> getEntitySetNames() {
        return toplevelEntitySets.keySet();
    }

    /**
     * Gets entity set URIs.
     *
     * @return entity set URIs.
     */
    public Collection<URI> getEntitySetURIs() {
        return toplevelEntitySets.values();
    }

    /**
     * Gets URI about the given entity set.
     *
     * @param name name.
     * @return URI.
     */
    public URI getEntitySetURI(final String name) {
        return toplevelEntitySets.get(name);
    }

    /**
     * Gets the number of all top level entity sets.
     *
     * @return number of all top level entity sets.
     */
    public int count() {
        return toplevelEntitySets.size();
    }
}
