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

    URI getBaseURI();
    
    String getType();

    void setType(String type);

    String getId();

    void setId(String id);

    String getEtag();

    void setEtag(String etag);

    String getSelfLink();

    void setSelfLink(String editLink);

    String getEditLink();

    void setEditLink(String editLink);

    List<? extends LinkResource> getAssociationLinks();

    void setAssociationLinks(List<LinkResource> associationLinks);

    List<? extends LinkResource> getNavigationLinks();

    void setNavigationLinks(List<LinkResource> navigationLinks);

    List<? extends LinkResource> getMediaEditLinks();

    void setMediaEditLinks(List<LinkResource> mediaEditLinks);

    Element getContent();

    void setContent(Element content);
}
