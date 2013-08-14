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
package com.msopentech.odatajclient.engine.data.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.LinkResource;
import com.msopentech.odatajclient.engine.data.ODataOperation;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

/**
 * A single entry, represented via JSON.
 */
@JsonSerialize(using = JSONEntrySerializer.class)
@JsonDeserialize(using = JSONEntryDeserializer.class)
public class JSONEntry extends AbstractJSONMetadataObject implements EntryResource {

    private static final long serialVersionUID = -5275365545400797758L;

    private URI metadata;

    private String type;

    private String id;

    private String etag;

    private JSONLink readLink;

    private JSONLink editLink;

    private List<JSONLink> associationLinks;

    private List<JSONLink> navigationLinks;

    private List<JSONLink> mediaEditLinks;

    private List<ODataOperation> operations;

    private Element content;

    private Element mediaEntryProperties;

    private String mediaContentSource;

    private String mediaContentType;

    public JSONEntry() {
        associationLinks = new ArrayList<JSONLink>();
        navigationLinks = new ArrayList<JSONLink>();
        mediaEditLinks = new ArrayList<JSONLink>();
        operations = new ArrayList<ODataOperation>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public URI getMetadata() {
        return metadata;
    }

    /**
     * Sets metadata URI.
     *
     * @param metadata metadata URI.
     */
    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getEtag() {
        return etag;
    }

    /**
     * Sets ETag.
     *
     * @param etag ETag.
     */
    public void setEtag(final String etag) {
        this.etag = etag;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public LinkResource getSelfLink() {
        return readLink;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setSelfLink(final LinkResource readLink) {
        final boolean result = (readLink instanceof JSONLink);
        if (result) {
            this.readLink = (JSONLink) readLink;
        }

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public LinkResource getEditLink() {
        return editLink;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setEditLink(final LinkResource editLink) {
        final boolean result = (editLink instanceof JSONLink);
        if (result) {
            this.editLink = (JSONLink) editLink;
        }

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addAssociationLink(final LinkResource link) {
        return (link instanceof JSONLink) ? associationLinks.add((JSONLink) link) : false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<JSONLink> getAssociationLinks() {
        return associationLinks;
    }

    private void setLinks(final List<JSONLink> links, final List<LinkResource> linkResources) {
        links.clear();
        for (LinkResource link : linkResources) {
            if (link instanceof JSONLink) {
                links.add((JSONLink) link);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addNavigationLink(final LinkResource link) {
        return (link instanceof JSONLink) ? navigationLinks.add((JSONLink) link) : false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setAssociationLinks(final List<LinkResource> associationLinks) {
        setLinks(this.associationLinks, associationLinks);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<JSONLink> getNavigationLinks() {
        return navigationLinks;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addMediaEditLink(final LinkResource link) {
        return (link instanceof JSONLink) ? mediaEditLinks.add((JSONLink) link) : false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setNavigationLinks(final List<LinkResource> navigationLinks) {
        setLinks(this.navigationLinks, navigationLinks);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<JSONLink> getMediaEditLinks() {
        return mediaEditLinks;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setMediaEditLinks(final List<LinkResource> mediaEditLinks) {
        setLinks(this.mediaEditLinks, mediaEditLinks);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ODataOperation> getOperations() {
        return operations;
    }

    /**
     * Adds operation.
     *
     * @param operation operation.
     * @return 'TRUE' in case of success; 'FALSE' otherwise.
     */
    public boolean addOperation(final ODataOperation operation) {
        return this.operations.add(operation);
    }

    /**
     * Sets operations.
     *
     * @param operations operations.
     */
    public void setOperations(final List<ODataOperation> operations) {
        this.operations.clear();
        if (operations != null && !operations.isEmpty()) {
            this.operations.addAll(operations);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Element getContent() {
        return content;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setContent(final Element content) {
        this.content = content;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Element getMediaEntryProperties() {
        return mediaEntryProperties;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setMediaEntryProperties(final Element mediaEntryProperties) {
        this.mediaEntryProperties = mediaEntryProperties;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMediaContentType() {
        return this.mediaContentType;
    }

    /**
     * Sets media content type.
     *
     * @param mediaContentType media content type.
     */
    public void setMediaContentType(final String mediaContentType) {
        this.mediaContentType = mediaContentType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMediaContentSource() {
        return this.mediaContentSource;
    }

    /**
     * Sets media content source.
     *
     * @param mediaContentSource media content source.
     */
    public void setMediaContentSource(final String mediaContentSource) {
        this.mediaContentSource = mediaContentSource;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setMediaContent(final String mediaContentSource, final String mediaContentType) {
        setMediaContentSource(mediaContentSource);
        setMediaContentType(mediaContentType);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isMediaEntry() {
        return StringUtils.isNotBlank(this.mediaContentSource);
    }
}
