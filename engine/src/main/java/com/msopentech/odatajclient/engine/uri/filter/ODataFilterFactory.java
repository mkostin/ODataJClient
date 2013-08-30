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
package com.msopentech.odatajclient.engine.uri.filter;

/**
 * OData filter factory.
 */
public final class ODataFilterFactory {

    private static final String NULL = "null";

    private ODataFilterFactory() {
        // Empty private constructor for static utility classes
    }

    public static ODataFilter match(final ODataFilterArg arg) {
        return new MatchFilter(arg);
    }

    public static ODataFilter eq(final String key, final Object value) {
        return new EqFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter eq(final ODataFilterArg left, final ODataFilterArg right) {
        return new EqFilter(left, right);
    }

    public static ODataFilter ne(final String key, final Object value) {
        return new NeFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter ne(final ODataFilterArg left, final ODataFilterArg right) {
        return new NeFilter(left, right);
    }

    public static ODataFilter gt(final String key, final Object value) {
        return new GtFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter gt(final ODataFilterArg left, final ODataFilterArg right) {
        return new GtFilter(left, right);
    }

    public static ODataFilter ge(final String key, final Object value) {
        return new GeFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter ge(final ODataFilterArg left, final ODataFilterArg right) {
        return new GeFilter(left, right);
    }

    public static ODataFilter lt(final String key, final Object value) {
        return new LtFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter lt(final ODataFilterArg left, final ODataFilterArg right) {
        return new LtFilter(left, right);
    }

    public static ODataFilter le(final String key, final Object value) {
        return new LeFilter(ODataFilterArgFactory.property(key), ODataFilterArgFactory.literal(value));
    }

    public static ODataFilter le(final ODataFilterArg left, final ODataFilterArg right) {
        return new LeFilter(left, right);
    }

    public static ODataFilter and(final ODataFilter left, final ODataFilter right) {
        return new AndFilter(left, right);
    }

    public static ODataFilter or(final ODataFilter left, final ODataFilter right) {
        return new OrFilter(left, right);
    }

    public static ODataFilter not(final ODataFilter filter) {
        return new NotFilter(filter);
    }
}
