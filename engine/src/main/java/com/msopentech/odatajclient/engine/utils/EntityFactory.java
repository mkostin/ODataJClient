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
package com.msopentech.odatajclient.engine.utils;

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataFeed;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataLink.LinkType;
import java.net.URI;
import java.util.List;

public class EntityFactory {

    public static ODataEntity newEntity(final String name) {
        return new EntityImpl(name);
    }

    public static ODataEntity newEntity(final String name, final URI link) {
        return new EntityImpl(name, link);
    }

    public static ODataLink newLink(final String name, final URI link, final LinkType type) {
        return new LinkImpl(name, link, type);
    }

    public static ODataLink newEntityNavigationLink(final String name, final URI link) {
        return new LinkImpl(name, link, LinkType.ENTITY_NAVIGATION);
    }

    public static ODataLink newEntitySetNavigationLink(final String name, final URI link) {
        return new LinkImpl(name, link, LinkType.FEED_NAVIGATION);
    }

    public static ODataLink newAssociationLink(final String name, final URI link) {
        return new LinkImpl(name, link, LinkType.ASSOCIATION);
    }

    public static ODataLink newMediaLink(final String name, final URI link) {
        return new LinkImpl(name, link, LinkType.MEDIA_EDIT);
    }

    private static class FeedImpl extends ODataFeed {

        private static final long serialVersionUID = 1632243717538685102L;

        public FeedImpl(final List<ODataEntity> entries) {
            super();
            this.next = null;
            this.entities.addAll(entries);
        }

        public FeedImpl(final List<ODataEntity> entries, final URI next) {
            super();
            this.next = next;
            this.entities.addAll(entries);
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

    private static class LinkImpl extends ODataLink {

        private static final long serialVersionUID = -2533925527313767001L;

        public LinkImpl(final String name, final URI link, final LinkType type) {
            super(name, link, type);
        }
    }
}
