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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TMappingFragment complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TMappingFragment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TPropertyGroup"/>
 *       &lt;attribute name="StoreEntitySet" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MakeColumnsDistinct" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TMappingFragment", propOrder = {
    "complexPropertyOrScalarPropertyOrCondition"
})
public class MappingFragment {

    @XmlElements({
        @XmlElement(name = "ComplexProperty", type = ComplexProperty.class),
        @XmlElement(name = "Condition", type = Condition.class),
        @XmlElement(name = "ScalarProperty", type = ScalarProperty.class)
    })
    protected List<Object> complexPropertyOrScalarPropertyOrCondition;

    @XmlAttribute(name = "StoreEntitySet", required = true)
    protected String storeEntitySet;

    @XmlAttribute(name = "MakeColumnsDistinct")
    protected Boolean makeColumnsDistinct;

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
