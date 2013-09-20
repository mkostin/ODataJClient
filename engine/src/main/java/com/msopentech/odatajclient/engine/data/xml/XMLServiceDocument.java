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
package com.msopentech.odatajclient.engine.data.xml;

import com.msopentech.odatajclient.engine.data.ServiceDocumentResource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Service document, represented via XML.
 */
public class XMLServiceDocument implements ServiceDocumentResource {

    private URI baseURI;

    private final Map<String, String> toplevelEntitySets = new HashMap<String, String>();

    /**
     * {@inheritDoc }
     */
    @Override
    public URI getBaseURI() {
        return this.baseURI;
    }

    /**
     * Sets base URI.
     *
     * @param baseURI base URI.
     */
    public void setBaseURI(final URI baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Add top-level entity set.
     *
     * @param title title.
     * @param href href.
     */
    public void addToplevelEntitySet(final String title, final String href) {
        this.toplevelEntitySets.put(title, href);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, String> getToplevelEntitySets() {
        return this.toplevelEntitySets;
    }
}
