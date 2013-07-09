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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.Access;
import com.msopentech.odatajclient.engine.data.metadata.edm.codegeneration.PublicOrInternalAccess;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import org.w3c.dom.Element;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="FunctionImport">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *                     &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                       &lt;element name="ReturnType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportReturnType" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="Parameter" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                     &lt;/choice>
 *                   &lt;/sequence>
 *                   &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportAttributes"/>
 *                   &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="EntitySet">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *                     &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                       &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
 *                       &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                     &lt;/choice>
 *                   &lt;/sequence>
 *                   &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TEntitySetAttributes"/>
 *                   &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="AssociationSet">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
 *                     &lt;element name="End" maxOccurs="2" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/edm}GEmptyElementExtensibility" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;attribute name="Role" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *                             &lt;attribute name="EntitySet" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *                   &lt;attribute name="Association" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TQualifiedName" />
 *                   &lt;anyAttribute processContents='lax' namespace='##other'/>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation"/>
 *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation"/>
 *         &lt;/choice>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute name="Extends" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2006/04/codegeneration}TypeAccess"/>
 *       &lt;attribute ref="{http://schemas.microsoft.com/ado/2009/02/edm/annotation}LazyLoadingEnabled"/>
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
    "documentation",
    "functionImportOrEntitySetOrAssociationSet",
    "any"
})
@XmlRootElement(name = "EntityContainer")
public class EntityContainer extends AbstractAnnotated {

    @XmlElement(name = "Documentation")
    protected Documentation documentation;

    @XmlElements({
        @XmlElement(name = "TypeAnnotation", type = TypeAnnotation.class),
        @XmlElement(name = "EntitySet", type = EntityContainer.EntitySet.class),
        @XmlElement(name = "AssociationSet", type = EntityContainer.AssociationSet.class),
        @XmlElement(name = "FunctionImport", type = EntityContainer.FunctionImport.class),
        @XmlElement(name = "ValueAnnotation", type = ValueAnnotation.class)
    })
    protected List<Object> functionImportOrEntitySetOrAssociationSet;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    @XmlAttribute(name = "Name", required = true)
    protected String name;

    @XmlAttribute(name = "Extends")
    protected String _extends;

    @XmlAttribute(name = "TypeAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
    protected PublicOrInternalAccess typeAccess;

    @XmlAttribute(name = "LazyLoadingEnabled", namespace = "http://schemas.microsoft.com/ado/2009/02/edm/annotation")
    protected Boolean lazyLoadingEnabled;

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
     * Gets the value of the functionImportOrEntitySetOrAssociationSet property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the functionImportOrEntitySetOrAssociationSet property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionImportOrEntitySetOrAssociationSet().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeAnnotation }
     * {@link EntityContainer.EntitySet }
     * {@link EntityContainer.AssociationSet }
     * {@link EntityContainer.FunctionImport }
     * {@link ValueAnnotation }
     *
     *
     */
    @Override
    public List<Object> getValues() {
        if (functionImportOrEntitySetOrAssociationSet == null) {
            functionImportOrEntitySetOrAssociationSet = new ArrayList<Object>();
        }
        return this.functionImportOrEntitySetOrAssociationSet;
    }

    public List<EntitySet> getEntitySets() {
        return getElements(EntitySet.class);
    }

    public EntitySet getEntitySet(final String name) {
        EntitySet result = null;
        for (EntitySet entitySet : getEntitySets()) {
            if (name.equals(entitySet.getName())) {
                result = entitySet;
            }
        }
        return result;
    }

    public List<AssociationSet> getAssociationSets() {
        return getElements(AssociationSet.class);
    }

    public AssociationSet getAssociationSet(final String name) {
        AssociationSet result = null;
        for (AssociationSet associationSet : getAssociationSets()) {
            if (name.equals(associationSet.getName())) {
                result = associationSet;
            }
        }
        return result;
    }

    public List<FunctionImport> getFunctionImports() {
        return getElements(FunctionImport.class);
    }

