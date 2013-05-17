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
package com.msopentech.odatajclient.data;

import java.io.InputStream;

/**
 * OData URI/QueryQuery builder.
 */
public class ODataURI {

    public enum QueryOption {

        EXPAND("expand"),
        FORMAT("format"),
        SELECT("select"),
        ORDERBY("orderby"),
        TOP("top"),
        SKIP("skip"),
        FILTER("filter"),
        INLINECOUNT("inlinecount"),
        LINKS("links"),
        COUNT("count");

        final String option;

        QueryOption(final String option) {
            this.option = option;
        }

        @Override
        public String toString() {
            return option;
        }
    }

    /**
     * Add the specified option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURI object.
     */
    public ODataURI addQueryOption(final QueryOption option, final String value) {
        return addQueryOption(option.toString(), value);
    }

    /**
     * Add the specified option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURI object.
     */
    public ODataURI addQueryOption(final String option, final String value) {
        // add query option here ...
        return this;
    }

    /**
     * Extend the URI with the specified string element.
     *
     * @param subPath sub path to be appended.
     * @return current query object.
     */
    public ODataURI append(final String subPath) {
        return this;
    }

    /**
     * Add expand query option.
     *
     * @param entityName entity object to be in-line expanded.
     * @return current ODataURI object.
     */
    public ODataURI expand(final String entityName) {
        // add expand query option here ...
        return addQueryOption(QueryOption.EXPAND, entityName);
    }

    /**
     * Get absolute URI as a string.
     *
     * @return URI as a string.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Get absolute URI as a stream.
     *
     * @return URI as a stream.
     */
    public InputStream toStream() {
        return null;
    }
}
