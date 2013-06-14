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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.TPublicOrInternalAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>Java class for TEnumType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEnumType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Member" type="{http://schemas.microsoft.com/ado/2009/11/edm}TEnumTypeMember" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAttributes"/>
 *       &lt;attribute name="IsFlags" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="UnderlyingType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TPropertyType" />
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2006/04/codegeneration}TypeAccess"/>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEnumType", propOrder = {
    "documentation",
    "memberOrValueAnnotationOrTypeAnnotation",
    "any"
})
public class TEnumType extends AbstractAnnotated {

    @XmlElement(name = "Documentation")
    protected TDocumentation documentation;

    @XmlElements({
        @XmlElement(name = "ValueAnnotation", type = TValueAnnotation.class),
        @XmlElement(name = "Member", type = TEnumTypeMember.class),
        @XmlElement(name = "TypeAnnotation", type = TTypeAnnotation.class)
    })
    protected List<Object> memberOrValueAnnotationOrTypeAnnotation;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    @XmlAttribute(name = "IsFlags")
    protected Boolean isFlags;

    @XmlAttribute(name = "UnderlyingType")
    protected String underlyingType;

    @XmlAttribute(name = "TypeAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected TPublicOrInternalAccess typeAccess;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the documentation property.
     *
     * @return
     * possible object is
     * {@link TDocumentation }
     *
     */
    public TDocumentation getDocumentation() {
        return documentation;
    }

    /**
     * Sets the value of the documentation property.
     *
     * @param value
     * allowed object is
     * {@link TDocumentation }
     *
     */
    public void setDocumentation(TDocumentation value) {
        this.documentation = value;
    }

    /**
     * Gets the value of the memberOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the memberOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemberOrValueAnnotationOrTypeAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TValueAnnotation }
     * {@link TEnumTypeMember }
     * {@link TTypeAnnotation }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (memberOrValueAnnotationOrTypeAnnotation == null) {
            memberOrValueAnnotationOrTypeAnnotation = new ArrayList<Object>();
        }
        return this.memberOrValueAnnotationOrTypeAnnotation;
    }

    public List<TEnumTypeMember> getMembers() {
        return getElements(TEnumTypeMember.class);
    }

    /**
     * Gets the value of the any property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the any property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
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
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Gets the value of the isFlags property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isIsFlags() {
        return isFlags;
    }

    /**
     * Sets the value of the isFlags property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setIsFlags(Boolean value) {
        this.isFlags = value;
    }

    /**
     * Gets the value of the underlyingType property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getUnderlyingType() {
        return underlyingType;
    }

    /**
     * Sets the value of the underlyingType property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setUnderlyingType(String value) {
        this.underlyingType = value;
    }

    /**
     * Gets the value of the typeAccess property.
     *
     * @return
     * possible object is
     * {@link TPublicOrInternalAccess }
     *
     */
    public TPublicOrInternalAccess getTypeAccess() {
        return typeAccess;
    }

    /**
     * Sets the value of the typeAccess property.
     *
     * @param value
     * allowed object is
     * {@link TPublicOrInternalAccess }
     *
     */
    public void setTypeAccess(TPublicOrInternalAccess value) {
        this.typeAccess = value;
    }

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
