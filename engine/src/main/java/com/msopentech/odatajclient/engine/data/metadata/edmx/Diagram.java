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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TDiagram complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TDiagram">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="EntityTypeShape" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TEntityTypeShape" minOccurs="0"/>
 *           &lt;element name="AssociationConnector" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TAssociationConnector" minOccurs="0"/>
 *           &lt;element name="InheritanceConnector" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TInheritanceConnector" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DiagramId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ZoomLevel" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ShowGrid" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SnapToGrid" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="DisplayType" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TDiagram", propOrder = {
    "entityTypeShapeOrAssociationConnectorOrInheritanceConnector"
})
public class Diagram {

    @XmlElements({
        @XmlElement(name = "InheritanceConnector", type = InheritanceConnector.class),
        @XmlElement(name = "AssociationConnector", type = AssociationConnector.class),
        @XmlElement(name = "EntityTypeShape", type = EntityTypeShape.class)
    })
    protected List<Object> entityTypeShapeOrAssociationConnectorOrInheritanceConnector;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAttribute(name = "DiagramId")
    protected String diagramId;

    @XmlAttribute(name = "ZoomLevel")
    protected Integer zoomLevel;

    @XmlAttribute(name = "ShowGrid")
    protected Boolean showGrid;

    @XmlAttribute(name = "SnapToGrid")
    protected Boolean snapToGrid;

    @XmlAttribute(name = "DisplayType")
    protected Boolean displayType;

    /**
     * Gets the value of the entityTypeShapeOrAssociationConnectorOrInheritanceConnector property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the entityTypeShapeOrAssociationConnectorOrInheritanceConnector property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityTypeShapeOrAssociationConnectorOrInheritanceConnector().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TInheritanceConnector }
     * {@link TAssociationConnector }
     * {@link TEntityTypeShape }
     *
     *
     */
    public List<Object> getEntityTypeShapeOrAssociationConnectorOrInheritanceConnector() {
        if (entityTypeShapeOrAssociationConnectorOrInheritanceConnector == null) {
            entityTypeShapeOrAssociationConnectorOrInheritanceConnector = new ArrayList<Object>();
        }
        return this.entityTypeShapeOrAssociationConnectorOrInheritanceConnector;
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
     * Gets the value of the diagramId property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getDiagramId() {
        return diagramId;
    }

    /**
     * Sets the value of the diagramId property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setDiagramId(String value) {
        this.diagramId = value;
    }

    /**
     * Gets the value of the zoomLevel property.
     *
     * @return
     * possible object is
     * {@link Integer }
     *
     */
    public Integer getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Sets the value of the zoomLevel property.
     *
     * @param value
     * allowed object is
     * {@link Integer }
     *
     */
    public void setZoomLevel(Integer value) {
        this.zoomLevel = value;
    }

    /**
     * Gets the value of the showGrid property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isShowGrid() {
        return showGrid;
    }

    /**
     * Sets the value of the showGrid property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setShowGrid(Boolean value) {
        this.showGrid = value;
    }

    /**
     * Gets the value of the snapToGrid property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isSnapToGrid() {
        return snapToGrid;
    }

    /**
     * Sets the value of the snapToGrid property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setSnapToGrid(Boolean value) {
        this.snapToGrid = value;
    }

    /**
     * Gets the value of the displayType property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isDisplayType() {
        return displayType;
    }

    /**
     * Sets the value of the displayType property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setDisplayType(Boolean value) {
        this.displayType = value;
    }
}
