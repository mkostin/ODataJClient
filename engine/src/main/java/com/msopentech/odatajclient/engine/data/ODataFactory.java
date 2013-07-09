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
        return new EntitySetImpl();
    }

    public static ODataEntitySet newEntitySet(final URI next) {
        return new EntitySetImpl(next);
    }

    public static ODataEntity newEntity(final String name) {
        return new EntityImpl(name);
    }

    public static ODataEntity newEntity(final String name, final URI link) {
        return new EntityImpl(name, link);
    }

    public static ODataLink newInlineEntitySet(final String name, final URI link, final ODataEntitySet entitySet) {
        return new InlineEntitySetImpl(link, ODataLinkType.ENTITY_SET_NAVIGATION, name, entitySet);
    }

    public static ODataLink newInlineEntitySet(final String name, final URI baseURI, final String href,
            final ODataEntitySet entitySet) {

        return new InlineEntitySetImpl(baseURI, href, ODataLinkType.ENTITY_SET_NAVIGATION, name, entitySet);
    }

    public static ODataLink newInlineEntity(final String name, final URI link, final ODataEntity entity) {
        return new InlineEntityImpl(link, ODataLinkType.ENTITY_NAVIGATION, name, entity);
    }

    public static ODataLink newInlineEntity(final String name, final URI baseURI, final String href,
            final ODataEntity entity) {

        return new InlineEntityImpl(baseURI, href, ODataLinkType.ENTITY_NAVIGATION, name, entity);
    }

    public static ODataLink newEntityNavigationLink(final String name, final URI link) {
        return new LinkImpl(link, ODataLinkType.ENTITY_NAVIGATION, name);
    }

    public static ODataLink newEntityNavigationLink(final String name, final URI baseURI, final String href) {
        return new LinkImpl(baseURI, href, ODataLinkType.ENTITY_NAVIGATION, name);
    }

    public static ODataLink newFeedNavigationLink(final String name, final URI link) {
        return new LinkImpl(link, ODataLinkType.ENTITY_SET_NAVIGATION, name);
    }

    public static ODataLink newFeedNavigationLink(final String name, final URI baseURI, final String href) {
        return new LinkImpl(baseURI, href, ODataLinkType.ENTITY_SET_NAVIGATION, name);
    }

    public static ODataLink newAssociationLink(final String name, final URI link) {
        return new LinkImpl(link, ODataLinkType.ASSOCIATION, name);
    }

    public static ODataLink newAssociationLink(final String name, final URI baseURI, final String href) {
        return new LinkImpl(baseURI, href, ODataLinkType.ASSOCIATION, name);
    }

    public static ODataLink newMediaEditLink(final String name, final URI link) {
        return new LinkImpl(link, ODataLinkType.MEDIA_EDIT, name);
    }

    public static ODataLink newMediaEditLink(final String name, final URI baseURI, final String href) {
        return new LinkImpl(baseURI, href, ODataLinkType.MEDIA_EDIT, name);
    }

    public static ODataProperty newPrimitiveProperty(final String name, final ODataPrimitiveValue value) {
        return new PropertyImpl(name, value);
    }

    public static ODataProperty newComplexProperty(final String name, final ODataComplexValue value) {
        return new PropertyImpl(name, value);
    }

    public static ODataProperty newCollectionProperty(final String name, final ODataCollectionValue value) {
        return new PropertyImpl(name, value);
    }

    private static class EntitySetImpl extends ODataEntitySet {

        private static final long serialVersionUID = 1632243717538685102L;

        public EntitySetImpl() {
            super();
        }

        public EntitySetImpl(final URI next) {
            super();
            this.next = next;
        }
    }

    private static class EntityImpl extends ODataEntity {

        private static final long serialVersionUID = 1632243717538685102L;

        public EntityImpl(final String name) {
            super(name);
        }

        public EntityImpl(final String name, final URI link) {
            super(name);
            this.link = link;
        }
    }

    private static class InlineEntitySetImpl extends ODataInlineEntitySet {

        private static final long serialVersionUID = 3193897540346153417L;

        public InlineEntitySetImpl(final URI uri, final ODataLinkType type, final String title,
                final ODataEntitySet entitySet) {

            super(uri, type, title, entitySet);
        }

        public InlineEntitySetImpl(final URI baseURI, final String href, final ODataLinkType type, final String title,
                final ODataEntitySet entitySet) {

            super(baseURI, href, type, title, entitySet);
        }
    }

    private static class InlineEntityImpl extends ODataInlineEntity {

        private static final long serialVersionUID = 3193897540346153417L;

        public InlineEntityImpl(final URI uri, final ODataLinkType type, final String title, final ODataEntity entity) {
            super(uri, type, title, entity);
        }

        public InlineEntityImpl(final URI baseURI, final String href, final ODataLinkType type, final String title,
                final ODataEntity entity) {

            super(baseURI, href, type, title, entity);
        }
    }

    private static class LinkImpl extends ODataLink {

        private static final long serialVersionUID = -2533925527313767001L;

        public LinkImpl(final URI uri, final ODataLinkType type, final String title) {
            super(uri, type, title);
        }

        public LinkImpl(final URI baseURI, final String href, final ODataLinkType type, final String title) {
            super(baseURI, href, type, title);
        }
    }

    private static class PropertyImpl extends ODataProperty {

        private static final long serialVersionUID = -8408352153010787473L;

        public PropertyImpl(final String name, final ODataValue value) {
            super(name, value);
        }
    }
}
