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

import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.TAccess;
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
 * <p>Java class for TNavigationProperty complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TNavigationProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute name="Relationship" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TQualifiedName" />
 *       &lt;attribute name="ToRole" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute name="FromRole" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute name="ContainsTarget" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2006/04/codegeneration}GetterAccess"/>
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2006/04/codegeneration}SetterAccess"/>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNavigationProperty", propOrder = {
    "documentation",
    "valueAnnotationOrTypeAnnotationOrAny"
})
public class TNavigationProperty extends AbstractAnnotated {

    @XmlElement(name = "Documentation")
    protected TDocumentation documentation;

    @XmlElementRefs({
        @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class),
        @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                JAXBElement.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> valueAnnotationOrTypeAnnotationOrAny;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAttribute(name = "Relationship", required = true)
    protected String relationship;

    @XmlAttribute(name = "ToRole", required = true)
    protected String toRole;

    @XmlAttribute(name = "FromRole", required = true)
    protected String fromRole;

    @XmlAttribute(name = "ContainsTarget")
    protected Boolean containsTarget;

    @XmlAttribute(name = "GetterAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected TAccess getterAccess;

    @XmlAttribute(name = "SetterAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected TAccess setterAccess;

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
     * Gets the value of the valueAnnotationOrTypeAnnotationOrAny property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the valueAnnotationOrTypeAnnotationOrAny property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueAnnotationOrTypeAnnotationOrAny().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TTypeAnnotation }{@code >}
     * {@link Object }
     * {@link JAXBElement }{@code <}{@link TValueAnnotation }{@code >}
     * {@link Element }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (valueAnnotationOrTypeAnnotationOrAny == null) {
            valueAnnotationOrTypeAnnotationOrAny = new ArrayList<Object>();
        }
        return this.valueAnnotationOrTypeAnnotationOrAny;
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
     * Gets the value of the relationship property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setRelationship(String value) {
        this.relationship = value;
    }

    /**
     * Gets the value of the toRole property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getToRole() {
        return toRole;
    }

    /**
     * Sets the value of the toRole property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setToRole(String value) {
        this.toRole = value;
    }

    /**
     * Gets the value of the fromRole property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFromRole() {
        return fromRole;
    }

    /**
     * Sets the value of the fromRole property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFromRole(String value) {
        this.fromRole = value;
    }

    /**
     * Gets the value of the containsTarget property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isContainsTarget() {
        return containsTarget;
    }

    /**
     * Sets the value of the containsTarget property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setContainsTarget(Boolean value) {
        this.containsTarget = value;
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
