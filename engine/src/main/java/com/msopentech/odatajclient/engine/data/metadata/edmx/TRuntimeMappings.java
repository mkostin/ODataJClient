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

import com.msopentech.odatajclient.engine.data.metadata.edmx.mapping.cs.TMapping;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TRuntimeMappings complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TRuntimeMappings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}Mapping" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRuntimeMappings", propOrder = {
    "mapping"
})
public class TRuntimeMappings {

    @XmlElement(name = "Mapping", namespace = "http://schemas.microsoft.com/ado/2009/11/mapping/cs")
    protected TMapping mapping;

    /**
     * Gets the value of the mapping property.
     *
     * @return
     * possible object is
     * {@link TMapping }
     *
     */
    public TMapping getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     *
     * @param value
     * allowed object is
     * {@link TMapping }
     *
     */
    public void setMapping(TMapping value) {
        this.mapping = value;
    }
}
