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

import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * <p>Java class for TTypeAnnotation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TTypeAnnotation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="PropertyValue" type="{http://schemas.microsoft.com/ado/2009/11/edm}TPropertyValue" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}GInlineExpressions"/>
 *       &lt;attribute name="Term" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TQualifiedName" />
 *       &lt;attribute name="Qualifier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TTypeAnnotation", propOrder = {
    "documentation",
    "propertyValueOrAny"
})
public class TypeAnnotation {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElementRef(name = "PropertyValue", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
            JAXBElement.class)
    @XmlAnyElement(lax = true)
    protected List<Object> propertyValueOrAny;

    @XmlAttribute(name = "Term", required = true)
    protected String term;

    @XmlAttribute(name = "Qualifier")
    protected String qualifier;

    @XmlAttribute(name = "String")
    protected String string;

    @XmlAttribute(name = "Binary")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] binary;

    @XmlAttribute(name = "Int")
    protected BigInteger _int;

    @XmlAttribute(name = "Float")
    protected Double _float;

    @XmlAttribute(name = "Guid")
    protected String guid;

    @XmlAttribute(name = "Decimal")
    protected BigDecimal decimal;

    @XmlAttribute(name = "Bool")
    protected Boolean bool;

    @XmlAttribute(name = "Time")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time;

    @XmlAttribute(name = "DateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;

    @XmlAttribute(name = "DateTimeOffset")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTimeOffset;

    @XmlAttribute(name = "Path")
    protected String path;

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
     * Gets the value of the propertyValueOrAny property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the propertyValueOrAny property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyValueOrAny().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TPropertyValue }{@code >}
     * {@link Object }
     * {@link Element }
     *
     *
     */
    public List<Object> getPropertyValueOrAny() {
        if (propertyValueOrAny == null) {
            propertyValueOrAny = new ArrayList<Object>();
        }
        return this.propertyValueOrAny;
    }

    /**
     * Gets the value of the term property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setTerm(String value) {
        this.term = value;
    }

    /**
     * Gets the value of the qualifier property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * Sets the value of the qualifier property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setQualifier(String value) {
        this.qualifier = value;
    }

    /**
     * Gets the value of the string property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the value of the string property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setString(String value) {
        this.string = value;
    }

    /**
     * Gets the value of the binary property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public byte[] getBinary() {
        return binary;
    }

    /**
     * Sets the value of the binary property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setBinary(byte[] value) {
        this.binary = ((byte[]) value);
    }

    /**
     * Gets the value of the int property.
     *
     * @return
     * possible object is
     * {@link BigInteger }
     *
     */
    public BigInteger getInt() {
        return _int;
    }

    /**
     * Sets the value of the int property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger }
     *
     */
    public void setInt(BigInteger value) {
        this._int = value;
    }

    /**
     * Gets the value of the float property.
     *
     * @return
     * possible object is
     * {@link Double }
     *
     */
    public Double getFloat() {
        return _float;
    }

    /**
     * Sets the value of the float property.
     *
     * @param value
     * allowed object is
     * {@link Double }
     *
     */
    public void setFloat(Double value) {
        this._float = value;
    }

    /**
     * Gets the value of the guid property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the decimal property.
     *
     * @return
     * possible object is
     * {@link BigDecimal }
     *
     */
    public BigDecimal getDecimal() {
        return decimal;
    }

    /**
     * Sets the value of the decimal property.
     *
     * @param value
     * allowed object is
     * {@link BigDecimal }
     *
     */
    public void setDecimal(BigDecimal value) {
        this.decimal = value;
    }

    /**
     * Gets the value of the bool property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isBool() {
        return bool;
    }

    /**
     * Sets the value of the bool property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setBool(Boolean value) {
        this.bool = value;
    }

    /**
     * Gets the value of the time property.
     *
     * @return
     * possible object is
     * {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value
     * allowed object is
     * {@link XMLGregorianCalendar }
     *
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the dateTime property.
     *
     * @return
     * possible object is
     * {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     *
     * @param value
     * allowed object is
     * {@link XMLGregorianCalendar }
     *
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the dateTimeOffset property.
     *
     * @return
     * possible object is
     * {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDateTimeOffset() {
        return dateTimeOffset;
    }

    /**
     * Sets the value of the dateTimeOffset property.
     *
     * @param value
     * allowed object is
     * {@link XMLGregorianCalendar }
     *
     */
    public void setDateTimeOffset(XMLGregorianCalendar value) {
        this.dateTimeOffset = value;
    }

    /**
     * Gets the value of the path property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setPath(String value) {
        this.path = value;
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
