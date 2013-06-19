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

/**
 * OData inline feed.
 */
public abstract class ODataInlineFeed extends ODataLink {

    private ODataFeed feed;

    public ODataInlineFeed(final URI uri, final ODataLinkType type, final String title, final ODataFeed feed) {
        super(uri, type, title);
        this.feed = feed;
    }

    public ODataInlineFeed(final URI baseURI, final String href, final ODataLinkType type, final String title,
            final ODataFeed feed) {

        super(baseURI, href, type, title);
        this.feed = feed;
    }

    public ODataFeed getFeed() {
        return feed;
    }
}
