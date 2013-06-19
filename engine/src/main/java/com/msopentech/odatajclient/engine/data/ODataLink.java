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

import static com.msopentech.odatajclient.engine.data.ODataLinkType.ASSOCIATION;
import static com.msopentech.odatajclient.engine.data.ODataLinkType.ENTITY_NAVIGATION;
import static com.msopentech.odatajclient.engine.data.ODataLinkType.FEED_NAVIGATION;
import static com.msopentech.odatajclient.engine.data.ODataLinkType.MEDIA_EDIT;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.net.URI;

/**
 * OData link.
 */
public abstract class ODataLink extends ODataItem {

    protected final ODataLinkType type;

    protected final String rel;

    public ODataLink(final URI uri, final ODataLinkType type, final String title) {
        super(title);
        this.link = uri;

        this.type = type;

        switch (this.type) {
            case ASSOCIATION:
                this.rel = ODataConstants.ASSOCIATION_LINK_REL + title;
                break;

            case ENTITY_NAVIGATION:
            case FEED_NAVIGATION:
                this.rel = ODataConstants.NAVIGATION_LINK_REL + title;
                break;

            case MEDIA_EDIT:
            default:
                this.rel = ODataConstants.MEDIA_EDIT_LINK_REL + title;
                break;
        }
    }

    public ODataLink(final URI baseURI, final String href, final ODataLinkType type, final String title) {
        this(URIUtils.getURI(baseURI, href), type, title);
    }

    /**
     * Gets link type.
     *
     * @return link type;
     */
    public ODataLinkType getType() {
        return type;
    }

    /**
     * Gets link rel.
     *
     * @return link rel
     */
    public String getRel() {
        return rel;
    }
}
