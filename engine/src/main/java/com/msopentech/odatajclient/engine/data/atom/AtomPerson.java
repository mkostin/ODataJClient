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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

/**
 * <p>Java class for atomPersonConstruct complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="atomPersonConstruct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}name" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}uri" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}email" minOccurs="0"/>
 *         &lt;group ref="{http://www.w3.org/2005/Atom}extensionElement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.w3.org/2005/Atom}atomCommonAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "atomPersonConstruct", propOrder = {
    "name",
    "uri",
    "email",
    "extensionElement"
})
public class AtomPerson {

    protected String name;

    protected String uri;

    protected String email;

    @XmlAnyElement(lax = true)
    protected List<Object> extensionElement;

    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    protected String base;

    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the name property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the uri property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the extensionElement property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the extensionElement property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtensionElement().add(newItem);
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
    public List<Object> getExtensionElement() {
        if (extensionElement == null) {
            extensionElement = new ArrayList<Object>();
        }
        return this.extensionElement;
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
}
