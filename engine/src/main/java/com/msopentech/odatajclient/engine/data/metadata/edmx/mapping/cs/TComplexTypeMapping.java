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

/**
 * <p>Java class for TComplexTypeMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TComplexTypeMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="ScalarProperty" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TScalarProperty"/>
 *           &lt;element name="ComplexProperty" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TComplexProperty"/>
 *           &lt;element name="Condition" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TCondition"/>
 *         &lt;/choice>
 *       &lt;/sequence>
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
@XmlType(name = "TComplexTypeMapping", propOrder = {
    "scalarPropertyOrComplexPropertyOrCondition"
})
public class TComplexTypeMapping {

    @XmlElements({
        @XmlElement(name = "ComplexProperty", type = TComplexProperty.class),
        @XmlElement(name = "Condition", type = TCondition.class),
        @XmlElement(name = "ScalarProperty", type = TScalarProperty.class)
    })
    protected List<Object> scalarPropertyOrComplexPropertyOrCondition;

    @XmlAttribute(name = "TypeName")
    protected String typeName;

    @XmlAttribute(name = "IsPartial")
    protected Boolean isPartial;

    /**
     * Gets the value of the scalarPropertyOrComplexPropertyOrCondition property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the scalarPropertyOrComplexPropertyOrCondition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScalarPropertyOrComplexPropertyOrCondition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TComplexProperty }
     * {@link TCondition }
     * {@link TScalarProperty }
     *
     *
     */
    public List<Object> getScalarPropertyOrComplexPropertyOrCondition() {
        if (scalarPropertyOrComplexPropertyOrCondition == null) {
            scalarPropertyOrComplexPropertyOrCondition = new ArrayList<Object>();
        }
        return this.scalarPropertyOrComplexPropertyOrCondition;
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
