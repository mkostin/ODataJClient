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

import java.net.URI;

/**
 * OData inline entity.
 */
public abstract class ODataInlineEntity extends ODataLink {

    private static final long serialVersionUID = -4763341581843700743L;

    private final ODataEntity entity;

    public ODataInlineEntity(final URI uri, final ODataLinkType type, final String title, final ODataEntity entity) {
        super(uri, type, title);
        this.entity = entity;
    }

    public ODataInlineEntity(final URI baseURI, final String href, final ODataLinkType type, final String title,
            final ODataEntity entity) {

        super(baseURI, href, type, title);
        this.entity = entity;
    }

    public ODataEntity getEntity() {
        return entity;
    }
}
