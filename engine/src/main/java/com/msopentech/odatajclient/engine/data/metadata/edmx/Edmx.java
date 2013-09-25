/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.data.metadata.edmx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TEdmx complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEdmx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;group ref="{http://schemas.microsoft.com/ado/2007/06/edmx}GDesignerFirstGroup"/>
 *         &lt;group ref="{http://schemas.microsoft.com/ado/2007/06/edmx}GDesignerLastGroup"/>
 *         &lt;group ref="{http://schemas.microsoft.com/ado/2007/06/edmx}GDesignerOnly"/>
 *       &lt;/choice>
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEdmx", propOrder = {
    "content"
})
public class Edmx {

    @XmlElementRefs({
        @XmlElementRef(name = "Runtime", namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", type =
                JAXBElement.class),
        @XmlElementRef(name = "Designer", namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", type =
                JAXBElement.class),
        @XmlElementRef(name = "DataServices", namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", type =
                JAXBElement.class)
    })
    protected List<JAXBElement<?>> content;

    @XmlAttribute(name = "Version", required = true)
    protected String version;

    /**
     * Gets the rest of the content model.
     *
     * <p>
     * You are getting this "catch-all" property because of the following reason:
     * The field name "Runtime" is used by two different parts of a schema.
     * <p>
     * To get rid of this property, apply a property customization to one
     * of both of the following declarations to change their names:
     * Gets the value of the content property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the content property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TDataServices }{@code >}
     * {@link JAXBElement }{@code <}{@link TRuntime }{@code >}
     * {@link JAXBElement }{@code <}{@link TDesigner }{@code >}
     *
     *
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }
}
