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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TEntityTypeShape complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TEntityTypeShape">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="EntityType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PointX" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="PointY" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="Width" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="Height" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="IsExpanded" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="FillColor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEntityTypeShape")
public class EntityTypeShape {

    @XmlAttribute(name = "EntityType", required = true)
    protected String entityType;

    @XmlAttribute(name = "PointX")
    protected Double pointX;

    @XmlAttribute(name = "PointY")
    protected Double pointY;

    @XmlAttribute(name = "Width")
    protected Double width;

    @XmlAttribute(name = "Height")
    protected Double height;

    @XmlAttribute(name = "IsExpanded")
    protected Boolean isExpanded;

    @XmlAttribute(name = "FillColor")
    protected String fillColor;

    /**
     * Gets the value of the entityType property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Sets the value of the entityType property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setEntityType(String value) {
        this.entityType = value;
    }

    /**
     * Gets the value of the pointX property.
     *
     * @return
     * possible object is
     * {@link Double }
     *
     */
    public Double getPointX() {
        return pointX;
    }

    /**
     * Sets the value of the pointX property.
     *
     * @param value
     * allowed object is
     * {@link Double }
     *
     */
    public void setPointX(Double value) {
        this.pointX = value;
    }

    /**
     * Gets the value of the pointY property.
     *
     * @return
     * possible object is
     * {@link Double }
     *
     */
    public Double getPointY() {
        return pointY;
    }

    /**
     * Sets the value of the pointY property.
     *
     * @param value
     * allowed object is
     * {@link Double }
     *
     */
    public void setPointY(Double value) {
        this.pointY = value;
    }

    /**
     * Gets the value of the width property.
     *
     * @return
     * possible object is
     * {@link Double }
     *
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     *
     * @param value
     * allowed object is
     * {@link Double }
     *
     */
    public void setWidth(Double value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     *
     * @return
     * possible object is
     * {@link Double }
     *
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     *
     * @param value
     * allowed object is
     * {@link Double }
     *
     */
    public void setHeight(Double value) {
        this.height = value;
    }

    /**
     * Gets the value of the isExpanded property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isIsExpanded() {
        return isExpanded;
    }

    /**
     * Sets the value of the isExpanded property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setIsExpanded(Boolean value) {
        this.isExpanded = value;
    }

    /**
     * Gets the value of the fillColor property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFillColor() {
        return fillColor;
    }

    /**
     * Sets the value of the fillColor property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFillColor(String value) {
        this.fillColor = value;
    }
}
