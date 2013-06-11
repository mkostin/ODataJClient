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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for TEntitySetMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntitySetMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="QueryView" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TQueryView" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="EntityTypeMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEntityTypeMapping" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="MappingFragment" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TMappingFragment" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TPropertyGroup"/>
 *       &lt;/choice>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSimpleIdentifier" />
 *       &lt;attribute name="TypeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="StoreEntitySet" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MakeColumnsDistinct" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntitySetMapping", propOrder = {
    "queryView",
    "entityTypeMapping",
    "mappingFragment",
    "complexPropertyOrScalarPropertyOrCondition"
})
public class TEntitySetMapping {

    @XmlElement(name = "QueryView")
    protected List<TQueryView> queryView;

    @XmlElement(name = "EntityTypeMapping")
    protected List<TEntityTypeMapping> entityTypeMapping;

    @XmlElement(name = "MappingFragment")
    protected List<TMappingFragment> mappingFragment;

    @XmlElements({
        @XmlElement(name = "ComplexProperty", type = TComplexProperty.class),
        @XmlElement(name = "Condition", type = TCondition.class),
        @XmlElement(name = "ScalarProperty", type = TScalarProperty.class)
    })
    protected List<Object> complexPropertyOrScalarPropertyOrCondition;

    @XmlAttribute(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    @XmlAttribute(name = "TypeName")
    protected String typeName;

    @XmlAttribute(name = "StoreEntitySet")
    protected String storeEntitySet;

    @XmlAttribute(name = "MakeColumnsDistinct")
    protected Boolean makeColumnsDistinct;

    /**
     * Gets the value of the queryView property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the queryView property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueryView().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TQueryView }
     *
     *
     */
    public List<TQueryView> getQueryView() {
        if (queryView == null) {
            queryView = new ArrayList<TQueryView>();
        }
        return this.queryView;
    }

    /**
     * Gets the value of the entityTypeMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the entityTypeMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityTypeMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TEntityTypeMapping }
     *
     *
     */
    public List<TEntityTypeMapping> getEntityTypeMapping() {
        if (entityTypeMapping == null) {
            entityTypeMapping = new ArrayList<TEntityTypeMapping>();
        }
        return this.entityTypeMapping;
    }

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
     * Gets the value of the complexPropertyOrScalarPropertyOrCondition property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the complexPropertyOrScalarPropertyOrCondition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexPropertyOrScalarPropertyOrCondition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TComplexProperty }
     * {@link TCondition }
     * {@link TScalarProperty }
     *
     *
     */
    public List<Object> getComplexPropertyOrScalarPropertyOrCondition() {
        if (complexPropertyOrScalarPropertyOrCondition == null) {
            complexPropertyOrScalarPropertyOrCondition = new ArrayList<Object>();
        }
        return this.complexPropertyOrScalarPropertyOrCondition;
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

    /**
     * Gets the value of the makeColumnsDistinct property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isMakeColumnsDistinct() {
        return makeColumnsDistinct;
    }

    /**
     * Sets the value of the makeColumnsDistinct property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setMakeColumnsDistinct(Boolean value) {
        this.makeColumnsDistinct = value;
    }
}
