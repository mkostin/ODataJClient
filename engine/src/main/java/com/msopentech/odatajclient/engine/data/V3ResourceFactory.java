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
import com.msopentech.odatajclient.engine.data.json.JSONV3Entry;
import com.msopentech.odatajclient.engine.data.json.JSONV3Feed;
import com.msopentech.odatajclient.engine.data.json.JSONV3Link;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;

public class V3ResourceFactory implements ResourceFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends FeedResource> T newFeed(final Class<T> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomFeed();
        }
        if (JSONV3Feed.class.equals(resourceClass)) {
            result = (T) new JSONV3Feed();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntryResource> T newEntry(final Class<T> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomEntry();
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (T) new JSONV3Entry();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource> T newLink(final Class<T> resourceClass) {
        T result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV3Link.class.equals(resourceClass)) {
            result = (T) new JSONV3Link();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends FeedResource> Class<T> feedClassForFormat(final ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomFeed.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
                result = (Class<T>) JSONV3Feed.class;
                break;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntryResource> Class<T> entryClassForFormat(final ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomEntry.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
                result = (Class<T>) JSONV3Entry.class;
                break;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource, K extends FeedResource> T newLinkForFeed(final Class<K> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV3Feed.class.equals(resourceClass)) {
            result = (T) new JSONV3Link();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource, K extends EntryResource> T newLinkForEntry(final Class<K> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (T) new JSONV3Link();
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource, K extends FeedResource> Class<K> feedClassForLink(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomFeed.class;
        }
        if (JSONV3Link.class.equals(resourceClass)) {
            result = (Class<K>) JSONV3Feed.class;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource, K extends EntryResource> Class<T> linkClassForEntry(
            final Class<K> resourceClass) {

        Class<T> result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (Class<T>) AtomLink.class;
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (Class<T>) JSONV3Link.class;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends LinkResource, K extends EntryResource> Class<K> entryClassForLink(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONV3Link.class.equals(resourceClass)) {
            result = (Class<K>) JSONV3Entry.class;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends FeedResource, K extends EntryResource> Class<K> entryClassForFeed(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONV3Feed.class.equals(resourceClass)) {
            result = (Class<K>) JSONV3Entry.class;
        }

        return result;
    }
}
