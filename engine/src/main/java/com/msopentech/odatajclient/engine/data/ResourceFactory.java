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

import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.AtomFeed;
import com.msopentech.odatajclient.engine.data.atom.AtomLink;
import com.msopentech.odatajclient.engine.data.json.JSONEntry;
import com.msopentech.odatajclient.engine.data.json.JSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONLink;

public class ResourceFactory {

    @SuppressWarnings("unchecked")
    public static <T extends FeedResource> T newFeed(final Class<T> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomFeed();
        }
        if (JSONFeed.class.equals(resourceClass)) {
            result = (T) new JSONFeed();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T newEntry(final Class<T> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomEntry();
        }
        if (JSONEntry.class.equals(resourceClass)) {
            result = (T) new JSONEntry();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource> T newLink(final Class<T> resourceClass) {
        T result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONLink.class.equals(resourceClass)) {
            result = (T) new JSONLink();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends FeedResource> T newLinkForFeed(final Class<K> resourceClass) {
        T result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONFeed.class.equals(resourceClass)) {
            result = (T) new JSONLink();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> T newLinkForEntry(final Class<K> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONEntry.class.equals(resourceClass)) {
            result = (T) new JSONLink();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends FeedResource> Class<K> feedClassForLink(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomFeed.class;
        }
        if (JSONLink.class.equals(resourceClass)) {
            result = (Class<K>) JSONFeed.class;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntryResource, K extends FeedResource> Class<K> feedClassForEntry(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (Class<K>) AtomFeed.class;
        }
        if (JSONEntry.class.equals(resourceClass)) {
            result = (Class<K>) JSONFeed.class;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> Class<T> linkClassForEntry(
            final Class<K> resourceClass) {

        Class<T> result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (Class<T>) AtomLink.class;
        }
        if (JSONEntry.class.equals(resourceClass)) {
            result = (Class<T>) JSONLink.class;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> Class<K> entryClassForLink(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONLink.class.equals(resourceClass)) {
            result = (Class<K>) JSONEntry.class;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends FeedResource, K extends EntryResource> Class<K> entryClassForFeed(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONFeed.class.equals(resourceClass)) {
            result = (Class<K>) JSONEntry.class;
        }

        return result;
    }
}
