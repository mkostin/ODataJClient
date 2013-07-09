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

    public static final String ATTR_METADATA = "metadata";

    public static final String ATTR_TITLE = "title";

    public static final String ATTR_TARGET = "target";

    public static final String ELEM_SERVICE = "service";

    public static final String ELEM_COLLECTION = "collection";

    public static final String ATTR_ATOM_TITLE = "atom:title";

    public static final String ELEM_POINT = PREFIX_GML + "Point";

    public static final String ELEM_POS = PREFIX_GML + "pos";

    public static final String ELEM_PROPERTY = "property";

    public static final String ELEM_URI = "uri";

    public static final String ELEM_ACTION = PREFIX_METADATA + "action";

    public static final String ELEM_FUNCTION = PREFIX_METADATA + "function";

    public final static String JSON_METADATA = "odata.metadata";

    public final static String JSON_TYPE = "odata.type";

    public final static String JSON_ID = "odata.id";

    public final static String JSON_ETAG = "odata.etag";

    public final static String JSON_READ_LINK = "odata.readLink";

    public final static String JSON_EDIT_LINK = "odata.editLink";

    public final static String JSON_MEDIAREAD_LINK = "odata.mediaReadLink";

    public final static String JSON_MEDIAEDIT_LINK = "odata.mediaEditLink";

    public final static String JSON_MEDIA_CONTENT_TYPE = "odata.mediaContentType";

    public final static String JSON_NAVIGATION_LINK_SUFFIX = "@odata.navigationLinkUrl";

    public final static String JSON_BIND_LINK_SUFFIX = "@odata.bind";

    public final static String JSON_ASSOCIATION_LINK_SUFFIX = "@odata.associationLinkUrl";

    public final static String JSON_MEDIAEDIT_LINK_SUFFIX = "@odata.mediaEditLink";

    public final static String JSON_VALUE = "value";

    public final static String JSON_URL = "url";

}
