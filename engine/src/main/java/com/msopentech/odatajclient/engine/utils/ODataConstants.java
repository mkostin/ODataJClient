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
package com.msopentech.odatajclient.engine.utils;

import javax.xml.XMLConstants;

/**
 * Constant values related to the OData protocol.
 */
public class ODataConstants {

    public static final String NS_DATASERVICES = "http://schemas.microsoft.com/ado/2007/08/dataservices";

    public static final String NS_METADATA = NS_DATASERVICES + "/metadata";

    public static final String NS_SCHEME = NS_DATASERVICES + "/scheme";

    public static final String NS_GEORSS = "http://www.georss.org/georss";

    public static final String NS_GML = "http://www.opengis.net/gml";

    public static final String XMLNS_DATASERVICES = XMLConstants.XMLNS_ATTRIBUTE + ":d";

    public static final String PREFIX_DATASERVICES = "d:";

    public static final String XMLNS_METADATA = XMLConstants.XMLNS_ATTRIBUTE + ":m";

    public static final String PREFIX_METADATA = "m:";

    public static final String XMLNS_GEORSS = XMLConstants.XMLNS_ATTRIBUTE + ":georss";

    public static final String PREFIX_GEORSS = "georss:";

    public static final String XMLNS_GML = XMLConstants.XMLNS_ATTRIBUTE + ":gml";

    public static final String PREFIX_GML = "gml:";

    public static final String NAVIGATION_LINK_REL = NS_DATASERVICES + "/related/";

    public static final String ASSOCIATION_LINK_REL = NS_DATASERVICES + "/relatedlinks/";

    /**
     * Media edit link rel value.
     */
    public static final String MEDIA_EDIT_LINK_REL = NS_DATASERVICES + "/edit-media/";

    /**
     * Edit link rel value.
     */
    public static final String EDIT_LINK_REL = "edit";

    /**
     * Self link rel value.
     */
    public static final String SELF_LINK_REL = "self";

    public static final String NEXT_LINK_REL = "next";

    public static final String ELEM_PROPERTIES = PREFIX_METADATA + "properties";

    public static final String ELEM_ELEMENT = "element";

    public static final String ELEM_INLINE = PREFIX_METADATA + "inline";

    public static final String ATTR_TYPE = PREFIX_METADATA + "type";

    public static final String ATTR_NULL = PREFIX_METADATA + "null";

    public static final String ATTR_XMLBASE = "xml:base";
    
    public static final String ATTR_HREF = "href";

    public static final String ELEM_SERVICE = "service";

    public static final String ELEM_COLLECTION = "collection";

    public static final String ATTR_ATOM_TITLE = "atom:title";

}
