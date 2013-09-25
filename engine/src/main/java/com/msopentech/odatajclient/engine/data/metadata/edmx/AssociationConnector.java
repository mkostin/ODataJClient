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
package com.msopentech.odatajclient.engine.data.metadata.edmx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TAssociationConnector complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TAssociationConnector">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConnectorPoint" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TConnectorPoint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Association" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ManuallyRouted" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAssociationConnector", propOrder = {
    "connectorPoint"
})
public class AssociationConnector {

    @XmlElement(name = "ConnectorPoint")
    protected List<ConnectorPoint> connectorPoint;

    @XmlAttribute(name = "Association", required = true)
    protected String association;

    @XmlAttribute(name = "ManuallyRouted")
    protected Boolean manuallyRouted;

    /**
     * Gets the value of the connectorPoint property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the connectorPoint property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnectorPoint().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TConnectorPoint }
     *
     *
     */
    public List<ConnectorPoint> getConnectorPoint() {
        if (connectorPoint == null) {
            connectorPoint = new ArrayList<ConnectorPoint>();
        }
        return this.connectorPoint;
    }

    /**
     * Gets the value of the association property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getAssociation() {
        return association;
    }

    /**
     * Sets the value of the association property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setAssociation(String value) {
        this.association = value;
    }

    /**
     * Gets the value of the manuallyRouted property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isManuallyRouted() {
        return manuallyRouted;
    }

    /**
     * Sets the value of the manuallyRouted property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setManuallyRouted(Boolean value) {
        this.manuallyRouted = value;
    }
}
