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

import java.io.Serializable;
import java.net.URI;
import org.slf4j.LoggerFactory;

/**
 * Abstract representation of entities and navigation links.
 */
public abstract class ODataItem implements Serializable {

    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataItem.class);

    private static final long serialVersionUID = 1961061310337516113L;

    /**
     * OData entity self link.
     */
    protected URI link;

    /**
     * OData entity name/type.
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param name OData entity name.
     */
    public ODataItem(final String name) {
        this.name = name;
    }

    /**
     * Returns self link.
     *
     * @return entity edit link.
     */
    public URI getLink() {
        return link;
    }

    /**
     * Sets self link.
     *
     * @param self link.
     */
    public void setLink(final URI link) {
        this.link = link;
    }

    /**
     * Returns OData entity name.
     *
     * @return entity name.
     */
    public String getName() {
        return name;
    }
}
