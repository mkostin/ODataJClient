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
import java.util.List;

/**
 * REST resource for an <tt>ODataEntitySet</tt>.
 *
 * @see ODataEntitySet
 */
public interface FeedResource {

    /**
     * Gets base URI.
     *
     * @return base URI.
     */
    URI getBaseURI();

    /**
     * Gets number of entries if an
     * <code>inlinecount</code> has been required.
     *
     * @return number of entries into the feed.
     */
    Integer getCount();

    /**
     * Gets entries.
     *
     * @return entries.
     */
    List<? extends EntryResource> getEntries();

    /**
     * Sets entries.
     *
     * @param entries entries.
     */
    void setEntries(List<EntryResource> entries);

    /**
     * Sets next link.
     *
     * @param next next link.
     */
    void setNext(LinkResource next);

    /**
     * Gets next link if exists.
     *
     * @return next link if exists; null otherwise.
     */
    LinkResource getNext();
}
