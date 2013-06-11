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
 * <p>Java class for TModificationFunctionMappingAssociationEnd complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TModificationFunctionMappingAssociationEnd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TModificationFunctionMappingAssociationEndPropertyGroup"/>
 *       &lt;attribute name="AssociationSet" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="From" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="To" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TModificationFunctionMappingAssociationEnd", propOrder = {
    "scalarProperty"
})
public class TModificationFunctionMappingAssociationEnd {

    @XmlElement(name = "ScalarProperty")
    protected List<TModificationFunctionMappingScalarProperty> scalarProperty;

    @XmlAttribute(name = "AssociationSet", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String associationSet;

    @XmlAttribute(name = "From", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String from;

    @XmlAttribute(name = "To", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String to;

    /**
     * Gets the value of the scalarProperty property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the scalarProperty property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScalarProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TModificationFunctionMappingScalarProperty }
     *
     *
     */
    public List<TModificationFunctionMappingScalarProperty> getScalarProperty() {
        if (scalarProperty == null) {
            scalarProperty = new ArrayList<TModificationFunctionMappingScalarProperty>();
        }
        return this.scalarProperty;
    }

    /**
     * Gets the value of the associationSet property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getAssociationSet() {
        return associationSet;
    }

    /**
     * Sets the value of the associationSet property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setAssociationSet(String value) {
        this.associationSet = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setTo(String value) {
        this.to = value;
    }
}
