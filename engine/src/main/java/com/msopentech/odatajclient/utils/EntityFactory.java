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
package com.msopentech.odatajclient.utils;

import com.msopentech.odatajclient.data.ODataEntity;
import com.msopentech.odatajclient.data.ODataLink;
import com.msopentech.odatajclient.data.ODataURI;
import java.util.Date;

public class EntityFactory {

    public static ODataEntity newEntity(final String title) {
        return new EntityImpl(title);
    }

    static ODataEntity newEntity(final String title, final ODataURI link, final Date updated) {
        return new EntityImpl(title, link);
    }

    public static ODataLink newLink(final String title, final ODataURI link) {
        return new LinkImpl(title, link);
    }

    private static class EntityImpl extends ODataEntity {

        public EntityImpl(final String title) {
            super(title);
        }

        public EntityImpl(final String title, final ODataURI link) {
            super(title);
            this.link = link;
        }
    }

    private static class LinkImpl extends ODataLink {

        public LinkImpl(final String title, final ODataURI link) {
            super(title, link);
        }
    }
}
