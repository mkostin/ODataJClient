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
package com.msopentech.odatajclient.engine.data.atom;

import com.msopentech.odatajclient.engine.data.AbstractElement;
import com.msopentech.odatajclient.engine.data.LinkResource;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public abstract class AbstractAtomElement extends AbstractElement {

    public String getId() {
        final List<Id> id = getElements(Id.class);

        return id.isEmpty() ? null : id.get(0).getContent();
    }

    protected AtomText getTextProperty(final String name) {
        final List<AtomText> prop = getJAXBElements(name, AtomText.class);
        return prop.isEmpty() ? null : prop.get(0);
    }

    public String getTitle() {
        final AtomText value = getTextProperty("title");
        return value == null || value.getContent().isEmpty() ? null : value.getContent().get(0).toString();
    }

    public void setTitle(final String title) {
        AtomText value = getTextProperty("title");
        if (value != null) {
            removeJAXBElements(title, AtomText.class);
        }

        value = new AtomText();
        value.getContent().add(title);

        getValues().add(new JAXBElement<AtomText>(new QName("title"), AtomText.class, value));
    }

    public String getUpdated() {
        final AtomText value = getTextProperty("updated");
        return value == null || value.getContent().isEmpty() ? null : value.getContent().get(0).toString();
    }

    protected List<AtomLink> getLinks() {
        return getElements(AtomLink.class);
    }

    protected AtomLink getLinkWithRel(final String rel) {
        AtomLink relLink = null;

        for (AtomLink link : getLinks()) {
            if (rel.equals(link.getRel())) {
                relLink = link;
            }
        }

        return relLink;
    }

    public LinkResource getSelfLink() {
        AtomLink link = getLinkWithRel(ODataConstants.SELF_LINK_REL);
        return link == null ? null : link;
    }
}
