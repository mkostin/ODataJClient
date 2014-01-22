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
package com.msopentech.odatajclient.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.msopentech.odatajclient.engine.client.ODataV4Client;
import com.msopentech.odatajclient.engine.data.metadata.EdmV4Metadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmV4Type;
import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.StoreGeneratedPattern;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.Action;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.AnnotationConstantExpressionType;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.Annotations;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.ComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.EntitySet;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.EnumType;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.Function;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.FunctionImport;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.Schema;
import com.msopentech.odatajclient.engine.data.metadata.edm.v4.Singleton;
import java.util.List;
import org.junit.Test;

public class V4MetadataTest extends AbstractMetadataTest {

    @Override
    protected ODataV4Client getClient() {
        return v4Client;
    }

    @Test
    public void parse() {
        final EdmV4Metadata metadata = getClient().getReader().
                readMetadata(getClass().getResourceAsStream(getPath("metadata.xml")));
        assertNotNull(metadata);

        // 1. Enum
        final EnumType responseEnumType = metadata.getSchema(0).getEnumType("ResponseType");
        assertNotNull(responseEnumType);
        assertEquals(6, responseEnumType.getMembers().size());
        assertEquals(3, responseEnumType.getMember("Accepted").getValue().intValue());
        assertEquals("Accepted", responseEnumType.getMember(3).getName());

        final EdmV4Type responseType = new EdmV4Type(metadata, "Microsoft.Exchange.Services.OData.Model.ResponseType");
        assertNotNull(responseType);
        assertFalse(responseType.isCollection());
        assertFalse(responseType.isSimpleType());
        assertTrue(responseType.isEnumType());
        assertFalse(responseType.isComplexType());
        assertFalse(responseType.isEntityType());

        // 2. Complex
        final ComplexType responseStatus = metadata.getSchema(0).getComplexType("ResponseStatus");
        assertNotNull(responseStatus);
        assertTrue(responseStatus.getNavigationProperties().isEmpty());
        assertEquals(EdmSimpleType.DateTimeOffset,
                EdmSimpleType.fromValue(responseStatus.getProperty("Time").getType()));

        // 3. Entity
        final EntityType user = metadata.getSchema(0).getEntityType("User");
        assertNotNull(user);
        assertEquals("Microsoft.Exchange.Services.OData.Model.Entity", user.getBaseType());
        assertFalse(user.getProperties().isEmpty());
        assertFalse(user.getNavigationProperties().isEmpty());
        assertEquals("Microsoft.Exchange.Services.OData.Model.Folder", user.getNavigationProperty("Inbox").getType());

        // 4. Action
        final List<Action> moves = metadata.getSchema(0).getActions("Move");
        assertFalse(moves.isEmpty());
        Action move = null;
        for (Action action : moves) {
            if ("Microsoft.Exchange.Services.OData.Model.EmailMessage".equals(action.getReturnType().getType())) {
                move = action;
            }
        }
        assertNotNull(move);
        assertTrue(move.isBound());
        assertEquals("bindingParameter", move.getEntitySetPath());
        assertEquals(2, move.getParameters().size());
        assertEquals("Microsoft.Exchange.Services.OData.Model.EmailMessage", move.getParameters().get(0).getType());

        // 5. EntityContainer
        final EntityContainer container = metadata.getSchema(0).getEntityContainer();
        assertNotNull(container);
        final EntitySet users = container.getEntitySet("Users");
        assertNotNull(users);
        assertEquals(metadata.getSchema(0).getNamespace() + "." + user.getName(), users.getEntityType());
        assertEquals(user.getNavigationProperties().size(), users.getNavigationPropertyBindings().size());
    }

