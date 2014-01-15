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
import com.msopentech.odatajclient.engine.data.json.JSONFeed;
import com.msopentech.odatajclient.engine.data.json.JSONLink;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;

public class ResourceFactory {

    /**
     * Gets a new instance of
     * <code>FeedResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>FeedResource</code> object.
     */
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

    /**
     * Gets a new instance of
     * <code>EntryResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>EntryResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> T newEntry(final Class<T> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomEntry();
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (T) new JSONV3Entry();
        }

        return result;
    }

    /**
     * Gets a new instance of
     * <code>LinkResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>LinkResource</code> object.
     */
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

    /**
     * Gets feed reference class from the given format.
     *
     * @param <T> resource type.
     * @param format format.
     * @return resource reference class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends FeedResource> Class<T> feedClassForFormat(final ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomFeed.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
            case JSON_VERBOSE_METADATA:
                result = (Class<T>) JSONFeed.class;
                break;
        }

        return result;
    }

    /**
     * Gets entry reference class from the given format.
     *
     * @param <T> resource type.
     * @param format format.
     * @return resource reference class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntryResource> Class<T> entryClassForFormat(final ODataPubFormat format) {
        Class<T> result = null;

        switch (format) {
            case ATOM:
                result = (Class<T>) AtomEntry.class;
                break;

            case JSON:
            case JSON_FULL_METADATA:
            case JSON_NO_METADATA:
            case JSON_VERBOSE_METADATA:
                result = (Class<T>) JSONV3Entry.class;
                break;
        }

        return result;
    }

    /**
     * Gets
     * <code>LinkResource</code> object from feed resource.
     *
     * @param <T> link resource type.
     * @param <K> feed resource type.
     * @param resourceClass feed reference class.
     * @return <code>LinkResource</code> object.
     */
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

    /**
     * Gets
     * <code>LinkResource</code> object from entry resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass entry reference class.
     * @return <code>LinkResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> T newLinkForEntry(final Class<K> resourceClass) {
        T result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (T) new AtomLink();
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (T) new JSONLink();
        }

        return result;
    }

    /**
     * Gets
     * <code>FeedResource</code> object from link resource.
     *
     * @param <T> link resource type.
     * @param <K> feed resource type.
     * @param resourceClass link reference class.
     * @return <code>FeedResource</code> object.
     */
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

    /**
     * Gets
     * <code>LinkResource</code> object from entry resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass entry reference class.
     * @return <code>LinkResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> Class<T> linkClassForEntry(
            final Class<K> resourceClass) {

        Class<T> result = null;

        if (AtomEntry.class.equals(resourceClass)) {
            result = (Class<T>) AtomLink.class;
        }
        if (JSONV3Entry.class.equals(resourceClass)) {
            result = (Class<T>) JSONLink.class;
        }

        return result;
    }

    /**
     * Gets
     * <code>EntryResource</code> object from link resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass link reference class.
     * @return <code>EntryResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends LinkResource, K extends EntryResource> Class<K> entryClassForLink(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomLink.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONLink.class.equals(resourceClass)) {
            result = (Class<K>) JSONV3Entry.class;
        }

        return result;
    }

    /**
     * Gets
     * <code>EntryResource</code> object from feed resource.
     *
     * @param <T> feed resource type.
     * @param <K> entry resource type.
     * @param resourceClass feed reference class.
     * @return <code>EntryResource</code> object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends FeedResource, K extends EntryResource> Class<K> entryClassForFeed(
            final Class<T> resourceClass) {

        Class<K> result = null;

        if (AtomFeed.class.equals(resourceClass)) {
            result = (Class<K>) AtomEntry.class;
        }
        if (JSONFeed.class.equals(resourceClass)) {
            result = (Class<K>) JSONV3Entry.class;
        }

        return result;
    }
}
