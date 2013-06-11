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
 * <p>Java class for TAssociationSetMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TAssociationSetMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QueryView" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndProperty" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEndProperty" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="Condition" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TCondition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ModificationFunctionMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAssociationSetModificationFunctionMapping" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="TypeName" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TQualifiedName" />
 *       &lt;attribute name="StoreEntitySet" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAssociationSetMapping", propOrder = {
    "queryView",
    "endProperty",
    "condition",
    "modificationFunctionMapping"
})
public class TAssociationSetMapping {

    @XmlElement(name = "QueryView")
    protected String queryView;

    @XmlElement(name = "EndProperty")
    protected List<TEndProperty> endProperty;

    @XmlElement(name = "Condition")
    protected List<TCondition> condition;

    @XmlElement(name = "ModificationFunctionMapping")
    protected TAssociationSetModificationFunctionMapping modificationFunctionMapping;

    @XmlAttribute(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    @XmlAttribute(name = "TypeName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String typeName;

    @XmlAttribute(name = "StoreEntitySet")
    protected String storeEntitySet;

    /**
     * Gets the value of the queryView property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getQueryView() {
        return queryView;
    }

    /**
     * Sets the value of the queryView property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setQueryView(String value) {
        this.queryView = value;
    }

    /**
     * Gets the value of the endProperty property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the endProperty property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TEndProperty }
     *
     *
     */
    public List<TEndProperty> getEndProperty() {
        if (endProperty == null) {
            endProperty = new ArrayList<TEndProperty>();
        }
        return this.endProperty;
    }

    /**
     * Gets the value of the condition property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the condition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCondition }
     *
     *
     */
    public List<TCondition> getCondition() {
        if (condition == null) {
            condition = new ArrayList<TCondition>();
        }
        return this.condition;
    }

    /**
     * Gets the value of the modificationFunctionMapping property.
     *
     * @return
     * possible object is
     * {@link TAssociationSetModificationFunctionMapping }
     *
     */
    public TAssociationSetModificationFunctionMapping getModificationFunctionMapping() {
        return modificationFunctionMapping;
    }

    /**
     * Sets the value of the modificationFunctionMapping property.
     *
     * @param value
     * allowed object is
     * {@link TAssociationSetModificationFunctionMapping }
     *
     */
    public void setModificationFunctionMapping(TAssociationSetModificationFunctionMapping value) {
        this.modificationFunctionMapping = value;
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

    /**
     * Gets the value of the storeEntitySet property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getStoreEntitySet() {
        return storeEntitySet;
    }

    /**
     * Sets the value of the storeEntitySet property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setStoreEntitySet(String value) {
        this.storeEntitySet = value;
    }
}
