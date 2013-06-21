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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

/**
 * <p>Java class for TDesigner complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TDesigner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Connection" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TConnection" minOccurs="0"/>
 *         &lt;element name="Options" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TOptions" minOccurs="0"/>
 *         &lt;element name="Diagrams" type="{http://schemas.microsoft.com/ado/2007/06/edmx}TDiagrams" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TDesigner", propOrder = {
    "connection",
    "options",
    "diagrams",
    "any"
})
public class Designer {

    @XmlElement(name = "Connection")
    protected Connection connection;

    @XmlElement(name = "Options")
    protected Options options;

    @XmlElement(name = "Diagrams")
    protected Diagrams diagrams;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the connection property.
     *
     * @return
     * possible object is
     * {@link TConnection }
     *
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the value of the connection property.
     *
     * @param value
     * allowed object is
     * {@link TConnection }
     *
     */
    public void setConnection(Connection value) {
        this.connection = value;
    }

    /**
     * Gets the value of the options property.
     *
     * @return
     * possible object is
     * {@link TOptions }
     *
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     *
     * @param value
     * allowed object is
     * {@link TOptions }
     *
     */
    public void setOptions(Options value) {
        this.options = value;
    }

    /**
     * Gets the value of the diagrams property.
     *
     * @return
     * possible object is
     * {@link TDiagrams }
     *
     */
    public Diagrams getDiagrams() {
        return diagrams;
    }

    /**
     * Sets the value of the diagrams property.
     *
     * @param value
     * allowed object is
     * {@link TDiagrams }
     *
     */
    public void setDiagrams(Diagrams value) {
        this.diagrams = value;
    }

    /**
     * Gets the value of the any property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the any property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     *
     *
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }
}
