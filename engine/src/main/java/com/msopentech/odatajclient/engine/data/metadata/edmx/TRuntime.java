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
public class TRuntime {

    @XmlElement(name = "StorageModels")
    protected TRuntimeStorageModels storageModels;

    @XmlElement(name = "ConceptualModels")
    protected TRuntimeConceptualModels conceptualModels;

    @XmlElement(name = "Mappings")
    protected TRuntimeMappings mappings;

    /**
     * Gets the value of the storageModels property.
     *
     * @return
     * possible object is
     * {@link TRuntimeStorageModels }
     *
     */
    public TRuntimeStorageModels getStorageModels() {
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
    public void setStorageModels(TRuntimeStorageModels value) {
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
    public TRuntimeConceptualModels getConceptualModels() {
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
    public void setConceptualModels(TRuntimeConceptualModels value) {
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
    public TRuntimeMappings getMappings() {
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
    public void setMappings(TRuntimeMappings value) {
        this.mappings = value;
    }
}
