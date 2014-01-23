/**
 * Copyright Â© Microsoft Open Technologies, Inc.
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

import com.msopentech.odatajclient.engine.format.ODataPubFormat;

public interface ResourceFactory {

    /**
     * Gets a new instance of <code>FeedResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>FeedResource</code> object.
     */
    public <T extends FeedResource> T newFeed(final Class<T> resourceClass);

    /**
     * Gets a new instance of <code>EntryResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>EntryResource</code> object.
     */
    public <T extends EntryResource> T newEntry(final Class<T> resourceClass);

    /**
     * Gets a new instance of <code>LinkResource</code>.
     *
     * @param <T> resource type.
     * @param resourceClass reference class.
     * @return <code>LinkResource</code> object.
     */
    public <T extends LinkResource> T newLink(final Class<T> resourceClass);

    /**
     * Gets feed reference class from the given format.
     *
     * @param <T> resource type.
     * @param format format.
     * @return resource reference class.
     */
    public <T extends FeedResource> Class<T> feedClassForFormat(final ODataPubFormat format);

    /**
     * Gets entry reference class from the given format.
     *
     * @param <T> resource type.
     * @param format format.
     * @return resource reference class.
     */
    public <T extends EntryResource> Class<T> entryClassForFormat(final ODataPubFormat format);

    /**
     * Gets <code>LinkResource</code> object from feed resource.
     *
     * @param <T> link resource type.
     * @param <K> feed resource type.
     * @param resourceClass feed reference class.
     * @return <code>LinkResource</code> object.
     */
    public <T extends LinkResource, K extends FeedResource> T newLinkForFeed(final Class<K> resourceClass);

    /**
     * Gets <code>LinkResource</code> object from entry resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass entry reference class.
     * @return <code>LinkResource</code> object.
     */
    public <T extends LinkResource, K extends EntryResource> T newLinkForEntry(final Class<K> resourceClass);

    /**
     * Gets <code>FeedResource</code> object from link resource.
     *
     * @param <T> link resource type.
     * @param <K> feed resource type.
     * @param resourceClass link reference class.
     * @return <code>FeedResource</code> object.
     */
    public <T extends LinkResource, K extends FeedResource> Class<K> feedClassForLink(final Class<T> resourceClass);

    /**
     * Gets <code>LinkResource</code> object from entry resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass entry reference class.
     * @return <code>LinkResource</code> object.
     */
    public <T extends LinkResource, K extends EntryResource> Class<T> linkClassForEntry(final Class<K> resourceClass);

    /**
     * Gets <code>EntryResource</code> object from link resource.
     *
     * @param <T> link resource type.
     * @param <K> entry resource type.
     * @param resourceClass link reference class.
     * @return <code>EntryResource</code> object.
     */
    public <T extends LinkResource, K extends EntryResource> Class<K> entryClassForLink(final Class<T> resourceClass);

    /**
     * Gets <code>EntryResource</code> object from feed resource.
     *
     * @param <T> feed resource type.
     * @param <K> entry resource type.
     * @param resourceClass feed reference class.
     * @return <code>EntryResource</code> object.
     */
    public <T extends FeedResource, K extends EntryResource> Class<K> entryClassForFeed(final Class<T> resourceClass);
}