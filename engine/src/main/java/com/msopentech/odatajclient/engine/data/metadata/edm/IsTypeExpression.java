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

/**
 * <p>Java class for TIsTypeExpression complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TIsTypeExpression">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/edm}GExpression"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element name="CollectionType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TCollectionType" minOccurs="0"/>
 *             &lt;element name="ReferenceType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TReferenceType" minOccurs="0"/>
 *             &lt;element name="RowType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TRowType" minOccurs="0"/>
 *           &lt;/choice>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TFacetAttributes"/>
 *       &lt;attribute name="Type" type="{http://schemas.microsoft.com/ado/2009/11/edm}TWrappedFunctionType" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIsTypeExpression", propOrder = {
    "documentation",
    "stringOrBinaryOrInt"
})
public class IsTypeExpression {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElementRefs({
        @XmlElementRef(name = "CollectionType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "DateTimeOffset", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "EnumMemberReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "String", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Apply", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "IsType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "EntitySetReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "RowType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "LabeledElementReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm",
                type = JAXBElement.class),
        @XmlElementRef(name = "Collection", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Int", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Decimal", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "AssertType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "DateTime", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Path", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Bool", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "FunctionReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Binary", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "ReferenceType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "LabeledElement", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "ParameterReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "PropertyReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Guid", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "ValueTermReference", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "If", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type = JAXBElement.class),
        @XmlElementRef(name = "Time", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Record", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Float", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Null", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> stringOrBinaryOrInt;

    @XmlAttribute(name = "Type")
    protected String type;

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
     * Gets the value of the stringOrBinaryOrInt property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the stringOrBinaryOrInt property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStringOrBinaryOrInt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TCollectionType }{@code >}
     * {@link JAXBElement }{@code <}{@link TDateTimeOffsetConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TEnumMemberReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TStringConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TApplyExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TIsTypeExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TEntitySetReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TRowType }{@code >}
     * {@link JAXBElement }{@code <}{@link TLabeledElementReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TCollectionExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TIntConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TDecimalConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TAssertTypeExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TDateTimeConstantExpression }{@code >}
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link TPathExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TBoolConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TFunctionReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TBinaryConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TReferenceType }{@code >}
     * {@link JAXBElement }{@code <}{@link TLabeledElement }{@code >}
     * {@link JAXBElement }{@code <}{@link TParameterReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TPropertyReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TGuidConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TValueTermReferenceExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TTimeConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TIfExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TRecordExpression }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link TFloatConstantExpression }{@code >}
     * {@link JAXBElement }{@code <}{@link TNullExpression }{@code >}
     *
     *
     */
    public List<Object> getStringOrBinaryOrInt() {
        if (stringOrBinaryOrInt == null) {
            stringOrBinaryOrInt = new ArrayList<Object>();
        }
        return this.stringOrBinaryOrInt;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
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
