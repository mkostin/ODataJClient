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
 * <p>Java class for TConnectorPoint complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TConnectorPoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="PointX" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="PointY" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TConnectorPoint")
public class ConnectorPoint {

    @XmlAttribute(name = "PointX", required = true)
    protected double pointX;

    @XmlAttribute(name = "PointY", required = true)
    protected double pointY;

    /**
     * Gets the value of the pointX property.
     *
     */
    public double getPointX() {
        return pointX;
    }

    /**
     * Sets the value of the pointX property.
     *
     */
    public void setPointX(double value) {
        this.pointX = value;
    }

    /**
     * Gets the value of the pointY property.
     *
     */
    public double getPointY() {
        return pointY;
    }

    /**
     * Sets the value of the pointY property.
     *
     */
    public void setPointY(double value) {
        this.pointY = value;
    }
}
