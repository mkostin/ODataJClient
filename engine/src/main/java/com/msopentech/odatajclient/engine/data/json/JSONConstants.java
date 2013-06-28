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
package com.msopentech.odatajclient.engine.data.json;

/**
 * Set of some useful JSON constants.
 */
public final class JSONConstants {

    private JSONConstants() {
        // Empty private constructor for static utility classes
    }

    public final static String METADATA = "odata.metadata";

    public final static String TYPE = "odata.type";

    public final static String ID = "odata.id";

    public final static String ETAG = "odata.etag";

    public final static String READ_LINK = "odata.readLink";

    public final static String EDIT_LINK = "odata.editLink";

    public final static String MEDIAREAD_LINK = "odata.mediaReadLink";

    public final static String MEDIAEDIT_LINK = "odata.mediaEditLink";

    public final static String MEDIA_CONTENT_TYPE = "odata.mediaContentType";

    public final static String NAVIGATION_LINK_SUFFIX = "@odata.navigationLinkUrl";

    public final static String BIND_LINK_SUFFIX = "@odata.bind";

    public final static String ASSOCIATION_LINK_SUFFIX = "@odata.associationLinkUrl";

    public final static String MEDIAEDIT_LINK_SUFFIX = "@odata.mediaEditLink";

    public final static String VALUE = "value";

    public final static String URL = "url";

}
