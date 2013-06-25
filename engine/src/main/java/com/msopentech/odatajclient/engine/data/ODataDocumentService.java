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

public class ODataDocumentService {

    private final Map<String, URI> topLevelEntitySets = new HashMap<String, URI>();

    public void addEntitySet(final String name, final URI uri) {
        topLevelEntitySets.put(name, uri);
    }

    public void removeEntitySet(final String name) {
        topLevelEntitySets.remove(name);
    }

    public Collection<String> getEntitySetNames() {
        return topLevelEntitySets.keySet();
    }

    public Collection<URI> getEntitySetURIs() {
        return topLevelEntitySets.values();
    }

    public URI getEntitySetURI(final String name) {
        return topLevelEntitySets.get(name);
    }

    public int size() {
        return topLevelEntitySets.size();
    }
}
