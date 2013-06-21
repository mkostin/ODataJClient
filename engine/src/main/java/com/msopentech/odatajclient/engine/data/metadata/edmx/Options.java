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
 * <p>Java class for TOptions complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DesignerInfoPropertySet" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TDesignerInfoPropertySet" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TOptions", propOrder = {
    "designerInfoPropertySet"
})
public class Options {

    @XmlElement(name = "DesignerInfoPropertySet")
    protected DesignerInfoPropertySet designerInfoPropertySet;

    /**
     * Gets the value of the designerInfoPropertySet property.
     *
     * @return
     * possible object is
     * {@link TDesignerInfoPropertySet }
     *
     */
    public DesignerInfoPropertySet getDesignerInfoPropertySet() {
        return designerInfoPropertySet;
    }

    /**
     * Sets the value of the designerInfoPropertySet property.
     *
     * @param value
     * allowed object is
     * {@link TDesignerInfoPropertySet }
     *
     */
    public void setDesignerInfoPropertySet(DesignerInfoPropertySet value) {
        this.designerInfoPropertySet = value;
    }
}
