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
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.types.ODataFormat;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;

/**
 * OData link types.
 */
public enum ODataLinkType {

    ENTITY_NAVIGATION(ODataFormat.ATOM + ";type=entry"),
    FEED_NAVIGATION(ODataFormat.ATOM + ";type=feed"),
    ASSOCIATION(MediaType.APPLICATION_XML),
    MEDIA_EDIT("*/*");

    private String type;

    private ODataLinkType(final String type) {
        this.type = type;
    }

    private ODataLinkType setType(final String type) {
        this.type = type;
        return this;
    }

    public static ODataLinkType evaluate(final String rel, final String type) {
        if (StringUtils.isNotBlank(rel) && rel.startsWith(ODataConstants.MEDIA_EDIT_LINK_REL)) {
            return MEDIA_EDIT.setType(StringUtils.isBlank(type) ? "*/*" : type);
        }

        if (ODataLinkType.ENTITY_NAVIGATION.type.equals(type)) {
            return ENTITY_NAVIGATION;
        }

        if (ODataLinkType.FEED_NAVIGATION.type.equals(type)) {
            return FEED_NAVIGATION;
        }

        if (ODataLinkType.ASSOCIATION.type.equals(type)) {
            return ASSOCIATION;
        }

        throw new IllegalArgumentException("Invalid link type: " + type);
    }

    @Override
    public String toString() {
        return type;
    }
}
