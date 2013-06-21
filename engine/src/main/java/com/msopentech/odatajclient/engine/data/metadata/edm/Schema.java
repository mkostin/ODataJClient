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

import com.msopentech.odatajclient.engine.data.AbstractElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

/**
 * <p>Java class for TSchema complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TSchema">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.microsoft.com/ado/2009/11/edm}GSchemaBodyElements" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Namespace" type="{http://schemas.microsoft.com/ado/2009/11/edm}TNamespaceName" />
 *       &lt;attribute name="Alias" type="{http://schemas.microsoft.com/ado/2009/11/edm}TSimpleIdentifier" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TSchema", propOrder = {
    "gSchemaBodyElements",
    "any"
})
public class Schema extends AbstractElement {

    @XmlElements({
        @XmlElement(name = "EntityType", type = EntityType.class),
        @XmlElement(name = "Function", type = Function.class),
        @XmlElement(name = "EnumType", type = EnumType.class),
        @XmlElement(name = "Using", type = Using.class),
        @XmlElement(name = "ValueTerm", type = ValueTerm.class),
        @XmlElement(name = "Association", type = Association.class),
        @XmlElement(name = "EntityContainer", type = EntityContainer.class),
        @XmlElement(name = "Annotations", type = Annotations.class),
        @XmlElement(name = "ComplexType", type = ComplexType.class)
    })
    protected List<Object> gSchemaBodyElements;

    @XmlAnyElement(lax = true)
    protected List<Object> any;

    @XmlAttribute(name = "Namespace")
    protected String namespace;

    @XmlAttribute(name = "Alias")
    protected String alias;

    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the gSchemaBodyElements property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a
     * <CODE>set</CODE> method for the gSchemaBodyElements property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGSchemaBodyElements().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TEntityType }
     * {@link TFunction }
     * {@link TEnumType }
     * {@link TUsing }
     * {@link TValueTerm }
     * {@link TAssociation }
     * {@link TEntityContainer }
     * {@link TAnnotations }
     * {@link TComplexType }
     */
    @Override
    public List<Object> getValues() {
        if (gSchemaBodyElements == null) {
            gSchemaBodyElements = new ArrayList<Object>();
        }
        return this.gSchemaBodyElements;
    }

    public List<Using> getUsings() {
        return getElements(Using.class);
    }

    public List<EnumType> getEnumTypes() {
        return getElements(EnumType.class);
    }

    public List<ComplexType> getComplexTypes() {
        return getElements(ComplexType.class);
    }

    public List<RowType> getRowTypes() {
        return getElements(RowType.class);
    }

    public List<EntityType> getEntityTypes() {
        return getElements(EntityType.class);
    }

    public List<Association> getAssociations() {
        return getElements(Association.class);
    }

    public List<EntityContainer> getEntityContainers() {
        return getElements(EntityContainer.class);
    }

    public List<Annotations> getAnnotations() {
        return getElements(Annotations.class);
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
     * Gets the value of the namespace property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    /**
     * Gets the value of the alias property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setAlias(String value) {
        this.alias = value;
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
