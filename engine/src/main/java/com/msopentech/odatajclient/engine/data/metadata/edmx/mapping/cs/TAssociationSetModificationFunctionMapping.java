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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TAssociationSetModificationFunctionMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TAssociationSetModificationFunctionMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="DeleteFunction" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAssociationSetModificationFunction" minOccurs="0"/>
 *         &lt;element name="InsertFunction" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAssociationSetModificationFunction" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAssociationSetModificationFunctionMapping", propOrder = {})
public class TAssociationSetModificationFunctionMapping {

    @XmlElement(name = "DeleteFunction")
    protected TAssociationSetModificationFunction deleteFunction;

    @XmlElement(name = "InsertFunction")
    protected TAssociationSetModificationFunction insertFunction;

    /**
     * Gets the value of the deleteFunction property.
     *
     * @return
     * possible object is
     * {@link TAssociationSetModificationFunction }
     *
     */
    public TAssociationSetModificationFunction getDeleteFunction() {
        return deleteFunction;
    }

    /**
     * Sets the value of the deleteFunction property.
     *
     * @param value
     * allowed object is
     * {@link TAssociationSetModificationFunction }
     *
     */
    public void setDeleteFunction(TAssociationSetModificationFunction value) {
        this.deleteFunction = value;
    }

    /**
     * Gets the value of the insertFunction property.
     *
     * @return
     * possible object is
     * {@link TAssociationSetModificationFunction }
     *
     */
    public TAssociationSetModificationFunction getInsertFunction() {
        return insertFunction;
    }

    /**
     * Sets the value of the insertFunction property.
     *
     * @param value
     * allowed object is
     * {@link TAssociationSetModificationFunction }
     *
     */
    public void setInsertFunction(TAssociationSetModificationFunction value) {
        this.insertFunction = value;
    }
}
