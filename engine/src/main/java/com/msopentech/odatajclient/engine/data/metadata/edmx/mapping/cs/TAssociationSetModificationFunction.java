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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TAssociationSetModificationFunction complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TAssociationSetModificationFunction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAssociationSetModificationFunctionMappingPropertyGroup"/>
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
@XmlType(name = "TAssociationSetModificationFunction", propOrder = {
    "endProperty"
})
public class TAssociationSetModificationFunction {

    @XmlElement(name = "EndProperty")
    protected List<TModificationFunctionMappingEndProperty> endProperty;

    @XmlAttribute(name = "FunctionName", required = true)
    protected String functionName;

    @XmlAttribute(name = "RowsAffectedParameter")
    protected String rowsAffectedParameter;

    /**
     * Gets the value of the endProperty property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the endProperty property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TModificationFunctionMappingEndProperty }
     *
     *
     */
    public List<TModificationFunctionMappingEndProperty> getEndProperty() {
        if (endProperty == null) {
            endProperty = new ArrayList<TModificationFunctionMappingEndProperty>();
        }
        return this.endProperty;
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
