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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for TEntityContainerMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityContainerMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="EntitySetMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEntitySetMapping"/>
 *         &lt;element name="AssociationSetMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAssociationSetMapping"/>
 *         &lt;element name="FunctionImportMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TFunctionImportMapping"/>
 *       &lt;/choice>
 *       &lt;attribute name="CdmEntityContainer" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="StorageEntityContainer" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="GenerateUpdateViews" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntityContainerMapping", propOrder = {
    "entitySetMappingOrAssociationSetMappingOrFunctionImportMapping"
})
public class EntityContainerMapping {

    @XmlElements({
        @XmlElement(name = "FunctionImportMapping", type = FunctionImportMapping.class),
        @XmlElement(name = "EntitySetMapping", type = EntitySetMapping.class),
        @XmlElement(name = "AssociationSetMapping", type = AssociationSetMapping.class)
    })
    protected List<Object> entitySetMappingOrAssociationSetMappingOrFunctionImportMapping;

    @XmlAttribute(name = "CdmEntityContainer", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String cdmEntityContainer;

    @XmlAttribute(name = "StorageEntityContainer", required = true)
    protected String storageEntityContainer;

    @XmlAttribute(name = "GenerateUpdateViews")
    protected Boolean generateUpdateViews;

    /**
     * Gets the value of the entitySetMappingOrAssociationSetMappingOrFunctionImportMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the entitySetMappingOrAssociationSetMappingOrFunctionImportMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntitySetMappingOrAssociationSetMappingOrFunctionImportMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TFunctionImportMapping }
     * {@link TEntitySetMapping }
     * {@link TAssociationSetMapping }
     *
     *
     */
    public List<Object> getEntitySetMappingOrAssociationSetMappingOrFunctionImportMapping() {
        if (entitySetMappingOrAssociationSetMappingOrFunctionImportMapping == null) {
            entitySetMappingOrAssociationSetMappingOrFunctionImportMapping = new ArrayList<Object>();
        }
        return this.entitySetMappingOrAssociationSetMappingOrFunctionImportMapping;
    }

    /**
     * Gets the value of the cdmEntityContainer property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getCdmEntityContainer() {
        return cdmEntityContainer;
    }

    /**
     * Sets the value of the cdmEntityContainer property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setCdmEntityContainer(String value) {
        this.cdmEntityContainer = value;
    }

    /**
     * Gets the value of the storageEntityContainer property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getStorageEntityContainer() {
        return storageEntityContainer;
    }

    /**
     * Sets the value of the storageEntityContainer property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setStorageEntityContainer(String value) {
        this.storageEntityContainer = value;
    }

    /**
     * Gets the value of the generateUpdateViews property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isGenerateUpdateViews() {
        return generateUpdateViews;
    }

    /**
     * Sets the value of the generateUpdateViews property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setGenerateUpdateViews(Boolean value) {
        this.generateUpdateViews = value;
    }
}
