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

import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.atom.AtomLink;
import com.msopentech.odatajclient.engine.data.json.JSONV4Entry;
import com.msopentech.odatajclient.engine.data.json.JSONV4Feed;
import com.msopentech.odatajclient.engine.data.json.JSONV4Link;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;

public class V4ResourceFactory implements ResourceFactory {
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends FeedResource, K extends EntryResource> Class<K> entryClassForFeed(Class<T> resourceClass) {
        Class<K> result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONV4Feed.class.equals(resourceClass)) {
            result = (Class<K>) JSONV4Entry.class;
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends EntryResource> Class<T> entryClassForFormat(ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomEntry.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
                result = (Class<T>) JSONV4Entry.class;
                break;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource, K extends EntryResource> Class<K> entryClassForLink(Class<T> resourceClass) {
        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONV4Link.class.equals(resourceClass)) {
            result = (Class<K>) JSONV4Entry.class;
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends FeedResource> Class<T> feedClassForFormat(ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomFeed.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
                result = (Class<T>) JSONV4Feed.class;
                break;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource, K extends FeedResource> Class<K> feedClassForLink(Class<T> resourceClass) {
        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomFeed.class;
        }
        if (JSONV4Link.class.equals(resourceClass)) {
            result = (Class<K>) JSONV4Feed.class;
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource, K extends EntryResource> Class<T> linkClassForEntry(Class<K> resourceClass) {
        Class<T> result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (Class<T>) AtomLink.class;
        }
        if (JSONV4Entry.class.equals(resourceClass)) {
            result = (Class<T>) JSONV4Link.class;
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends EntryResource> T newEntry(Class<T> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomEntry();
        }
        if (JSONV4Entry.class.equals(resourceClass)) {
            result = (T) new JSONV4Entry();
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends FeedResource> T newFeed(Class<T> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomFeed();
        }
        if (JSONV4Feed.class.equals(resourceClass)) {
            result = (T) new JSONV4Feed();
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource> T newLink(Class<T> resourceClass) {
        T result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV4Link.class.equals(resourceClass)) {
            result = (T) new JSONV4Link();
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource, K extends EntryResource> T newLinkForEntry(Class<K> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV4Entry.class.equals(resourceClass)) {
            result = (T) new JSONV4Link();
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends LinkResource, K extends FeedResource> T newLinkForFeed(Class<K> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV4Feed.class.equals(resourceClass)) {
            result = (T) new JSONV4Link();
        }

        return result;
    }
}