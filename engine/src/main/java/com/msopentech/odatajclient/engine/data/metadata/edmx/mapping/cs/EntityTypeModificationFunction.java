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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TEntityTypeModificationFunction complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityTypeModificationFunction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEntityTypeModificationFunctionMappingPropertyGroup"/>
 *       &lt;attribute name="FunctionName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RowsAffectedParameter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntityTypeModificationFunction", propOrder = {
    "scalarPropertyOrAssociationEndOrComplexProperty"
})
@XmlSeeAlso({
    EntityTypeModificationFunctionWithResult.class
})
public class EntityTypeModificationFunction {

    @XmlElements({
        @XmlElement(name = "ScalarProperty", type = ModificationFunctionMappingScalarProperty.class),
        @XmlElement(name = "AssociationEnd", type = ModificationFunctionMappingAssociationEnd.class),
        @XmlElement(name = "ComplexProperty", type = ModificationFunctionMappingComplexProperty.class)
    })
    protected List<Object> scalarPropertyOrAssociationEndOrComplexProperty;

    @XmlAttribute(name = "FunctionName", required = true)
    protected String functionName;

    @XmlAttribute(name = "RowsAffectedParameter")
    protected String rowsAffectedParameter;

    /**
     * Gets the value of the scalarPropertyOrAssociationEndOrComplexProperty property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the scalarPropertyOrAssociationEndOrComplexProperty property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScalarPropertyOrAssociationEndOrComplexProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TModificationFunctionMappingScalarProperty }
     * {@link TModificationFunctionMappingAssociationEnd }
     * {@link TModificationFunctionMappingComplexProperty }
     *
     *
     */
    public List<Object> getScalarPropertyOrAssociationEndOrComplexProperty() {
        if (scalarPropertyOrAssociationEndOrComplexProperty == null) {
            scalarPropertyOrAssociationEndOrComplexProperty = new ArrayList<Object>();
        }
        return this.scalarPropertyOrAssociationEndOrComplexProperty;
    }

    /**
     * Gets the value of the functionName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the value of the functionName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFunctionName(String value) {
        this.functionName = value;
    }

    /**
     * Gets the value of the rowsAffectedParameter property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getRowsAffectedParameter() {
        return rowsAffectedParameter;
    }

    /**
     * Sets the value of the rowsAffectedParameter property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setRowsAffectedParameter(String value) {
        this.rowsAffectedParameter = value;
    }
}
