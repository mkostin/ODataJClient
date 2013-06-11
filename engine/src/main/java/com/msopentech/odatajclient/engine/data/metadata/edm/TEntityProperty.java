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

import com.msopentech.odatajclient.engine.data.metadata.edm.annotation.TStoreGeneratedPattern;
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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.TAccess;
import org.w3c.dom.Element;

/**
 * <p>Java class for TEntityProperty complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TCommonPropertyAttributes"/>
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2009/02/edm/annotation}StoreGeneratedPattern"/>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntityProperty", propOrder = {
    "documentationOrValueAnnotationOrTypeAnnotation"
})
public class TEntityProperty extends AbstractFaceteable {

    @XmlElementRefs({
        @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Documentation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> documentationOrValueAnnotationOrTypeAnnotation;

    @XmlAttribute(name = "StoreGeneratedPattern", namespace = "http://schemas.microsoft.com/ado/2009/02/edm/annotation")
    protected TStoreGeneratedPattern storeGeneratedPattern;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAttribute(name = "Type", required = true)
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

    @XmlAttribute(name = "ConcurrencyMode")
    protected TConcurrencyMode concurrencyMode;

    @XmlAttribute(name = "SetterAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected TAccess setterAccess;

    @XmlAttribute(name = "GetterAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected TAccess getterAccess;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the documentationOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the documentationOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentationOrValueAnnotationOrTypeAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TDocumentation }{@code >}
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link TValueAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link TTypeAnnotation }{@code >}
     * {@link Element }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (documentationOrValueAnnotationOrTypeAnnotation == null) {
            documentationOrValueAnnotationOrTypeAnnotation = new ArrayList<Object>();
        }
        return this.documentationOrValueAnnotationOrTypeAnnotation;
    }

    /**
     * Gets the value of the storeGeneratedPattern property.
     *
     * @return
     * possible object is
     * {@link TStoreGeneratedPattern }
     *
     */
    public TStoreGeneratedPattern getStoreGeneratedPattern() {
        return storeGeneratedPattern;
    }

    /**
     * Sets the value of the storeGeneratedPattern property.
     *
     * @param value
     * allowed object is
     * {@link TStoreGeneratedPattern }
     *
     */
    public void setStoreGeneratedPattern(TStoreGeneratedPattern value) {
        this.storeGeneratedPattern = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    @Override
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
     * Gets the value of the type property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    @Override
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
    @Override
    public boolean isNullable() {
        if (nullable == null) {
            return true;
        } else {
            return nullable;
        }
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
     * Gets the value of the concurrencyMode property.
     *
     * @return
     * possible object is
     * {@link TConcurrencyMode }
     *
     */
    @Override
    public TConcurrencyMode getConcurrencyMode() {
        return concurrencyMode;
    }

    /**
     * Sets the value of the concurrencyMode property.
     *
     * @param value
     * allowed object is
     * {@link TConcurrencyMode }
     *
     */
    public void setConcurrencyMode(TConcurrencyMode value) {
        this.concurrencyMode = value;
    }

    /**
     * Gets the value of the setterAccess property.
     *
     * @return
     * possible object is
     * {@link TAccess }
     *
     */
    public TAccess getSetterAccess() {
        return setterAccess;
    }

    /**
     * Sets the value of the setterAccess property.
     *
     * @param value
     * allowed object is
     * {@link TAccess }
     *
     */
    public void setSetterAccess(TAccess value) {
        this.setterAccess = value;
    }

    /**
     * Gets the value of the getterAccess property.
     *
     * @return
     * possible object is
     * {@link TAccess }
     *
     */
    public TAccess getGetterAccess() {
        return getterAccess;
    }

    /**
     * Sets the value of the getterAccess property.
     *
     * @param value
     * allowed object is
     * {@link TAccess }
     *
     */
    public void setGetterAccess(TAccess value) {
        this.getterAccess = value;
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
    @Override
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }
}
