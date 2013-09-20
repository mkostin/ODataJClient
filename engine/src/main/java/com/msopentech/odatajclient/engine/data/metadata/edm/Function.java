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

import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

/**
 * <p>Java class for TFunction complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TFunction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Parameter" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionParameter" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="DefiningExpression" type="{http://schemas.microsoft.com/ado/2009/11/edm}TCommandText" minOccurs="0"/>
 *           &lt;element name="ReturnType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionReturnType" minOccurs="0"/>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TFacetAttributes"/>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute name="ReturnType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TWrappedFunctionType" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFunction", propOrder = {
    "documentation",
    "parameterOrDefiningExpressionOrReturnType"
})
public class Function {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElementRefs({
        @XmlElementRef(name = "ReturnType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Parameter", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "DefiningExpression", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> parameterOrDefiningExpressionOrReturnType;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAttribute(name = "ReturnType")
    protected String returnType;

    @XmlAttribute(name = "Nullable")
    protected Boolean nullable;

    @XmlAttribute(name = "DefaultValue")
    protected String defaultValue;

    @XmlAttribute(name = "MaxLength")
    protected String maxLength;

    @XmlAttribute(name = "FixedLength")
    protected Boolean fixedLength;

    @XmlAttribute(name = "Precision")
    protected BigInteger precision;

    @XmlAttribute(name = "Scale")
    protected BigInteger scale;

    @XmlAttribute(name = "Unicode")
    protected Boolean unicode;

    @XmlAttribute(name = "Collation")
    protected String collation;

    @XmlAttribute(name = "SRID")
    protected String srid;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the documentation property.
     *
     * @return
     * possible object is
     * {@link Documentation }
     *
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * Sets the value of the documentation property.
     *
     * @param value
     * allowed object is
     * {@link Documentation }
     *
     */
    public void setDocumentation(Documentation value) {
        this.documentation = value;
    }

    /**
     * Gets the value of the parameterOrDefiningExpressionOrReturnType property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the parameterOrDefiningExpressionOrReturnType property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterOrDefiningExpressionOrReturnType().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link TFunctionReturnType }{@code >}
     * {@link JAXBElement }{@code <}{@link TFunctionParameter }{@code >}
     * {@link JAXBElement }{@code <}{@link TTypeAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link TValueAnnotation }{@code >}
     *
     *
     */
    public List<Object> getParameterOrDefiningExpressionOrReturnType() {
        if (parameterOrDefiningExpressionOrReturnType == null) {
            parameterOrDefiningExpressionOrReturnType = new ArrayList<Object>();
        }
        return this.parameterOrDefiningExpressionOrReturnType;
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
     * Gets the value of the returnType property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets the value of the returnType property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setReturnType(String value) {
        this.returnType = value;
    }

    /**
     * Gets the value of the nullable property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isNullable() {
        return nullable;
    }

    /**
     * Sets the value of the nullable property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setNullable(Boolean value) {
        this.nullable = value;
    }

    /**
     * Gets the value of the defaultValue property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the maxLength property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setMaxLength(String value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the fixedLength property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isFixedLength() {
        return fixedLength;
    }

    /**
     * Sets the value of the fixedLength property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setFixedLength(Boolean value) {
        this.fixedLength = value;
    }

    /**
     * Gets the value of the precision property.
     *
     * @return
     * possible object is
     * {@link BigInteger }
     *
     */
    public BigInteger getPrecision() {
        return precision;
    }

    /**
     * Sets the value of the precision property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger }
     *
     */
    public void setPrecision(BigInteger value) {
        this.precision = value;
    }

    /**
     * Gets the value of the scale property.
     *
     * @return
     * possible object is
     * {@link BigInteger }
     *
     */
    public BigInteger getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger }
     *
     */
    public void setScale(BigInteger value) {
        this.scale = value;
    }

    /**
     * Gets the value of the unicode property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isUnicode() {
        return unicode;
    }

    /**
     * Sets the value of the unicode property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setUnicode(Boolean value) {
        this.unicode = value;
    }

    /**
     * Gets the value of the collation property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getCollation() {
        return collation;
    }

    /**
     * Sets the value of the collation property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setCollation(String value) {
        this.collation = value;
    }

    /**
     * Gets the value of the srid property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getSRID() {
        return srid;
    }

    /**
     * Sets the value of the srid property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setSRID(String value) {
        this.srid = value;
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
