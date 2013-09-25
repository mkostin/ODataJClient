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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for TModificationFunctionMappingScalarProperty complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TModificationFunctionMappingScalarProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ParameterName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="Version" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TVersion" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TModificationFunctionMappingScalarProperty")
public class ModificationFunctionMappingScalarProperty {

    @XmlAttribute(name = "ParameterName", required = true)
    protected String parameterName;

    @XmlAttribute(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    @XmlAttribute(name = "Version")
    protected Version version;

    /**
     * Gets the value of the parameterName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the value of the parameterName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setParameterName(String value) {
        this.parameterName = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     * possible object is
     * {@link TVersion }
     *
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     * allowed object is
     * {@link TVersion }
     *
     */
    public void setVersion(Version value) {
        this.version = value;
    }
}
