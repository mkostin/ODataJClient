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

/**
 * REST resource for an <tt>ODataLink</tt>.
 *
 * @see ODataLink
 */
public interface LinkResource {

    String getRel();

    void setRel(String rel);

    String getType();

    void setType(String type);

    String getTitle();

    void setTitle(String title);

    String getHref();

    void setHref(String href);

    EntryResource getInlineEntry();

    void setInlineEntry(EntryResource entry);

    FeedResource getInlineFeed();

    void setInlineFeed(FeedResource feed);
}
