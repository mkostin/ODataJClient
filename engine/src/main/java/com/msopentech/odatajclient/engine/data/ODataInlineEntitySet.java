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
package com.msopentech.odatajclient.engine.data;

import java.net.URI;

/**
 * OData in-line entity set.
 */
public class ODataInlineEntitySet extends ODataLink {

    private static final long serialVersionUID = -77628001615355449L;

    private ODataEntitySet entitySet;

    /**
     * Constructor.
     *
     * @param uri edit link.
     * @param type type.
     * @param title title.
     * @param entitySet entity set.
     */
    ODataInlineEntitySet(final URI uri, final ODataLinkType type, final String title,
            final ODataEntitySet entitySet) {

        super(uri, type, title);
        this.entitySet = entitySet;
    }

    /**
     * Constructor.
     *
     * @param baseURI base URI.
     * @param href href.
     * @param type type.
     * @param title title.
     * @param entitySet entity set.
     */
    ODataInlineEntitySet(final URI baseURI, final String href, final ODataLinkType type, final String title,
            final ODataEntitySet entitySet) {

        super(baseURI, href, type, title);
        this.entitySet = entitySet;
    }

    /**
     * Gets wrapped entity set.
     *
     * @return wrapped entity set.
     */
    public ODataEntitySet getEntitySet() {
        return entitySet;
    }
}
