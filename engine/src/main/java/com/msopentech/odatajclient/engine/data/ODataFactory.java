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
 * Entry point for generating OData domain objects.
 *
 * @see ODataEntitySet
 * @see ODataEntity
 * @see ODataProperty
 * @see ODataLink
 */
public final class ODataFactory {

    private ODataFactory() {
        // Empty private constructor for static utility classes
    }

    public static ODataEntitySet newEntitySet() {
        return new ODataEntitySet();
    }

    public static ODataEntitySet newEntitySet(final URI next) {
        return new ODataEntitySet(next);
    }

    public static ODataEntity newEntity(final String name) {
        return new ODataEntity(name);
    }

    public static ODataEntity newEntity(final String name, final URI link) {
        final ODataEntity result = new ODataEntity(name);
        result.setLink(link);
        return result;
    }

    public static ODataInlineEntitySet newInlineEntitySet(final String name, final URI link,
            final ODataEntitySet entitySet) {

        return new ODataInlineEntitySet(link, ODataLinkType.ENTITY_SET_NAVIGATION, name, entitySet);
    }

    public static ODataInlineEntitySet newInlineEntitySet(final String name, final URI baseURI, final String href,
            final ODataEntitySet entitySet) {

        return new ODataInlineEntitySet(baseURI, href, ODataLinkType.ENTITY_SET_NAVIGATION, name, entitySet);
    }

    public static ODataInlineEntity newInlineEntity(final String name, final URI link, final ODataEntity entity) {
        return new ODataInlineEntity(link, ODataLinkType.ENTITY_NAVIGATION, name, entity);
    }

    public static ODataInlineEntity newInlineEntity(final String name, final URI baseURI, final String href,
            final ODataEntity entity) {

        return new ODataInlineEntity(baseURI, href, ODataLinkType.ENTITY_NAVIGATION, name, entity);
    }

    public static ODataLink newEntityNavigationLink(final String name, final URI link) {
        return new ODataLink(link, ODataLinkType.ENTITY_NAVIGATION, name);
    }

    public static ODataLink newEntityNavigationLink(final String name, final URI baseURI, final String href) {
        return new ODataLink(baseURI, href, ODataLinkType.ENTITY_NAVIGATION, name);
    }

    public static ODataLink newFeedNavigationLink(final String name, final URI link) {
        return new ODataLink(link, ODataLinkType.ENTITY_SET_NAVIGATION, name);
    }

    public static ODataLink newFeedNavigationLink(final String name, final URI baseURI, final String href) {
        return new ODataLink(baseURI, href, ODataLinkType.ENTITY_SET_NAVIGATION, name);
    }

    public static ODataLink newAssociationLink(final String name, final URI link) {
        return new ODataLink(link, ODataLinkType.ASSOCIATION, name);
    }

    public static ODataLink newAssociationLink(final String name, final URI baseURI, final String href) {
        return new ODataLink(baseURI, href, ODataLinkType.ASSOCIATION, name);
    }

    public static ODataLink newMediaEditLink(final String name, final URI link) {
        return new ODataLink(link, ODataLinkType.MEDIA_EDIT, name);
    }

    public static ODataLink newMediaEditLink(final String name, final URI baseURI, final String href) {
        return new ODataLink(baseURI, href, ODataLinkType.MEDIA_EDIT, name);
    }

    public static ODataProperty newPrimitiveProperty(final String name, final ODataPrimitiveValue value) {
        return new ODataProperty(name, value);
    }

    public static ODataProperty newComplexProperty(final String name, final ODataComplexValue value) {
        return new ODataProperty(name, value);
    }

    public static ODataProperty newCollectionProperty(final String name, final ODataCollectionValue value) {
        return new ODataProperty(name, value);
    }
}
