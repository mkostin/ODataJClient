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
package com.msopentech.odatajclient.engine.data.metadata.edmx.mapping.cs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TFunctionImportMappingResultMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TFunctionImportMappingResultMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="EntityTypeMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TFunctionImportEntityTypeMapping" maxOccurs="unbounded"/>
 *         &lt;element name="ComplexTypeMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TFunctionImportComplexTypeMapping"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFunctionImportMappingResultMapping", propOrder = {
    "entityTypeMapping",
    "complexTypeMapping"
})
public class FunctionImportMappingResultMapping {

    @XmlElement(name = "EntityTypeMapping")
    protected List<FunctionImportEntityTypeMapping> entityTypeMapping;

    @XmlElement(name = "ComplexTypeMapping")
    protected FunctionImportComplexTypeMapping complexTypeMapping;

    /**
     * Gets the value of the entityTypeMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the entityTypeMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityTypeMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TFunctionImportEntityTypeMapping }
     *
     *
     */
    public List<FunctionImportEntityTypeMapping> getEntityTypeMapping() {
        if (entityTypeMapping == null) {
            entityTypeMapping = new ArrayList<FunctionImportEntityTypeMapping>();
        }
        return this.entityTypeMapping;
    }

    /**
     * Gets the value of the complexTypeMapping property.
     *
     * @return
     * possible object is
     * {@link TFunctionImportComplexTypeMapping }
     *
     */
    public FunctionImportComplexTypeMapping getComplexTypeMapping() {
        return complexTypeMapping;
    }

    /**
     * Sets the value of the complexTypeMapping property.
     *
     * @param value
     * allowed object is
     * {@link TFunctionImportComplexTypeMapping }
     *
     */
    public void setComplexTypeMapping(FunctionImportComplexTypeMapping value) {
        this.complexTypeMapping = value;
    }
}
