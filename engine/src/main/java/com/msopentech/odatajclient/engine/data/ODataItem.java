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

/**
 * Abstract representation of entities and navigation links.
 */
public abstract class ODataItem implements Serializable {

    private static final long serialVersionUID = 1961061310337516113L;

    /**
     * OData entity edit link.
     */
    protected ODataURI link;

    /**
     * OData entity title.
     */
    private final String title;

    /**
     * Constructor.
     *
     * @param title OData entity title.
     */
    public ODataItem(final String title) {
        this.title = title;
    }

    /**
     * Returns OData entity edit link.
     *
     * @return entity edit link.
     */
    public ODataURI getLink() {
        return link;
    }

    /**
     * Returns OData entity title.
     *
     * @return entity title.
     */
    public String getTitle() {
        return title;
    }
}