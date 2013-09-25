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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * The Documentation element is used to provide documentation of comments on the contents of the XML file. It is valid
 * under Schema, Type, Index and Relationship elements.
 *
 * <p>Java class for TDocumentation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TDocumentation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Summary" type="{http://schemas.microsoft.com/ado/2009/11/edm}TText" minOccurs="0"/>
 *         &lt;element name="LongDescription" type="{http://schemas.microsoft.com/ado/2009/11/edm}TText" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TDocumentation", propOrder = {
    "summary",
    "longDescription"
})
public class Documentation {

    @XmlElement(name = "Summary")
    protected Text summary;

    @XmlElement(name = "LongDescription")
    protected Text longDescription;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the summary property.
     *
     * @return
     * possible object is
     * {@link TText }
     *
     */
    public Text getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     *
     * @param value
     * allowed object is
     * {@link TText }
     *
     */
    public void setSummary(Text value) {
        this.summary = value;
    }

    /**
     * Gets the value of the longDescription property.
     *
     * @return
     * possible object is
     * {@link TText }
     *
     */
    public Text getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     *
     * @param value
     * allowed object is
     * {@link TText }
     *
     */
    public void setLongDescription(Text value) {
        this.longDescription = value;
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