    @Test
    public void demo() {
        final EdmV4Metadata metadata = getClient().getReader().
                readMetadata(getClass().getResourceAsStream(getPath("demo-metadata.xml")));
        assertNotNull(metadata);

        assertFalse(metadata.getSchema(0).getAnnotationsList().isEmpty());
        Annotations annots = metadata.getSchema(0).getAnnotationsList("ODataDemo.DemoService/Suppliers");
        assertNotNull(annots);
        assertFalse(annots.getAnnotations().isEmpty());
        assertEquals(AnnotationConstantExpressionType.String,
                annots.getAnnotation("Org.OData.Publication.V1.PrivacyPolicyUrl").getConstantExpressionType());
        assertEquals("http://www.odata.org/",
                annots.getAnnotation("Org.OData.Publication.V1.PrivacyPolicyUrl").getConstantExpressionValue());
    }

    @Test
    public void multipleSchemas() {
        final EdmV4Metadata metadata = getClient().getReader().
                readMetadata(getClass().getResourceAsStream(getPath("northwind-metadata.xml")));
        assertNotNull(metadata);

        final Schema first = metadata.getSchema("NorthwindModel");
        assertNotNull(first);

        final Schema second = metadata.getSchema("ODataWebExperimental.Northwind.Model");
        assertNotNull(second);

        assertEquals(StoreGeneratedPattern.Identity,
                first.getEntityType("Category").getProperty("CategoryID").getStoreGeneratedPattern());

        final EntityContainer entityContainer = second.getDefaultEntityContainer();
        assertNotNull(entityContainer);
        assertEquals("NorthwindEntities", entityContainer.getName());
        assertTrue(entityContainer.isLazyLoadingEnabled());
    }

    /**
     * Tests Example 85 from CSDL specification.
     */
    @Test
    public void fromdoc1() {
        final EdmV4Metadata metadata = getClient().getReader().
                readMetadata(getClass().getResourceAsStream(getPath("fromdoc1-metadata.xml")));
        assertNotNull(metadata);

        assertFalse(metadata.getReferences().isEmpty());
        assertEquals("Org.OData.Measures.V1", metadata.getReferences().get(1).getIncludes().get(0).getNamespace());

        final EntityType product = metadata.getSchema(0).getEntityType("Product");
        assertTrue(product.isHasStream());
        assertEquals("UoM.ISOCurrency", product.getProperty("Price").getAnnotation().getTerm());
        //assertEquals("Currency", product.getProperty("Price").getAnnotation().getPath());
        assertEquals("Products", product.getNavigationProperty("Supplier").getPartner());

        final EntityType category = metadata.getSchema(0).getEntityType("Category");
        final EdmV4Type type = new EdmV4Type(metadata, category.getNavigationProperty("Products").getType());
        assertNotNull(type);
        assertTrue(type.isCollection());
        assertFalse(type.isSimpleType());

        final ComplexType address = metadata.getSchema(0).getComplexType("Address");
        assertFalse(address.getNavigationProperty("Country").getReferentialConstraints().isEmpty());
        assertEquals("Name",
                address.getNavigationProperty("Country").getReferentialConstraints().get(0).getReferencedProperty());

        final Function productsByRating = metadata.getSchema(0).getFunctions("ProductsByRating").get(0);
        assertNotNull(productsByRating.getParameter("Rating"));
        assertEquals("Edm.Int32", productsByRating.getParameter("Rating").getType());
        assertEquals("Collection(ODataDemo.Product)", productsByRating.getReturnType().getType());

        final Singleton contoso = metadata.getSchema(0).getEntityContainer().getSingleton("Contoso");
        assertNotNull(contoso);
        assertFalse(contoso.getNavigationPropertyBindings().isEmpty());
        assertEquals("Products", contoso.getNavigationPropertyBindings().get(0).getPath());

        final FunctionImport functionImport = metadata.getSchema(0).getEntityContainer().
                getFunctionImport("ProductsByRating");
        assertNotNull(functionImport);
        assertEquals(metadata.getSchema(0).getNamespace() + "." + productsByRating.getName(),
                functionImport.getFunction());
    }

    /**
     * Tests Example 86 from CSDL specification.
     */
    @Test
    public void fromdoc2() {
        final EdmV4Metadata metadata = getClient().getReader().
                readMetadata(getClass().getResourceAsStream(getPath("fromdoc2-metadata.xml")));
        assertNotNull(metadata);
    }
}
