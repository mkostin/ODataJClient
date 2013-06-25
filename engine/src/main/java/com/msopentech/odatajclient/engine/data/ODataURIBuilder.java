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

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;

/**
 * OData URI/Query builder.
 */
public class ODataURIBuilder implements Serializable {

    private static final long serialVersionUID = -3267515371720408124L;

    public enum SegmentType {

        ENTITYSET,
        ENTITYTYPE,
        KEY,
        NAVIGATION,
        STRUNCTURAL,
        VALUE,
        FUNCTION,
        LEGACY,
        ACTION,
        METADATA("$metadata"),
        BATCH("$batch"),
        SERVICEROOT;

        private String value;

        private SegmentType() {
            this.value = StringUtils.EMPTY;
        }

        private SegmentType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Query options.
     */
    public enum QueryOption {

        /**
         * This option indicates entities associated with the EntityType
         * instance or EntitySet, identified by the resource path section of
         * the URI, and MUST be represented inline in the data service's
         * response.
         */
        EXPAND("expand"),
        /**
         * This option specifies the media type acceptable in a response. If
         * present, this value SHOULD take precedence over value(s)
         * specified in an Accept request header.
         */
        FORMAT("format"),
        /**
         * This option is used to specify that a subset of the properties of
         * the entities identified by the path of the request URI and
         * $expand query option SHOULD be returned in the response
         * from the data service.
         */
        SELECT("select"),
        /**
         * This option specifies the sort properties and sort direction
         * (ascending or descending) that the data service MUST use to
         * order the entities in the EntitySet, identified by the resource
         * path section of the URI.
         */
        ORDERBY("orderby"),
        /**
         * This option specifies a positive integer N that is the maximum
         * number of entities in the EntitySet, identified by the resource
         * path section of the URI, that the data service MUST return.
         */
        TOP("top"),
        /**
         * This option specifies a positive integer N that represents the
         * number of entities, counted from the first entity in the
         * EntitySet and ordered as specified by the $orderby option,
         * that the data service should skip when returning the entities in
         * the EntitySet, which is identified by the resource path section
         * of the URI. The data service SHOULD return all subsequent
         * entities, starting from the one in position N+1.
         */
        SKIP("skip"),
        /**
         * This query option applies only to the OData 2.0 protocol to the AtomPub protocol.
         * The value of a $skiptoken query option is an opaque token
         * which identifies an index into the collection of entities identified
         * by the URI containing the $skiptoken parameter.
         */
        SKIPTOKEN("skiptoken"),
        /**
         * This option specifies a predicate used to filter the elements from
         * the EntitySet identified by the resource path section of the URI.
         */
        FILTER("filter"),
        /**
         * For a value of "allpages", this option indicates that the response
         * to the request MUST include the count of the number of entities
         * in the EntitySet, identified by the resource path section of the
         * URI after all $filter system query options have been applied.
         * For a value of "none", this option indicates that the response to
         * the request MUST NOT include the count value.
         */
        INLINECOUNT("inlinecount");

        final String option;

        QueryOption(final String option) {
            this.option = option;
        }

        @Override
        public String toString() {
            return option;
        }
    }

    final List<Segment> segments;

    /**
     * Case-insensitive map of query parameters.
     */
    private final Map<String, String> queryParameters = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Case-insensitive map of query options.
     */
    private final Map<String, String> queryOptions = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Constructor.
     *
     * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the
     * data service.
     */
    public ODataURIBuilder(final String serviceRoot) {
        segments = new ArrayList<Segment>();
        segments.add(new Segment(SegmentType.SERVICEROOT, serviceRoot));
    }

