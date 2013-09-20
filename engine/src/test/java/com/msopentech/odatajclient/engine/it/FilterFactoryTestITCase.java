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
package com.msopentech.odatajclient.engine.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.data.ODataEntitySet;
import com.msopentech.odatajclient.engine.uri.ODataURIBuilder;
import com.msopentech.odatajclient.engine.uri.filter.ODataFilter;
import com.msopentech.odatajclient.engine.uri.filter.ODataFilterArgFactory;
import com.msopentech.odatajclient.engine.uri.filter.ODataFilterFactory;
import org.junit.Test;

public class FilterFactoryTestITCase extends AbstractTest {

    private void match(final String entitySet, final ODataFilter filter, final int expected) {
        final ODataURIBuilder uriBuilder = new ODataURIBuilder(testDefaultServiceRootURL).
                appendEntitySetSegment(entitySet).filter(filter);

        final ODataEntitySet feed = ODataRetrieveRequestFactory.getEntitySetRequest(uriBuilder.build()).
                execute().getBody();
        assertNotNull(feed);
        assertEquals(expected, feed.getEntities().size());
    }

    @Test
    public void simple() {
        match("Car", ODataFilterFactory.lt("VIN", 16), 5);
    }

    @Test
    public void and() {
        final ODataFilter filter =
                ODataFilterFactory.and(
                ODataFilterFactory.lt("VIN", 16),
                ODataFilterFactory.gt("VIN", 12));

        match("Car", filter, 3);
    }

    @Test
    public void not() {
        final ODataFilter filter =
                ODataFilterFactory.not(
                ODataFilterFactory.or(
                ODataFilterFactory.ge("VIN", 16),
                ODataFilterFactory.le("VIN", 12)));

        match("Car", filter, 3);
    }

    @Test
    public void operator() {
        ODataFilter filter =
                ODataFilterFactory.eq(
                ODataFilterArgFactory.add(ODataFilterArgFactory.property("VIN"), ODataFilterArgFactory.literal(1)),
                ODataFilterArgFactory.literal(16));

        match("Car", filter, 1);

        filter =
                ODataFilterFactory.eq(
                ODataFilterArgFactory.add(ODataFilterArgFactory.literal(1), ODataFilterArgFactory.property("VIN")),
                ODataFilterArgFactory.literal(16));

        match("Car", filter, 1);

        filter =
                ODataFilterFactory.eq(
                ODataFilterArgFactory.literal(16),
                ODataFilterArgFactory.add(ODataFilterArgFactory.literal(1), ODataFilterArgFactory.property("VIN")));

        match("Car", filter, 1);
    }

    @Test
    public void function() {
        final ODataFilter filter =
                ODataFilterFactory.match(
                ODataFilterArgFactory.startswith(
                ODataFilterArgFactory.property("Description"), ODataFilterArgFactory.literal("cen")));

        match("Car", filter, 1);
    }

    @Test
    public void composed() {
        final ODataFilter filter =
                ODataFilterFactory.gt(
                ODataFilterArgFactory.length(ODataFilterArgFactory.property("Description")),
                ODataFilterArgFactory.add(ODataFilterArgFactory.property("VIN"), ODataFilterArgFactory.literal(10)));

        match("Car", filter, 5);
    }

    @Test
    public void propertyPath() {
        ODataFilter filter =
                ODataFilterFactory.eq(
                ODataFilterArgFactory.indexof(
                ODataFilterArgFactory.property("PrimaryContactInfo/HomePhone/PhoneNumber"),
                ODataFilterArgFactory.literal("ODataJClient")),
                ODataFilterArgFactory.literal(1));

        match("Customer", filter, 0);

        filter =
                ODataFilterFactory.ne(
                ODataFilterArgFactory.indexof(
                ODataFilterArgFactory.property("PrimaryContactInfo/HomePhone/PhoneNumber"),
                ODataFilterArgFactory.literal("lccvussrv")),
                ODataFilterArgFactory.literal(-1));

        match("Customer", filter, 2);
    }

    @Test
    public void datetime() {
        final ODataFilter filter =
                ODataFilterFactory.eq(
                ODataFilterArgFactory.month(
                ODataFilterArgFactory.property("ComplexConcurrency/QueriedDateTime")),
                ODataFilterArgFactory.literal(1));

        match("Product", filter, 2);
    }

    @Test
    public void isof() {
        final ODataFilter filter =
                ODataFilterFactory.match(
                ODataFilterArgFactory.isof(
                ODataFilterArgFactory.literal("Microsoft.Test.OData.Services.AstoriaDefaultService.SpecialEmployee")));

        match("Person", filter, 4);
    }
}
