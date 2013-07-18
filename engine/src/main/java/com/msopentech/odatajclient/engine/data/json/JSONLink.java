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
package com.msopentech.odatajclient.engine.data.json;

import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.data.LinkResource;

/**
 * Link from an entry, represented via JSON.
 */
public class JSONLink extends AbstractJSONObject implements LinkResource {

    private static final long serialVersionUID = 4662606817302869095L;

    private String title;

    private String rel;

    private String href;

    private String type;

    private JSONEntry entry;

    private JSONFeed feed;

    /**
     * {@inheritDoc }
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRel() {
        return rel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setRel(final String rel) {
        this.rel = rel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getHref() {
        return href;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setHref(final String href) {
        this.href = href;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EntryResource getInlineEntry() {
        return entry;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setInlineEntry(final EntryResource entry) {
        if (entry instanceof JSONEntry) {
            this.entry = (JSONEntry) entry;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public FeedResource getInlineFeed() {
        return feed;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setInlineFeed(final FeedResource feed) {
        if (feed instanceof JSONFeed) {
            this.feed = (JSONFeed) feed;
        }
    }
}
