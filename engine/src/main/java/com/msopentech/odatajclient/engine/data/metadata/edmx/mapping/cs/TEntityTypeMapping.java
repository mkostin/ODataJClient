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

/**
 * <p>Java class for TEntityTypeMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityTypeMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MappingFragment" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TMappingFragment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ModificationFunctionMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEntityTypeModificationFunctionMapping" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TypeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntityTypeMapping", propOrder = {
    "mappingFragment",
    "modificationFunctionMapping"
})
public class TEntityTypeMapping {

    @XmlElement(name = "MappingFragment")
    protected List<TMappingFragment> mappingFragment;

    @XmlElement(name = "ModificationFunctionMapping")
    protected TEntityTypeModificationFunctionMapping modificationFunctionMapping;

    @XmlAttribute(name = "TypeName", required = true)
    protected String typeName;

    /**
     * Gets the value of the mappingFragment property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the mappingFragment property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMappingFragment().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TMappingFragment }
     *
     *
     */
    public List<TMappingFragment> getMappingFragment() {
        if (mappingFragment == null) {
            mappingFragment = new ArrayList<TMappingFragment>();
        }
        return this.mappingFragment;
    }

    /**
     * Gets the value of the modificationFunctionMapping property.
     *
     * @return
     * possible object is
     * {@link TEntityTypeModificationFunctionMapping }
     *
     */
    public TEntityTypeModificationFunctionMapping getModificationFunctionMapping() {
        return modificationFunctionMapping;
    }

    /**
     * Sets the value of the modificationFunctionMapping property.
     *
     * @param value
     * allowed object is
     * {@link TEntityTypeModificationFunctionMapping }
     *
     */
    public void setModificationFunctionMapping(TEntityTypeModificationFunctionMapping value) {
        this.modificationFunctionMapping = value;
    }

    /**
     * Gets the value of the typeName property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets the value of the typeName property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setTypeName(String value) {
        this.typeName = value;
    }
}
