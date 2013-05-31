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
 * OData navigation link.
 */
public abstract class ODataLink extends ODataItem {

    private static final long serialVersionUID = -3625922586547616628L;

    /**
     * Constructor.
     *
     * @param name link property name.
     * @param link link value.
     */
    public ODataLink(final String name, final URI link) {
        super(name);
        this.link = link;
    }
}
