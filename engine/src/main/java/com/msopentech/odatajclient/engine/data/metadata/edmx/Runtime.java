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
package com.msopentech.odatajclient.engine.data.metadata.edmx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TRuntime complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TRuntime">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="StorageModels" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TRuntimeStorageModels" minOccurs="0"/>
 *         &lt;element name="ConceptualModels" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TRuntimeConceptualModels" minOccurs="0"/>
 *         &lt;element name="Mappings" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TRuntimeMappings" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRuntime", propOrder = {})
public class Runtime {

    @XmlElement(name = "StorageModels")
    protected RuntimeStorageModels storageModels;

    @XmlElement(name = "ConceptualModels")
    protected RuntimeConceptualModels conceptualModels;

    @XmlElement(name = "Mappings")
    protected RuntimeMappings mappings;

    /**
     * Gets the value of the storageModels property.
     *
     * @return
     * possible object is
     * {@link TRuntimeStorageModels }
     *
     */
    public RuntimeStorageModels getStorageModels() {
        return storageModels;
    }

    /**
     * Sets the value of the storageModels property.
     *
     * @param value
     * allowed object is
     * {@link TRuntimeStorageModels }
     *
     */
    public void setStorageModels(RuntimeStorageModels value) {
        this.storageModels = value;
    }

    /**
     * Gets the value of the conceptualModels property.
     *
     * @return
     * possible object is
     * {@link TRuntimeConceptualModels }
     *
     */
    public RuntimeConceptualModels getConceptualModels() {
        return conceptualModels;
    }

    /**
     * Sets the value of the conceptualModels property.
     *
     * @param value
     * allowed object is
     * {@link TRuntimeConceptualModels }
     *
     */
    public void setConceptualModels(RuntimeConceptualModels value) {
        this.conceptualModels = value;
    }

    /**
     * Gets the value of the mappings property.
     *
     * @return
     * possible object is
     * {@link TRuntimeMappings }
     *
     */
    public RuntimeMappings getMappings() {
        return mappings;
    }

    /**
     * Sets the value of the mappings property.
     *
     * @param value
     * allowed object is
     * {@link TRuntimeMappings }
     *
     */
    public void setMappings(RuntimeMappings value) {
        this.mappings = value;
    }
}