    /**
     * Add query parameter.
     *
     * @param name parameter name.
     * @param value parameter value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder addQueryParameter(final String name, final String value) {
        queryParameters.put(name, value);
        return this;
    }

    /**
     * Add the specified query option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder addQueryOption(final QueryOption option, final String value) {
        return addQueryOption(option.toString(), value);
    }

    /**
     * Add the specified (custom) query option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder addQueryOption(final String option, final String value) {
        queryOptions.put(option, value);
        return this;
    }

    /**
     * Append EntitySet segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendEntitySetSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.SERVICEROOT, segmentValue));
        return this;
    }

    /**
     * Append EntityType segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendEntityTypeSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.ENTITYTYPE, segmentValue));
        return this;
    }

    /**
     * Append key segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendKeySegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.KEY, segmentValue));
        return this;
    }

    /**
     * Append navigation link segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendNavigationLinkSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.NAVIGATION, segmentValue));
        return this;
    }

    /**
     * Append structural segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendStructuralSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.STRUNCTURAL, segmentValue));
        return this;
    }

    /**
     * Append value segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendValueSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.VALUE, segmentValue));
        return this;
    }

    /**
     * Append function segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendFunctionSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.FUNCTION, segmentValue));
        return this;
    }

    /**
     * Append legacy operation segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendLegacySegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.LEGACY, segmentValue));
        return this;
    }

    /**
     * Append action segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendActionSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.ACTION, segmentValue));
        return this;
    }

    /**
     * Append metadata segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendMetadataSegment() {
        segments.add(new Segment(SegmentType.METADATA, SegmentType.METADATA.getValue()));
        return this;
    }

    /**
     * Append batch segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURI object.
     */
    public ODataURIBuilder appendBatchSegment() {
        segments.add(new Segment(SegmentType.BATCH, SegmentType.BATCH.getValue()));
        return this;
    }

    /**
     * Add expand query option.
     *
     * @param entityName entity object to be in-line expanded.
     * @return current ODataURI object.
     * @see QueryOption#EXPAND
     */
    public ODataURIBuilder expand(final String entityName) {
        return addQueryOption(QueryOption.EXPAND, entityName);
    }

    /**
     * Add format query option.
     *
     * @param format media type acceptable in a response.
     * @return current ODataURI object.
     * @see QueryOption#FORMAT
     */
    public ODataURIBuilder format(final String format) {
        return addQueryOption(QueryOption.FORMAT, format);
    }

    /**
     * Add format query option.
     *
     * @param filter filter string.
     * @return current ODataURI object.
     * @see QueryOption#FILTER
     */
    public ODataURIBuilder filter(final String filter) {
        return addQueryOption(QueryOption.FILTER, filter);
    }

    /**
     * Add select query option.
     *
     * @param select select query option value.
     * @return current ODataURI object.
     * @see QueryOption#SELECT
     */
    public ODataURIBuilder select(final String select) {
        return addQueryOption(QueryOption.SELECT, select);
    }

    /**
     * Add orderby query option.
     *
     * @param order order string.
     * @return current ODataURI object.
     * @see QueryOption#ORDERBY
     */
    public ODataURIBuilder orderBy(final String order) {
        return addQueryOption(QueryOption.ORDERBY, order);
    }

    /**
     * Add top query option.
     *
     * @param top maximum number of entities to be returned.
     * @return current ODataURI object.
     * @see QueryOption#TOP
     */
    public ODataURIBuilder top(final int top) {
        return addQueryOption(QueryOption.TOP, String.valueOf(top));
    }

    /**
     * Add skip query option.
     *
     * @param skip number of entities to be skipped into the response.
     * @return current ODataURI object.
     * @see QueryOption#SKIP
     */
    public ODataURIBuilder skip(final int skip) {
        return addQueryOption(QueryOption.SKIP, String.valueOf(skip));
    }

    /**
     * Add skiptoken query option.
     *
     * @param skipToken opaque token.
     * @return current ODataURI object.
     * @see QueryOption#SKIPTOKEN
     */
    public ODataURIBuilder skipToken(final String skipToken) {
        return addQueryOption(QueryOption.SKIPTOKEN, skipToken);
    }

    /**
     * Add inlinecount query option.
     *
     * @return current ODataURI object.
     * @see QueryOption#INLINECOUNT
     */
    public ODataURIBuilder inlineCount() {
        return addQueryOption(QueryOption.INLINECOUNT, "allpages");
    }

    private static class Segment {

        private final SegmentType type;

        private final String value;

        public Segment(final SegmentType type, final String value) {
            this.type = type;
            this.value = value;
        }

        public SegmentType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Build OData URI.
     *
     * @return OData URI.
     */
    public URI build() {
        final StringBuilder builder = new StringBuilder();

        for (Segment seg : segments) {
            if (builder.length() > 0) {
                builder.append("/");
            }

            builder.append(seg.value);
        }

        final StringBuilder builderOptions = new StringBuilder();

        for (Map.Entry<String, String> option : queryOptions.entrySet()) {
            if (builderOptions.length() > 0) {
                builderOptions.append("&");
            }

            builderOptions.append("$").append(option.getKey()).append("=").append(option.getValue());
        }

        if (builderOptions.length() > 0) {
            builder.append("?").append(builderOptions);
        }

        return URI.create(builder.toString());
    }
}