    public FunctionImport getFunctionImport(final String name) {
        FunctionImport result = null;
        for (FunctionImport functionImport : getFunctionImports()) {
            if (name.equals(functionImport.getName())) {
                result = functionImport;
            }
        }
        return result;
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
     * Gets the value of the extends property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getExtends() {
        return _extends;
    }

    /**
     * Sets the value of the extends property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setExtends(final String value) {
        this._extends = value;
    }

    /**
     * Gets the value of the typeAccess property.
     *
     * @return
     * possible object is
     * {@link PublicOrInternalAccess }
     *
     */
    public PublicOrInternalAccess getTypeAccess() {
        return typeAccess;
    }

    /**
     * Sets the value of the typeAccess property.
     *
     * @param value
     * allowed object is
     * {@link PublicOrInternalAccess }
     *
     */
    public void setTypeAccess(final PublicOrInternalAccess value) {
        this.typeAccess = value;
    }

    /**
     * Gets the value of the lazyLoadingEnabled property.
     *
     * @return
     * possible object is
     * {@link Boolean }
     *
     */
    public Boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    /**
     * Sets the value of the lazyLoadingEnabled property.
     *
     * @param value
     * allowed object is
     * {@link Boolean }
     *
     */
    public void setLazyLoadingEnabled(final Boolean value) {
        this.lazyLoadingEnabled = value;
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

    public boolean isDefaultEntityContainer() {
        final String result = otherAttributes.get(new QName(ODataConstants.NS_METADATA, "IsDefaultEntityContainer"));
        return result == null ? false : Boolean.valueOf(result);
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
     *       &lt;sequence>
     *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
     *         &lt;element name="End" maxOccurs="2" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/edm}GEmptyElementExtensibility" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Role" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
     *                 &lt;attribute name="EntitySet" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="Name" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
     *       &lt;attribute name="Association" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TQualifiedName" />
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
        "documentation",
        "end",
        "any"
    })
    public static class AssociationSet {

        @XmlElement(name = "Documentation")
        protected Documentation documentation;

        @XmlElement(name = "End")
        protected List<EntityContainer.AssociationSet.End> end;

        @XmlAnyElement(lax = true)
        protected List<Object> any;

        @XmlAttribute(name = "Name", required = true)
        protected String name;

