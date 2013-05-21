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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ODataEntity extends AbstractEntity {

    private String id;

    private Date updated;

    private String summary;

    private String author;

    private final List<ODataProperty> properties = new ArrayList<ODataProperty>();

    private final List<AbstractEntity> links = new ArrayList<AbstractEntity>();

    public ODataEntity(final String title) {
        super(title);
    }

    public List<ODataProperty> getProperties() {
        return properties;
    }

    public void addProperty(final ODataProperty property) {
        if (!properties.contains(property)) {
            properties.add(property);
        }
    }

    public List<AbstractEntity> getLinks() {
        return links;
    }

    public void addLink(final AbstractEntity entity) {
        if (!links.contains(entity)) {
            links.add(entity);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getUpdated() {
        return updated;
    }
}
