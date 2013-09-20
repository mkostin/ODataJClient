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
package com.msopentech.odatajclient.engine.data.xml.error;

import com.msopentech.odatajclient.engine.data.ODataError;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;any minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "code",
    "message",
    "any"
})
@XmlRootElement(name = "error")
public class XMLODataError implements ODataError {

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/>
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Message {

        @XmlValue
        protected String value;

        @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
        protected String lang;

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setValue(final String value) {
            this.value = value;
        }

        /**
         * Gets the value of the lang property.
         *
         * @return
         * possible object is
         * {@link String }
         *
         */
        public String getLang() {
            return lang;
        }

        /**
         * Sets the value of the lang property.
         *
         * @param value
         * allowed object is
         * {@link String }
         *
         */
        public void setLang(final String value) {
            this.lang = value;
        }
    }

    @XmlElement(namespace = ODataConstants.NS_METADATA, required = true)
    protected String code;

    @XmlElement(namespace = ODataConstants.NS_METADATA, required = true)
    protected XMLODataError.Message message;

    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the code property.
     *
     * @return
     * possible object is
     * {@link String }
     *
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     * allowed object is
     * {@link String }
     *
     */
    public void setCode(final String value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     *
     * @return
     * possible object is
     * {@link Error.Message }
     *
     */
    public XMLODataError.Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value
     * allowed object is
     * {@link Error.Message }
     *
     */
    public void setMessage(final XMLODataError.Message value) {
        this.message = value;
    }

    /**
     * Gets the value of the any property.
     *
     * @return
     * possible object is
     * {@link Object }
     *
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     *
     * @param value
     * allowed object is
     * {@link Object }
     *
     */
    public void setAny(final Object value) {
        this.any = value;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMessageLang() {
        return this.message == null ? null : this.message.getLang();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMessageValue() {
        return this.message == null ? null : this.message.getValue();
    }

    private String getElementContent(final String elementName) {
        String result = null;

        if (this.any instanceof Element) {
            final Element innerError = (Element) this.any;
            final NodeList messages = innerError.getElementsByTagName(elementName);
            if (messages != null && messages.getLength() > 0) {
                result = messages.item(0).getTextContent();
            }
        }

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getInnerErrorMessage() {
        return getElementContent("m:message");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getInnerErrorType() {
        return getElementContent("m:type");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getInnerErrorStacktrace() {
        return getElementContent("m:stacktrace");
    }
}
