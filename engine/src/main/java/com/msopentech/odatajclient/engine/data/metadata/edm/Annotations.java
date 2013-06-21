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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TAnnotations complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TAnnotations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Target" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TPath" />
 *       &lt;attribute name="Qualifier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAnnotations", propOrder = {
    "valueAnnotationOrTypeAnnotation"
})
public class Annotations {

    @XmlElements({
        @XmlElement(name = "TypeAnnotation", type = TypeAnnotation.class),
        @XmlElement(name = "ValueAnnotation", type = ValueAnnotation.class)
    })
    protected List<Object> valueAnnotationOrTypeAnnotation;

    @XmlAttribute(name = "Target", required = true)
    protected String target;

    @XmlAttribute(name = "Qualifier")
    protected String qualifier;

    /**
     * Gets the value of the valueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the valueAnnotationOrTypeAnnotation property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueAnnotationOrTypeAnnotation().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TTypeAnnotation }
     * {@link TValueAnnotation }
     *
     *
     */
    public List<Object> getValueAnnotationOrTypeAnnotation() {
        if (valueAnnotationOrTypeAnnotation == null) {
            valueAnnotationOrTypeAnnotation = new ArrayList<Object>();
        }
        return this.valueAnnotationOrTypeAnnotation;
    }

    /**
     * Gets the value of the target property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setTarget(String value) {
        this.target = value;
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
}
