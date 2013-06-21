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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for TFunctionImportMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TFunctionImportMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResultMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TFunctionImportMappingResultMapping" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="FunctionName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FunctionImportName" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFunctionImportMapping", propOrder = {
    "resultMapping"
})
public class FunctionImportMapping {

    @XmlElement(name = "ResultMapping")
    protected List<FunctionImportMappingResultMapping> resultMapping;

    @XmlAttribute(name = "FunctionName", required = true)
    protected String functionName;

    @XmlAttribute(name = "FunctionImportName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String functionImportName;

    /**
     * Gets the value of the resultMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the resultMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TFunctionImportMappingResultMapping }
     *
     *
     */
    public List<FunctionImportMappingResultMapping> getResultMapping() {
        if (resultMapping == null) {
            resultMapping = new ArrayList<FunctionImportMappingResultMapping>();
        }
        return this.resultMapping;
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
     * Gets the value of the functionImportName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFunctionImportName() {
        return functionImportName;
    }

    /**
     * Sets the value of the functionImportName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFunctionImportName(String value) {
        this.functionImportName = value;
    }
}
