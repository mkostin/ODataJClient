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
import com.msopentech.odatajclient.engine.data.FeedResource;
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
import org.w3c.dom.Element;

/**
 * <p>Java class for feedType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="feedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{http://www.w3.org/2005/Atom}author"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}category"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}contributor"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}generator"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}icon"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}id"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}link"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}logo"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}rights"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}subtitle"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}title"/>
 *           &lt;element ref="{http://www.w3.org/2005/Atom}updated"/>
 *         &lt;/choice>
 *         &lt;group ref="{http://www.w3.org/2005/Atom}extensionElement"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}entry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.w3.org/2005/Atom}atomCommonAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "feed")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "feedType", propOrder = {
    "items",
    "anyOther",
    "anyLocal",
    "entry"
})
public class AtomFeed extends AbstractAtomElement implements FeedResource {

    @XmlElementRefs({
        @XmlElementRef(name = "logo", namespace = "http://www.w3.org/2005/Atom", type = Logo.class),
        @XmlElementRef(name = "icon", namespace = "http://www.w3.org/2005/Atom", type = Icon.class),
        @XmlElementRef(name = "title", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "rights", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "contributor", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "generator", namespace = "http://www.w3.org/2005/Atom", type = Generator.class),
        @XmlElementRef(name = "subtitle", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class),
        @XmlElementRef(name = "category", namespace = "http://www.w3.org/2005/Atom", type = Category.class),
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

    protected List<AtomEntry> entry;

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
     * {@link Logo }
     * {@link Icon }
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link Generator }
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link Category }
     * {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}
     * {@link Link }
     * {@link Id }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (items == null) {
            items = new ArrayList<Object>();
        }
        return this.items;
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
     * {@inheritDoc }
     */
    @Override
    public Integer getCount() {
        Element count = null;
        for (Object any : getAnyOther()) {
            if (any instanceof Element) {
                final Element anyElem = (Element) any;
                if ("m:count".equals(anyElem.getNodeName())) {
                    count = anyElem;
                }
            }
        }
        return count == null ? null : Integer.valueOf(count.getTextContent());
    }

    /**
     * Gets the value of the entry property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the entry property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntry().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtomEntry }
     *
     *
     */
    @Override
    public List<AtomEntry> getEntries() {
        if (entry == null) {
            entry = new ArrayList<AtomEntry>();
        }
        return this.entry;
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

    /**
     * Sets the value of the base property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setBase(final String value) {
        this.base = value;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public URI getBaseURI() {
        return base == null ? null : URI.create(base);
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
    public void setLang(final String value) {
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

    /**
     * {@inheritDoc }
     */
    @Override
    public void setEntries(final List<EntryResource> entries) {
        getEntries().clear();
        for (EntryResource entryResource : entries) {
            if (entryResource instanceof AtomEntry) {
                getEntries().add((AtomEntry) entryResource);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setNext(final LinkResource next) {
        final AtomLink link = getLinkWithRel(ODataConstants.NEXT_LINK_REL);
        if (link != null) {
            getValues().remove(link);
        }

        if (next instanceof AtomLink) {
            getValues().add(next);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AtomLink getNext() {
        final AtomLink link = getLinkWithRel(ODataConstants.NEXT_LINK_REL);
        return link == null ? null : link;
    }
}
