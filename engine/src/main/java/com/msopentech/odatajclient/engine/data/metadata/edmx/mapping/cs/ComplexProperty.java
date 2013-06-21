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
package com.msopentech.odatajclient.engine.data.metadata.edmx.mapping.cs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for TComplexProperty complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TComplexProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="ScalarProperty" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TScalarProperty"/>
 *           &lt;element name="ComplexProperty" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TComplexProperty"/>
 *           &lt;element name="ComplexTypeMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TComplexTypeMapping"/>
 *           &lt;element name="Condition" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TCondition"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="TypeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsPartial" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TComplexProperty", propOrder = {
    "scalarPropertyOrComplexPropertyOrComplexTypeMapping"
})
public class ComplexProperty {

    @XmlElements({
        @XmlElement(name = "Condition", type = Condition.class),
        @XmlElement(name = "ComplexTypeMapping", type = ComplexTypeMapping.class),
        @XmlElement(name = "ScalarProperty", type = ScalarProperty.class),
        @XmlElement(name = "ComplexProperty", type = ComplexProperty.class)
    })
    protected List<Object> scalarPropertyOrComplexPropertyOrComplexTypeMapping;

    @XmlAttribute(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    @XmlAttribute(name = "TypeName")
    protected String typeName;

    @XmlAttribute(name = "IsPartial")
    protected Boolean isPartial;

    /**
     * Gets the value of the scalarPropertyOrComplexPropertyOrComplexTypeMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the scalarPropertyOrComplexPropertyOrComplexTypeMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScalarPropertyOrComplexPropertyOrComplexTypeMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCondition }
     * {@link TComplexTypeMapping }
     * {@link TScalarProperty }
     * {@link TComplexProperty }
     *
     *
     */
    public List<Object> getScalarPropertyOrComplexPropertyOrComplexTypeMapping() {
        if (scalarPropertyOrComplexPropertyOrComplexTypeMapping == null) {
            scalarPropertyOrComplexPropertyOrComplexTypeMapping = new ArrayList<Object>();
        }
        return this.scalarPropertyOrComplexPropertyOrComplexTypeMapping;
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
     * Gets the value of the typeName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets the value of the typeName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setTypeName(String value) {
        this.typeName = value;
    }

    /**
     * Gets the value of the isPartial property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isIsPartial() {
        return isPartial;
    }

    /**
     * Sets the value of the isPartial property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setIsPartial(Boolean value) {
        this.isPartial = value;
    }
}
