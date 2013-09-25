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
package com.msopentech.odatajclient.engine.data.metadata.edm.annotation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for TStoreGeneratedPattern.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TStoreGeneratedPattern">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Identity"/>
 *     &lt;enumeration value="Computed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "TStoreGeneratedPattern", namespace = "http://schemas.microsoft.com/ado/2009/02/edm/annotation")
@XmlEnum
public enum StoreGeneratedPattern {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Identity")
    IDENTITY("Identity"),
    @XmlEnumValue("Computed")
    COMPUTED("Computed");

    private final String value;

    StoreGeneratedPattern(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StoreGeneratedPattern fromValue(String v) {
        for (StoreGeneratedPattern c : StoreGeneratedPattern.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
