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

import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.LinkResource;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

/**
 * <p>Java class for entryType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="entryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{http://www.w3.org/2005/Atom}author"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}category"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}content"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}contributor"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}id"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}link"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}published"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}rights"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}source"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}summary"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}title"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}updated"/>
 *         &lt;/choice>
 *         &lt;group ref="{http://www.w3.org/2005/Atom}extensionElement"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.w3.org/2005/Atom}atomCommonAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entryType", propOrder = {
    "items",
    "anyOther",
    "anyLocal"
})
public class AtomEntry extends AbstractAtomElement implements EntryResource {

    @XmlElementRefs({
        @XmlElementRef(name = "published", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "summary", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "title", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "content", namespace = "http://www.w3.org/2005/Atom", type = AtomContent.class),
        @XmlElementRef(name = "contributor", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "rights", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "category", namespace = "http://www.w3.org/2005/Atom", type = Category.class),
        @XmlElementRef(name = "source", namespace = "http://www.w3.org/2005/Atom", type = Source.class),
        @XmlElementRef(name = "updated", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "author", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "link", namespace = "http://www.w3.org/2005/Atom", type = AtomLink.class),
        @XmlElementRef(name = "id", namespace = "http://www.w3.org/2005/Atom", type = Id.class)
    })
    protected List<Object> items;

    @XmlAnyElement(lax = true)
    protected List<Object> anyOther;

    @XmlAnyElement(lax = true)
    protected List<Object> anyLocal;

    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    protected String base;

    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the items property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the items property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link AtomDate }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomText }{@code >}
     * {@link AtomContent }
     * {@link JAXBElement }{@code <}{@link AtomText }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomText }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomPerson }{@code >}
     * {@link Source }
     * {@link Category }
     * {@link JAXBElement }{@code <}{@link AtomPerson }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomDate }{@code >}
     * {@link AtomLink }
     * {@link Id }
     */
    @Override
    public List<Object> getValues() {
        if (items == null) {
            items = new ArrayList<Object>();
        }
        return this.items;
    }

    public String getSummary() {
        final AtomText value = getTextProperty("summary");
        return value == null || value.getContent().isEmpty() ? null : value.getContent().get(0).toString();
    }

    private AtomPerson getPersonProperty(final String name) {
        final List<AtomPerson> prop = getJAXBElements(name, AtomPerson.class);
        return prop.isEmpty() ? null : prop.get(0);
    }

    public String getAuthor() {
        final AtomPerson author = getPersonProperty("author");
        return author == null ? null : author.getName();
    }

    @Override
    public String getEditLink() {
        AtomLink link = getLinkWithRel(ODataConstants.EDIT_LINK_REL);
        return link == null ? null : link.getHref();
    }

    private List<AtomLink> getLinksWithRelPrefix(final String relPrefix) {
        List<AtomLink> relLinks = new ArrayList<AtomLink>();

        for (AtomLink link : getLinks()) {
            if (link.getRel().startsWith(relPrefix)) {
                relLinks.add(link);
            }
        }

        return relLinks;
    }

    @Override
    public List<? extends LinkResource> getAssociationLinks() {
        return getLinksWithRelPrefix(ODataConstants.ASSOCIATION_LINK_REL);
    }

    @Override
    public List<? extends LinkResource> getNavigationLinks() {
        return getLinksWithRelPrefix(ODataConstants.NAVIGATION_LINK_REL);
    }

    @Override
    public List<? extends LinkResource> getMediaEditLinks() {
        return getLinksWithRelPrefix(ODataConstants.MEDIA_EDIT_LINK_REL);
    }

    @Override
    public String getType() {
        List<Category> categories = getElements(Category.class);
        return categories.isEmpty() ? null : categories.get(0).getTerm();
    }

    public Source getSource() {
        List<Source> sources = getElements(Source.class);
        return sources.isEmpty() ? null : sources.get(0);
    }

    @Override
    public Element getContent() {
        List<AtomContent> contents = getElements(AtomContent.class);
        return contents.isEmpty() ? null : contents.get(0).getXMLContent();
    }

    @Override
    public Element getMediaEntryProperties() {
        return getAnyOther().isEmpty() ? null : (Element) getAnyOther().get(0);
    }

    /**
     * Gets the value of the anyOther property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the anyOther property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnyOther().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     *
     *
     */
    public List<Object> getAnyOther() {
        if (anyOther == null) {
            anyOther = new ArrayList<Object>();
        }
        return this.anyOther;
    }

    /**
     * Gets the value of the anyLocal property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the anyLocal property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnyLocal().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     *
     *
     */
    public List<Object> getAnyLocal() {
        if (anyLocal == null) {
            anyLocal = new ArrayList<Object>();
        }
        return this.anyLocal;
    }

    /**
     * Gets the value of the base property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getBase() {
        return base;
    }

    @Override
    public URI getBaseURI() {
        return base == null ? null : URI.create(base);
    }

    /**
     * Sets the value of the base property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setBase(String value) {
        this.base = value;
    }

    /**
     * Gets the value of the lang property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return
     * always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    @Override
    public String getEtag() {
        String etag = null;

        QName qname = new QName(ODataConstants.NS_METADATA, "etag", "m");
        if (getOtherAttributes().containsKey(qname)) {
            etag = getOtherAttributes().get(qname);
        }

        return etag;
    }

    @Override
    public void setType(final String type) {
        getValues().removeAll(getElements(Category.class));

        Category category = new Category();
        category.setTerm(type);
        category.setScheme(ODataConstants.NS_SCHEME);
        getValues().add(category);
    }

    @Override
    public void setId(final String uri) {
        getValues().removeAll(getElements(Id.class));

        Id id = new Id();
        id.setContent(uri);
        getValues().add(id);
    }

    @Override
    public void setEtag(String etag) {
    }

    @Override
    public void setSelfLink(String selfLink) {
        AtomLink link = getLinkWithRel(ODataConstants.SELF_LINK_REL);
        if (link != null) {
            getValues().remove(link);
        }

        link = new AtomLink();
        link.setRel(ODataConstants.SELF_LINK_REL);
        link.setTitle(getTitle());
        link.setHref(selfLink);

        getValues().add(link);
    }

    @Override
    public void setEditLink(String editLink) {
        AtomLink link = getLinkWithRel(ODataConstants.EDIT_LINK_REL);
        if (link != null) {
            getValues().remove(link);
        }

        link = new AtomLink();
        link.setRel(ODataConstants.EDIT_LINK_REL);
        link.setTitle(getTitle());
        link.setHref(editLink);

        getValues().add(link);
    }

    private void setLinksWithRelPrefix(final String relPrefix, final List<LinkResource> linkResources) {
        getValues().retainAll(getLinksWithRelPrefix(relPrefix));

        for (LinkResource link : linkResources) {
            if (link instanceof AtomLink) {
                getValues().add((AtomLink) link);
            }
        }
    }

    @Override
    public void setAssociationLinks(List<LinkResource> associationLinks) {
        setLinksWithRelPrefix(ODataConstants.ASSOCIATION_LINK_REL, associationLinks);
    }

    @Override
    public void setNavigationLinks(List<LinkResource> navigationLinks) {
        setLinksWithRelPrefix(ODataConstants.NAVIGATION_LINK_REL, navigationLinks);
    }

    @Override
    public void setMediaEditLinks(List<LinkResource> mediaEditLinks) {
        setLinksWithRelPrefix(ODataConstants.MEDIA_EDIT_LINK_REL, mediaEditLinks);
    }

    @Override
    public void setContent(Element content) {
        AtomContent atomContent = new AtomContent();
        atomContent.setXMLContent(content);
        getValues().add(atomContent);
    }

    @Override
    public void setMediaEntryProperties(Element content) {
        getAnyOther().clear();
        getAnyOther().add(content);
    }

    @Override
    public String getMediaContentType() {
        final List<AtomContent> contents = getElements(AtomContent.class);
        return contents.isEmpty() ? null
                : contents.get(0).type == null || contents.get(0).type.isEmpty() ? null : contents.get(0).type.get(0);
    }

    @Override
    public String getMediaContentSource() {
        final List<AtomContent> contents = getElements(AtomContent.class);
        return contents.isEmpty() || StringUtils.isBlank(contents.get(0).src) ? null : contents.get(0).src;
    }

    @Override
    public void setMediaContent(final String src, final String type) {
        AtomContent atomContent = new AtomContent();
        atomContent.setSrc(src);
        atomContent.getType().add(type);
        getValues().add(atomContent);
    }

    @Override
    public boolean isMediaEntry() {
        final List<AtomContent> contents = getElements(AtomContent.class);
        return !contents.isEmpty() && StringUtils.isNotBlank(contents.get(0).src);
    }
}
