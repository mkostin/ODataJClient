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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TFunctionImportCondition complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TFunctionImportCondition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ColumnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsNull" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFunctionImportCondition")
public class FunctionImportCondition {

    @XmlAttribute(name = "Value")
    protected String value;

    @XmlAttribute(name = "ColumnName", required = true)
    protected String columnName;

    @XmlAttribute(name = "IsNull")
    protected Boolean isNull;

    /**
     * Gets the value of the value property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the columnName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the value of the columnName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setColumnName(String value) {
        this.columnName = value;
    }

    /**
     * Gets the value of the isNull property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isIsNull() {
        return isNull;
    }

    /**
     * Sets the value of the isNull property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setIsNull(Boolean value) {
        this.isNull = value;
    }
}
