/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.uri;

import com.msopentech.odatajclient.engine.uri.filter.ODataFilter;
import com.msopentech.odatajclient.engine.uri.filter.ODataFilterFactory;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OData URI builder.
 */
public class ODataURIBuilder implements Serializable {

    private static class Segment {

        private final SegmentType type;

        private final String value;

        public Segment(final SegmentType type, final String value) {
            this.type = type;
            this.value = value;
        }
    }

    private static final long serialVersionUID = -3267515371720408124L;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ODataURIBuilder.class);

    private final List<Segment> segments;

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
     * Adds the specified query option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder addQueryOption(final QueryOption option, final String value) {
        return addQueryOption(option.toString(), value);
    }

    /**
     * Adds the specified (custom) query option to the URI.
     *
     * @param option query option.
     * @param value query option value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder addQueryOption(final String option, final String value) {
        queryOptions.put(option, value);
        return this;
    }

    /**
     * Append EntitySet segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendEntitySetSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.ENTITYSET, segmentValue));
        return this;
    }

    /**
     * Append EntityType segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendEntityTypeSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.ENTITYTYPE, segmentValue));
        return this;
    }

    /**
     * Append key segment to the URI.
     *
     * @param val segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendKeySegment(final Object val) {
        final String segValue = PrimitiveValueURIEscaper.escape(val);

        segments.add(Configuration.isKeyAsSegment()
                ? new Segment(SegmentType.KEY_AS_SEGMENT, segValue)
                : new Segment(SegmentType.KEY, "(" + segValue + ")"));
        return this;
    }

    /**
     * Append key segment to the URI, for multiple keys.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendKeySegment(final Map<String, Object> segmentValues) {
        if (!Configuration.isKeyAsSegment()) {
            final StringBuilder keyBuilder = new StringBuilder().append('(');
            for (Map.Entry<String, Object> entry : segmentValues.entrySet()) {
                keyBuilder.append(entry.getKey()).append('=').append(PrimitiveValueURIEscaper.escape(entry.getValue()));
                keyBuilder.append(',');
            }
            keyBuilder.deleteCharAt(keyBuilder.length() - 1).append(')');

            segments.add(new Segment(SegmentType.KEY, keyBuilder.toString()));
        }

        return this;
    }

    /**
     * Append navigation link segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendNavigationLinkSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.NAVIGATION, segmentValue));
        return this;
    }

    /**
     * Append structural segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendStructuralSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.STRUCTURAL, segmentValue));
        return this;
    }

    public ODataURIBuilder appendLinksSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.LINKS, SegmentType.LINKS.getValue()));
        segments.add(new Segment(SegmentType.ENTITYTYPE, segmentValue));
        return this;
    }

    /**
     * Append value segment to the URI.
     *
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendValueSegment() {
        segments.add(new Segment(SegmentType.VALUE, SegmentType.VALUE.getValue()));
        return this;
    }

    /**
     * Append count segment to the URI.
     *
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendCountSegment() {
        segments.add(new Segment(SegmentType.COUNT, SegmentType.COUNT.getValue()));
        return this;
    }

    /**
     * Append function import segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendFunctionImportSegment(final String segmentValue) {
        segments.add(new Segment(SegmentType.FUNCTIONIMPORT, segmentValue));
        return this;
    }

    /**
     * Append metadata segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendMetadataSegment() {
        segments.add(new Segment(SegmentType.METADATA, SegmentType.METADATA.getValue()));
        return this;
    }

    /**
     * Append batch segment to the URI.
     *
     * @param segmentValue segment value.
     * @return current ODataURIBuilder object.
     */
    public ODataURIBuilder appendBatchSegment() {
        segments.add(new Segment(SegmentType.BATCH, SegmentType.BATCH.getValue()));
        return this;
    }

    /**
     * Adds expand query option.
     *
     * @param entityName entity object to be in-line expanded.
     * @return current ODataURIBuilder object.
     * @see QueryOption#EXPAND
     */
    public ODataURIBuilder expand(final String entityName) {
        return addQueryOption(QueryOption.EXPAND, entityName);
    }

    /**
     * Adds format query option.
     *
     * @param format media type acceptable in a response.
     * @return current ODataURIBuilder object.
     * @see QueryOption#FORMAT
     */
    public ODataURIBuilder format(final String format) {
        return addQueryOption(QueryOption.FORMAT, format);
    }

    /**
     * Adds filter for filter query option.
     *
     * @param filter filter instance (to be obtained via <tt>ODataFilterFactory</tt>):
     * note that <tt>build()</tt> method will be immediately invoked.
     * @return current ODataURIBuilder object.
     * @see QueryOption#FILTER
     * @see ODataFilter
     * @see ODataFilterFactory
     */
    public ODataURIBuilder filter(final ODataFilter filter) {
        return addQueryOption(QueryOption.FILTER, filter.build());
    }

    /**
     * Adds filter query option.
     *
     * @param filter filter string.
     * @return current ODataURIBuilder object.
     * @see QueryOption#FILTER
     */
    public ODataURIBuilder filter(final String filter) {
        return addQueryOption(QueryOption.FILTER, filter);
    }

    /**
     * Adds select query option.
     *
     * @param select select query option value.
     * @return current ODataURIBuilder object.
     * @see QueryOption#SELECT
     */
    public ODataURIBuilder select(final String select) {
        return addQueryOption(QueryOption.SELECT, select);
    }

    /**
     * Adds orderby query option.
     *
     * @param order order string.
     * @return current ODataURIBuilder object.
     * @see QueryOption#ORDERBY
     */
    public ODataURIBuilder orderBy(final String order) {
        return addQueryOption(QueryOption.ORDERBY, order);
    }

    /**
     * Adds top query option.
     *
     * @param top maximum number of entities to be returned.
     * @return current ODataURIBuilder object.
     * @see QueryOption#TOP
     */
    public ODataURIBuilder top(final int top) {
        return addQueryOption(QueryOption.TOP, String.valueOf(top));
    }

    /**
     * Adds skip query option.
     *
     * @param skip number of entities to be skipped into the response.
     * @return current ODataURIBuilder object.
     * @see QueryOption#SKIP
     */
    public ODataURIBuilder skip(final int skip) {
        return addQueryOption(QueryOption.SKIP, String.valueOf(skip));
    }

    /**
     * Adds skiptoken query option.
     *
     * @param skipToken opaque token.
     * @return current ODataURIBuilder object.
     * @see QueryOption#SKIPTOKEN
     */
    public ODataURIBuilder skipToken(final String skipToken) {
        return addQueryOption(QueryOption.SKIPTOKEN, skipToken);
    }

    /**
     * Adds inlinecount query option.
     *
     * @return current ODataURIBuilder object.
     * @see QueryOption#INLINECOUNT
     */
    public ODataURIBuilder inlineCount() {
        return addQueryOption(QueryOption.INLINECOUNT, "allpages");
    }

    /**
     * Build OData URI.
     *
     * @return OData URI.
     */
    public URI build() {
        final StringBuilder segmentsBuilder = new StringBuilder();
        for (Segment seg : segments) {
            if (segmentsBuilder.length() > 0 && seg.type != SegmentType.KEY) {
                segmentsBuilder.append('/');
            }

            segmentsBuilder.append(seg.value);
        }

        try {
            final URIBuilder builder = new URIBuilder(segmentsBuilder.toString());

            for (Map.Entry<String, String> option : queryOptions.entrySet()) {
                builder.addParameter("$" + option.getKey(), option.getValue());
            }

            return builder.build().normalize();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not build valid URI", e);
        }
    }

    /**
     * ${@inheritDoc }
     */
    @Override
    public String toString() {
        return build().toASCIIString();
    }
}
