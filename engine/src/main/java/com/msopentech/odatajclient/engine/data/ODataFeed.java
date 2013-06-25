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
import java.util.ArrayList;
import java.util.List;

/**
 * OData entity collection.
 * If pagination was used to get this instance, forward page navigation URI will be available.
 */
public class ODataFeed extends ODataItem {

    private static final long serialVersionUID = 9039605899821494024L;

    /**
     * Link to the next page.
     */
    protected URI next;

    /**
     * OData entities contained in this collection.
     */
    protected List<ODataEntity> entities = new ArrayList<ODataEntity>();

    public ODataFeed() {
        super(null);
    }

    /**
     * Gets next page link.
     *
     * @return next page link; null value if single page or last page reached.
     */
    public URI getNext() {
        return next;
    }

    public boolean addEntity(final ODataEntity entity) {
        return entities.contains(entity) ? false : entities.add(entity);
    }

    public boolean removeEntity(final ODataEntity entity) {
        return entities.remove(entity);
    }

    /**
     * Gets contained entities.
     *
     * @return feed entries.
     */
    public List<ODataEntity> getEntities() {
        return entities;
    }
}
