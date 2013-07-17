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
import java.util.List;
import org.w3c.dom.Element;

/**
 * REST resource for an <tt>ODataEntity</tt>.
 *
 * @see ODataEntity
 */
public interface EntryResource {

    /**
     * Gets base URI.
     *
     * @return base URI.
     */
    URI getBaseURI();

    /**
     * Gets entry type.
     *
     * @return entry type.
     */
    String getType();

    /**
     * Sets entry type.
     *
     * @param type entry type.
     */
    void setType(String type);

    /**
     * Gest entry ID.
     *
     * @return entry ID.
     */
    String getId();

    /**
     * Sets entry ID.
     *
     * @param id entry ID.
     */
    void setId(String id);

    /**
     * Gets ETag.
     *
     * @return ETag.
     */
    String getEtag();

    /**
     * Gets entry self link.
     *
     * @return self link.
     */
    LinkResource getSelfLink();

    /**
     * Sets entry self link.
     *
     * @param selfLink self link.
     * @return 'TRUE' in case of success; false otherwise.
     */
    boolean setSelfLink(LinkResource selfLink);

    /**
     * Gets entry edit link.
     *
     * @return edit link.
     */
    LinkResource getEditLink();

    /**
     * Sets entry edit link.
     *
     * @param editLink edit link.
     * @return 'TRUE' in case of success; false otherwise.
     */
    boolean setEditLink(LinkResource editLink);

    /**
     * Gets association links.
     *
     * @return association links.
     */
    List<? extends LinkResource> getAssociationLinks();

    /**
     * Adds an association link.
     *
     * @param associationLink link.
     * @return 'TRUE' in case of success; false otherwise.
     */
    boolean addAssociationLink(LinkResource associationLink);

    /**
     * Sets association links.
     *
     * @param associationLinks links.
     * @return 'TRUE' in case of success; false otherwise.
     */
    void setAssociationLinks(List<LinkResource> associationLinks);

    /**
     * Gets navigation links.
     *
     * @return links.
     */
    List<? extends LinkResource> getNavigationLinks();

    /**
     * Adds a navigation link.
     *
     * @param navigationLink link.
     * @return 'TRUE' in case of success; false otherwise.
     */
    boolean addNavigationLink(LinkResource navigationLink);

    /**
     * Sets navigation links.
     *
     * @param navigationLink links.
     * @return 'TRUE' in case of success; false otherwise.
     */
    void setNavigationLinks(List<LinkResource> navigationLinks);

    /**
     * Gets media entity links.
     *
     * @return links.
     */
    List<? extends LinkResource> getMediaEditLinks();

    /**
     * Adds a media entity link.
     *
     * @param mediaEditLink link.
     * @return 'TRUE' in case of success; false otherwise.
     */
    boolean addMediaEditLink(LinkResource mediaEditLink);

    /**
     * Sets media entity links.
     *
     * @param mediaEditLinks links.
     * @return 'TRUE' in case of success; false otherwise.
     */
    void setMediaEditLinks(List<LinkResource> mediaEditLinks);

    /**
     * Gets operations.
     *
     * @return operations.
     */
    List<ODataOperation> getOperations();

    /**
     * Gets content.
     *
     * @return content.
     */
    Element getContent();

    /**
     * Sets content.
     *
     * @param content content.
     */
    void setContent(Element content);

    /**
     * Gets media entry properties.
     *
     * @return media entry properties.
     */
    Element getMediaEntryProperties();

    /**
     * Sets media entry properties.
     *
     * @param content media entry properties.
     */
    void setMediaEntryProperties(Element content);

    /**
     * Gets media content type.
     *
     * @return media content type.
     */
    String getMediaContentType();

    /**
     * Gets media content resource.
     *
     * @return media content resource.
     */
    String getMediaContentSource();

    /**
     * Set media content.
     *
     * @param mediaContentSource media content source.
     * @param mediaContentType media content type.
     */
    void setMediaContent(final String mediaContentSource, final String mediaContentType);

    /**
     * Checks if the current entry is a media entry.
     *
     * @return 'TRUE' if is a media entry; 'FALSE' otherwise.
     */
    boolean isMediaEntry();
}
