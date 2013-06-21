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
 * <p>Java class for TComplexType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Property" type="{http://schemas.microsoft.com/ado/2009/11/edm}TComplexTypeProperty" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAttributes"/>
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
@XmlType(name = "TComplexType", propOrder = {
    "documentation",
    "propertyOrValueAnnotationOrTypeAnnotation"
})
public class ComplexType extends AbstractAnnotated {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElementRefs({
        @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "Property", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> propertyOrValueAnnotationOrTypeAnnotation;

    @XmlAttribute(name = "TypeAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected PublicOrInternalAccess typeAccess;

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
     * Gets the value of the propertyOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the propertyOrValueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyOrValueAnnotationOrTypeAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link TTypeAnnotation }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link TValueAnnotation }{@code >}
     * {@link JAXBElement }{@code <}{@link TComplexTypeProperty }{@code >}
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (propertyOrValueAnnotationOrTypeAnnotation == null) {
            propertyOrValueAnnotationOrTypeAnnotation = new ArrayList<Object>();
        }
        return this.propertyOrValueAnnotationOrTypeAnnotation;
    }

    public List<ComplexTypeProperty> getProperties() {
        return getJAXBElements(ComplexTypeProperty.class);
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
