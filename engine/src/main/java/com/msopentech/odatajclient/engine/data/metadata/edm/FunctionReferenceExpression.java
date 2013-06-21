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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

/**
 * <p>Java class for TFunctionReferenceExpression complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TFunctionReferenceExpression">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="Parameter" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice maxOccurs="unbounded">
 *                     &lt;choice minOccurs="0">
 *                       &lt;element name="CollectionType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TCollectionType" minOccurs="0"/>
 *                       &lt;element name="ReferenceType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TReferenceType" minOccurs="0"/>
 *                       &lt;element name="RowType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TRowType" minOccurs="0"/>
 *                     &lt;/choice>
 *                     &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/choice>
 *                   &lt;attribute name="Type" type="{http://schemas.microsoft.com/ado/2009/11/edm}TWrappedFunctionType" />
 *                   &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Function" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TQualifiedName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TFunctionReferenceExpression", propOrder = {
    "documentation",
    "parameterOrAny"
})
public class FunctionReferenceExpression {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElementRef(name = "Parameter", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
            JAXBElement.class)
    @XmlAnyElement(lax = true)
    protected List<Object> parameterOrAny;

    @XmlAttribute(name = "Function", required = true)
    protected String function;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the documentation property.
     *
     * @return
     * possible object is
     * {@link Documentation }
     *
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * Sets the value of the documentation property.
     *
     * @param value
     * allowed object is
     * {@link Documentation }
     *
     */
    public void setDocumentation(Documentation value) {
        this.documentation = value;
    }

    /**
     * Gets the value of the parameterOrAny property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the parameterOrAny property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterOrAny().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TFunctionReferenceExpression.Parameter }{@code >}
     * {@link Object }
     * {@link Element }
     *
     *
     */
    public List<Object> getParameterOrAny() {
        if (parameterOrAny == null) {
            parameterOrAny = new ArrayList<Object>();
        }
        return this.parameterOrAny;
    }

    /**
     * Gets the value of the function property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return
     * always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice maxOccurs="unbounded">
     *         &lt;choice minOccurs="0">
     *           &lt;element name="CollectionType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TCollectionType" minOccurs="0"/>
     *           &lt;element name="ReferenceType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TReferenceType" minOccurs="0"/>
     *           &lt;element name="RowType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TRowType" minOccurs="0"/>
     *         &lt;/choice>
     *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/choice>
     *       &lt;attribute name="Type" type="{http://schemas.microsoft.com/ado/2009/11/edm}TWrappedFunctionType" />
     *       &lt;anyAttribute processContents='lax' namespace='##other'/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "collectionTypeOrReferenceTypeOrRowType"
    })
    public static class Parameter {

        @XmlElementRefs({
            @XmlElementRef(name = "RowType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "CollectionType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "ReferenceType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class)
        })
        @XmlAnyElement(lax = true)
        protected List<Object> collectionTypeOrReferenceTypeOrRowType;

        @XmlAttribute(name = "Type")
        protected String type;

        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the collectionTypeOrReferenceTypeOrRowType property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a
         * <CODE>set</CODE> method for the collectionTypeOrReferenceTypeOrRowType property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCollectionTypeOrReferenceTypeOrRowType().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * {@link Element }
         * {@link JAXBElement }{@code <}{@link TRowType }{@code >}
         * {@link JAXBElement }{@code <}{@link TCollectionType }{@code >}
         * {@link JAXBElement }{@code <}{@link TReferenceType }{@code >}
         *
         *
         */
        public List<Object> getCollectionTypeOrReferenceTypeOrRowType() {
            if (collectionTypeOrReferenceTypeOrRowType == null) {
                collectionTypeOrReferenceTypeOrRowType = new ArrayList<Object>();
            }
            return this.collectionTypeOrReferenceTypeOrRowType;
        }

        /**
         * Gets the value of the type property.
         *
         * @return
         * possible object is
         * {@link String }
         *
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         *
         * @param value
         * allowed object is
         * {@link String }
         *
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this class.
         *
         * <p>
         * the map is keyed by the name of the attribute and
         * the value is the string value of the attribute.
         *
         * the map returned by this method is live, and you can add new attribute
         * by updating the map directly. Because of this design, there's no setter.
         *
         *
         * @return
         * always non-null
         */
        public Map<QName, String> getOtherAttributes() {
            return otherAttributes;
        }
    }
}
