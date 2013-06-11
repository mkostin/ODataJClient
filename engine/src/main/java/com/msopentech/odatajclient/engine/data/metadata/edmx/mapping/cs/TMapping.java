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
 * <p>Java class for TMapping complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TMapping">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Alias" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TAlias" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EntityContainerMapping" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TEntityContainerMapping"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Space" use="required" type="{http://schemas.microsoft.com/ado/2009/11/mapping/cs}TSpace" fixed="C-S" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TMapping", propOrder = {
    "alias",
    "entityContainerMapping"
})
public class TMapping {

    @XmlElement(name = "Alias")
    protected List<TAlias> alias;

    @XmlElement(name = "EntityContainerMapping", required = true)
    protected TEntityContainerMapping entityContainerMapping;

    @XmlAttribute(name = "Space", required = true)
    protected TSpace space;

    /**
     * Gets the value of the alias property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the alias property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TAlias }
     *
     *
     */
    public List<TAlias> getAlias() {
        if (alias == null) {
            alias = new ArrayList<TAlias>();
        }
        return this.alias;
    }

    /**
     * Gets the value of the entityContainerMapping property.
     *
     * @return
     * possible object is
     * {@link TEntityContainerMapping }
     *
     */
    public TEntityContainerMapping getEntityContainerMapping() {
        return entityContainerMapping;
    }

    /**
     * Sets the value of the entityContainerMapping property.
     *
     * @param value
     * allowed object is
     * {@link TEntityContainerMapping }
     *
     */
    public void setEntityContainerMapping(TEntityContainerMapping value) {
        this.entityContainerMapping = value;
    }

    /**
     * Gets the value of the space property.
     *
     * @return
     * possible object is
     * {@link TSpace }
     *
     */
    public TSpace getSpace() {
        if (space == null) {
            return TSpace.C___S;
        } else {
            return space;
        }
    }

    /**
     * Sets the value of the space property.
     *
     * @param value
     * allowed object is
     * {@link TSpace }
     *
     */
    public void setSpace(TSpace value) {
        this.space = value;
    }
}