        @XmlAttribute(name = "Association", required = true)
        protected String association;

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
         * Gets the value of the end property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a
         * <CODE>set</CODE> method for the end property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEnd().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EntityContainer.AssociationSet.End }
         *
         *
         */
        public List<EntityContainer.AssociationSet.End> getEnd() {
            if (end == null) {
                end = new ArrayList<EntityContainer.AssociationSet.End>();
            }
            return this.end;
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
         *       &lt;sequence>
         *         &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/edm}GEmptyElementExtensibility" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="Role" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
         *       &lt;attribute name="EntitySet" use="required" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "documentation",
            "any"
        })
        public static class End {

            @XmlElement(name = "Documentation")
            protected Documentation documentation;

            @XmlAnyElement(lax = true)
            protected List<Object> any;

            @XmlAttribute(name = "Role")
            protected String role;

            @XmlAttribute(name = "EntitySet", required = true)
            protected String entitySet;

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

            /**
             * Gets the value of the role property.
             *
             * @return
             * possible object is
             * {@link String }
             *
             */
            public String getRole() {
                return role;
            }

            /**
             * Sets the value of the role property.
             *
             * @param value
             * allowed object is
             * {@link String }
             *
             */
            public void setRole(String value) {
                this.role = value;
            }

            /**
             * Gets the value of the entitySet property.
             *
             * @return
             * possible object is
             * {@link String }
             *
             */
            public String getEntitySet() {
                return entitySet;
            }

            /**
             * Sets the value of the entitySet property.
             *
             * @param value
             * allowed object is
             * {@link String }
             *
             */
            public void setEntitySet(String value) {
                this.entitySet = value;
            }
        }
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
     *       &lt;sequence>
     *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TEntitySetAttributes"/>
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
        "documentation",
        "valueAnnotationOrTypeAnnotationOrAny"
    })
    public static class EntitySet extends AbstractAnnotated {

        @XmlElement(name = "Documentation")
        protected Documentation documentation;

        @XmlElementRefs({
            @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class)
        })
        @XmlAnyElement(lax = true)
        protected List<Object> valueAnnotationOrTypeAnnotationOrAny;

        @XmlAttribute(name = "Name", required = true)
        protected String name;

        @XmlAttribute(name = "EntityType", required = true)
        protected String entityType;

        @XmlAttribute(name = "GetterAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
        protected Access getterAccess;

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
         * Gets the value of the valueAnnotationOrTypeAnnotationOrAny property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a
         * <CODE>set</CODE> method for the valueAnnotationOrTypeAnnotationOrAny property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValueAnnotationOrTypeAnnotationOrAny().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * {@link JAXBElement }{@code <}{@link ValueAnnotation }{@code >}
         * {@link JAXBElement }{@code <}{@link TypeAnnotation }{@code >}
         * {@link Element }
         *
         *
         */
        @Override
        public List<Object> getValues() {
            if (valueAnnotationOrTypeAnnotationOrAny == null) {
                valueAnnotationOrTypeAnnotationOrAny = new ArrayList<Object>();
            }
            return this.valueAnnotationOrTypeAnnotationOrAny;
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
         * Gets the value of the getterAccess property.
         *
         * @return
         * possible object is
         * {@link Access }
         *
         */
        public Access getGetterAccess() {
            return getterAccess;
        }

        /**
         * Sets the value of the getterAccess property.
         *
         * @param value
         * allowed object is
         * {@link Access }
         *
         */
        public void setGetterAccess(Access value) {
            this.getterAccess = value;
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

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Documentation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TDocumentation" minOccurs="0"/>
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="ReturnType" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportReturnType" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;element name="Parameter" type="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportParameter" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;element name="ValueAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TValueAnnotation" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;element name="TypeAnnotation" type="{http://schemas.microsoft.com/ado/2009/11/edm}TTypeAnnotation" maxOccurs="unbounded" minOccurs="0"/>
     *           &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *       &lt;attGroup ref="{http://schemas.microsoft.com/ado/2009/11/edm}TFunctionImportAttributes"/>
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
        "documentation",
        "returnTypeOrParameterOrValueAnnotation"
    })
    public static class FunctionImport extends AbstractAnnotated {

        @XmlElement(name = "Documentation")
        protected Documentation documentation;

        @XmlElementRefs({
            @XmlElementRef(name = "ReturnType", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "Parameter", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "TypeAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class),
            @XmlElementRef(name = "ValueAnnotation", namespace = "http://schemas.microsoft.com/ado/2009/11/edm", type =
                    JAXBElement.class)
        })
        @XmlAnyElement(lax = true)
        protected List<Object> returnTypeOrParameterOrValueAnnotation;

        @XmlAttribute(name = "Name", required = true)
        protected String name;

        @XmlAttribute(name = "ReturnType")
        protected String returnType;

        @XmlAttribute(name = "EntitySet")
        protected String entitySet;

        @XmlAttribute(name = "EntitySetPath")
        protected String entitySetPath;

        @XmlAttribute(name = "IsComposable")
        protected Boolean isComposable;

        @XmlAttribute(name = "IsSideEffecting")
        protected Boolean isSideEffecting;

        @XmlAttribute(name = "IsBindable")
        protected Boolean isBindable;

        @XmlAttribute(name = "MethodAccess", namespace = "http://schemas.microsoft.com/ado/2006/04/codegeneration")
        protected Access methodAccess;

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
         * Gets the value of the returnTypeOrParameterOrValueAnnotation property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a
         * <CODE>set</CODE> method for the returnTypeOrParameterOrValueAnnotation property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReturnTypeOrParameterOrValueAnnotation().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link FunctionImportParameter }{@code >}
         * {@link Object }
         * {@link JAXBElement }{@code <}{@link TypeAnnotation }{@code >}
         * {@link JAXBElement }{@code <}{@link ValueAnnotation }{@code >}
         * {@link Element }
         * {@link JAXBElement }{@code <}{@link FunctionImportReturnType }{@code >}
         *
         *
         */
        @Override
        public List<Object> getValues() {
            if (returnTypeOrParameterOrValueAnnotation == null) {
                returnTypeOrParameterOrValueAnnotation = new ArrayList<Object>();
            }
            return this.returnTypeOrParameterOrValueAnnotation;
        }

        public List<FunctionImportParameter> getParameters() {
            return getJAXBElements(FunctionImportParameter.class);
        }

        public String getFunctionImportReturnType() {
            final List<FunctionImportReturnType> returnTypes = getJAXBElements(FunctionImportReturnType.class);
            return returnTypes.isEmpty()
                    ? getReturnType()
                    : returnTypes.get(0).getType();
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
        public void setName(final String value) {
            this.name = value;
        }

        /**
         * Gets the value of the returnType property.
         *
         * @return
         * possible object is
         * {@link String }
         *
         */
        public String getReturnType() {
            return returnType;
        }

        /**
         * Sets the value of the returnType property.
         *
         * @param value
         * allowed object is
         * {@link String }
         *
         */
        public void setReturnType(final String value) {
            this.returnType = value;
        }

        /**
         * Gets the value of the entitySet property.
         *
         * @return
         * possible object is
         * {@link String }
         *
         */
        public String getEntitySet() {
            return entitySet;
        }

        /**
         * Sets the value of the entitySet property.
         *
         * @param value
         * allowed object is
         * {@link String }
         *
         */
        public void setEntitySet(final String value) {
            this.entitySet = value;
        }

        /**
         * Gets the value of the entitySetPath property.
         *
         * @return
         * possible object is
         * {@link String }
         *
         */
        public String getEntitySetPath() {
            return entitySetPath;
        }

        /**
         * Sets the value of the entitySetPath property.
         *
         * @param value
         * allowed object is
         * {@link String }
         *
         */
        public void setEntitySetPath(final String value) {
            this.entitySetPath = value;
        }

        /**
         * Gets the value of the isComposable property.
         *
         * @return
         * possible object is
         * {@link Boolean }
         *
         */
        public boolean isIsComposable() {
            return isComposable == null ? false : isComposable;
        }

        /**
         * Sets the value of the isComposable property.
         *
         * @param value
         * allowed object is
         * {@link Boolean }
         *
         */
        public void setIsComposable(final Boolean value) {
            this.isComposable = value;
        }

        /**
         * Gets the value of the isSideEffecting property.
         *
         * @return
         * possible object is
         * {@link Boolean }
         *
         */
        public Boolean isIsSideEffecting() {
            return isSideEffecting == null ? true : isSideEffecting;
        }

        /**
         * Sets the value of the isSideEffecting property.
         *
         * @param value
         * allowed object is
         * {@link Boolean }
         *
         */
        public void setIsSideEffecting(final Boolean value) {
            this.isSideEffecting = value;
        }

        /**
         * Gets the value of the isBindable property.
         *
         * @return
         * possible object is
         * {@link Boolean }
         *
         */
        public boolean isIsBindable() {
            return isBindable == null ? false : isBindable;
        }

        /**
         * Sets the value of the isBindable property.
         *
         * @param value
         * allowed object is
         * {@link Boolean }
         *
         */
        public void setIsBindable(final Boolean value) {
            this.isBindable = value;
        }

        /**
         * Gets the value of the methodAccess property.
         *
         * @return
         * possible object is
         * {@link Access }
         *
         */
        public Access getMethodAccess() {
            return methodAccess;
        }

        /**
         * Sets the value of the methodAccess property.
         *
         * @param value
         * allowed object is
         * {@link Access }
         *
         */
        public void setMethodAccess(final Access value) {
            this.methodAccess = value;
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

        public boolean isIsAlwaysBindable() {
            final String result = otherAttributes.get(new QName(ODataConstants.NS_METADATA, "IsAlwaysBindable"));
            return result == null ? false : Boolean.valueOf(result);
        }

        public String getHttpMethod() {
            return otherAttributes.get(new QName(ODataConstants.NS_METADATA, "HttpMethod"));
        }
    }
}
