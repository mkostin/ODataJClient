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

import com.msopentech.odatajclient.engine.utils.ODataContants;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;

/**
 * OData navigation link.
 */
public abstract class ODataLink extends ODataItem {

    /**
     * Link types.
     */
    public enum LinkType {

        ENTITY_NAVIGATION("application/atom+xml;type=entry"),
        FEED_NAVIGATION("application/atom+xml;type=feed"),
        ASSOCIATION("application/xml"),
        MEDIA_EDIT("MEDIA-EDIT");

        private String type;

        private LinkType(final String type) {
            this.type = type;
        }

        LinkType setType(final String type) {
            this.type = type;
            return this;
        }

        public static LinkType evaluate(final String rel, final String type) {
            if (StringUtils.isNotBlank(rel) && rel.startsWith(ODataContants.MEDIA_EDIT_LINK_REL)) {
                return MEDIA_EDIT.setType(type);
            }

            if (LinkType.ENTITY_NAVIGATION.toString().equals(type)) {
                return ENTITY_NAVIGATION;
            }

            if (LinkType.FEED_NAVIGATION.toString().equals(type)) {
                return FEED_NAVIGATION;
            }

            if (LinkType.ASSOCIATION.toString().equals(type)) {
                return ASSOCIATION;
            }

            LOG.error("Invalid link type {}", type);
            throw new IllegalArgumentException("Invalid string type");
        }

        @Override
        public String toString() {
            return type;
        }
    }

    private static final long serialVersionUID = -3625922586547616628L;

    private final LinkType type;

    /**
     * Constructor.
     *
     * @param name link property name.
     * @param link link value.
     * @param link link type.
     * @see LinkType
     */
    public ODataLink(final String name, final URI link, final LinkType type) {
        super(name);
        this.link = link;
        this.type = type;
    }

    /**
     * Gets link type.
     *
     * @return link type;
     */
    public LinkType getType() {
        return type;
    }
}
