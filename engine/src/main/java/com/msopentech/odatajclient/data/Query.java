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

/**
 * Query builder.
 */
public class Query {

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
     * Add the specified option to the query.
     *
     * @param option query option.
     * @param value query option value.
     * @return current query object.
     */
    public Query addQueryOption(final QueryOption option, final String value) {
        return addQueryOption(option.toString(), value);
    }

    /**
     * Add the specified option to the query.
     *
     * @param option query option.
     * @param value query option value.
     * @return current query object.
     */
    public Query addQueryOption(final String option, final String value) {
        // add query option here ...
        return this;
    }

    /**
     * Extend the query path with the specified string.
     *
     * @param subPath sub path to be appended.
     * @return current query object.
     */
    public Query append(final String subPath) {
        return this;
    }

    /**
     * Add expand query option.
     *
     * @param entityName entity object to be in-line expanded.
     * @return current query object.
     */
    public Query expand(final String entityName) {
        // add expand query option here ...
        return addQueryOption(QueryOption.EXPAND, entityName);
    }

    public ODataURI getODataURI() {
        return new ODataURI();
    }

    /**
     * Get query's absolute URI.
     *
     * @return URI string.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
