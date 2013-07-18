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

import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.PublicOrInternalAccess;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
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
import org.w3c.dom.Element;

/**
 * <p>Java class for TEntityType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/ado/2009/11/edm}TEntityKeyElement" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Property" type="{http://schemas.microsoft.com/ado/2009/11/edm}TEntityProperty" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="NavigationProperty" type="{http://schemas.microsoft.com/ado/2009/11/edm}TNavigationProperty" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TDerivableTypeAttributes"/>
 *       &lt;attribute name="OpenType" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
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
@XmlType(name = "TEntityType", propOrder = {
    "documentation",
    "key",
    "propertyOrNavigationPropertyOrValueAnnotation",
    "any"
})
public class EntityType extends AbstractAnnotated {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElement(name = "Key")
    protected EntityKeyElement key;

    @XmlElements({
        @XmlElement(name = "ValueAnnotation", type = ValueAnnotation.class),
        @XmlElement(name = "TypeAnnotation", type = TypeAnnotation.class),
        @XmlElement(name = "NavigationProperty", type = NavigationProperty.class),
        @XmlElement(name = "Property", type = EntityProperty.class)
    })
    protected List<Object> propertyOrNavigationPropertyOrValueAnnotation;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    @XmlAttribute(name = "OpenType")
    protected Boolean openType;

    @XmlAttribute(name = "TypeAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected PublicOrInternalAccess typeAccess;

    @XmlAttribute(name = "BaseType")
    protected String baseType;

    @XmlAttribute(name = "Abstract")
    protected Boolean _abstract;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

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
     * Gets the value of the key property.
     *
     * @return
     * possible object is
     * {@link TEntityKeyElement }
     *
     */
    public EntityKeyElement getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     *
     * @param value
     * allowed object is
     * {@link TEntityKeyElement }
     *
     */
    public void setKey(EntityKeyElement value) {
        this.key = value;
    }

    /**
     * Gets the value of the propertyOrNavigationPropertyOrValueAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the propertyOrNavigationPropertyOrValueAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyOrNavigationPropertyOrValueAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TValueAnnotation }
     * {@link TTypeAnnotation }
     * {@link TNavigationProperty }
     * {@link TEntityProperty }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (propertyOrNavigationPropertyOrValueAnnotation == null) {
            propertyOrNavigationPropertyOrValueAnnotation = new ArrayList<Object>();
        }
        return this.propertyOrNavigationPropertyOrValueAnnotation;
    }

    /**
     * Gets entity properties.
     *
     * @return properties.
     */
    public List<EntityProperty> getProperties() {
        return getElements(EntityProperty.class);
    }

    /**
     * Gets navigation properties.
     *
     * @return navigation properties.
     */
    public List<NavigationProperty> getNavigationProperties() {
        return getElements(NavigationProperty.class);
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
     * Gets the value of the openType property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public boolean isOpenType() {
        if (openType == null) {
            return false;
        } else {
            return openType;
        }
    }

    /**
     * Sets the value of the openType property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setOpenType(Boolean value) {
        this.openType = value;
    }

    /**
     * Gets the value of the typeAccess property.
     *
     * @return
     * possible object is
     * {@link TPublicOrInternalAccess }
     *
     */
    public PublicOrInternalAccess getTypeAccess() {
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
    public void setTypeAccess(PublicOrInternalAccess value) {
        this.typeAccess = value;
    }

    /**
     * Gets the value of the baseType property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getBaseType() {
        return baseType;
    }

    /**
     * Sets the value of the baseType property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setBaseType(String value) {
        this.baseType = value;
    }

    /**
     * Gets the value of the abstract property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public boolean isAbstract() {
        if (_abstract == null) {
            return false;
        } else {
            return _abstract;
        }
    }

    /**
     * Sets the value of the abstract property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setAbstract(Boolean value) {
        this._abstract = value;
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

    public boolean hasStream() {
        final String result = otherAttributes.get(new QName(ODataConstants.NS_METADATA, "HasStream"));
        return result == null ? false : Boolean.valueOf(result);
    }
}
