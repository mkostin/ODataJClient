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

import java.util.Date;

public class ODataEntityAtomExtensions {

    /**
     * Atom entry ID.
     */
    private String id;

    /**
     * Last update date and time.
     */
    private Date updated;

    /**
     * OData entity description.
     */
    private String summary;

    /**
     * Author.
     */
    private String author;

    /**
     * Returns Atom entry ID.
     *
     * @return OData entity ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the given Atom entry ID.
     *
     * @param id ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns OData entity description (Atom entry summary).
     *
     * @return OData entity description.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set the given description (Atom entry summary).
     *
     * @param summary description.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Returns OData entity author (Atom entry author).
     *
     * @return OData entity author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the given author (Atom entry author).
     *
     * @param author author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns OData entity last update date and time.
     *
     * @return OData entity last update date and time.
     */
    public Date getUpdated() {
        return updated;
    }
}
