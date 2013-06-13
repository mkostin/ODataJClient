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
package com.msopentech.odatajclient.engine.sap;

import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractAnnotated;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractFaceteable;
import com.msopentech.odatajclient.engine.data.metadata.edm.TAssociation;
import com.msopentech.odatajclient.engine.data.metadata.edm.TAssociationEnd;
import com.msopentech.odatajclient.engine.data.metadata.edm.TComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TComplexTypeProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityContainer.TAssociationSet;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityContainer.TEntitySet;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityContainer.TFunctionImport;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityKeyElement;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TFunctionImportParameter;
import com.msopentech.odatajclient.engine.data.metadata.edm.TNavigationProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.TOnAction;
import com.msopentech.odatajclient.engine.data.metadata.edm.TPropertyRef;
import com.msopentech.odatajclient.engine.data.metadata.edm.TTypeAnnotation;
import com.msopentech.odatajclient.engine.data.metadata.edm.TUsing;
import com.msopentech.odatajclient.engine.data.metadata.edm.TValueAnnotation;
import com.sap.core.odata.api.edm.Edm;
import com.sap.core.odata.api.edm.EdmAction;
import com.sap.core.odata.api.edm.EdmConcurrencyMode;
import com.sap.core.odata.api.edm.EdmContentKind;
import com.sap.core.odata.api.edm.EdmMultiplicity;
import com.sap.core.odata.api.edm.EdmSimpleType;
import com.sap.core.odata.api.edm.EdmSimpleTypeKind;
import com.sap.core.odata.api.edm.FullQualifiedName;
import com.sap.core.odata.api.edm.provider.AnnotationAttribute;
import com.sap.core.odata.api.edm.provider.AnnotationElement;
import com.sap.core.odata.api.edm.provider.Association;
import com.sap.core.odata.api.edm.provider.AssociationEnd;
import com.sap.core.odata.api.edm.provider.AssociationSet;
import com.sap.core.odata.api.edm.provider.AssociationSetEnd;
import com.sap.core.odata.api.edm.provider.ComplexProperty;
import com.sap.core.odata.api.edm.provider.ComplexType;
import com.sap.core.odata.api.edm.provider.CustomizableFeedMappings;
import com.sap.core.odata.api.edm.provider.EntityContainer;
import com.sap.core.odata.api.edm.provider.EntitySet;
import com.sap.core.odata.api.edm.provider.EntityType;
import com.sap.core.odata.api.edm.provider.Facets;
import com.sap.core.odata.api.edm.provider.FunctionImport;
import com.sap.core.odata.api.edm.provider.FunctionImportParameter;
import com.sap.core.odata.api.edm.provider.Key;
import com.sap.core.odata.api.edm.provider.NavigationProperty;
import com.sap.core.odata.api.edm.provider.OnDelete;
import com.sap.core.odata.api.edm.provider.Property;
import com.sap.core.odata.api.edm.provider.PropertyRef;
import com.sap.core.odata.api.edm.provider.ReturnType;
import com.sap.core.odata.api.edm.provider.SimpleProperty;
import com.sap.core.odata.api.edm.provider.Using;
import com.sap.core.odata.api.ep.EntityProviderException;
import com.sap.core.odata.core.edm.parser.EdmParserConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 * Utility methods for converting SAP entity model objects to JAXB entity model objects.
 */
public final class JAXB2SAPBridge {

    public JAXB2SAPBridge() {
        // private static constructor for utility classes
    }

    public static List<AnnotationAttribute> toAnnotationAttributes(Map<QName, String> attributes) {
        List<AnnotationAttribute> annotationAttributes = new ArrayList<AnnotationAttribute>();

        for (Map.Entry<QName, String> attribute : attributes.entrySet()) {
            annotationAttributes.add(
                    new AnnotationAttribute().
                    setName(attribute.getKey().getLocalPart()).
                    setPrefix(attribute.getKey().getPrefix()).
                    setNamespace(attribute.getKey().getNamespaceURI()).
                    setText(attribute.getValue()));
        }

        return annotationAttributes;
    }

    private static FullQualifiedName extractFQName(final String name)
            throws EntityProviderException {

        // Looking for the last dot
        String[] names = name.split("\\" + Edm.DELIMITER + "(?=[^\\" + Edm.DELIMITER + "]+$)");
        if (names.length != 2) {
            throw new EntityProviderException(EntityProviderException.COMMON.addContent("Invalid type"));
        } else {
            return new FullQualifiedName(names[0], names[1]);
        }

    }

    private static Property toSimpleProperty(final AbstractFaceteable tProperty, final FullQualifiedName fqName) {
        SimpleProperty property = new SimpleProperty();
        property.setName(tProperty.getName());
        // TODO: add GeographyPoint (and more)
        try {
            System.out.println("BBBBBBBBBBBBB1 " + fqName.getName());
            System.out.println("BBBBBBBBBBBBB2 " + EdmSimpleTypeKind.valueOf(fqName.getName()));
            System.out.println("=========================\n");
            
            property.setType(EdmSimpleTypeKind.valueOf(fqName.getName()));
        } catch (IllegalArgumentException e) {
            // ignore            
        }
        return property;
    }

    private static Property toComplexProperty(final AbstractFaceteable tProperty, final FullQualifiedName fqName) {
        ComplexProperty property = new ComplexProperty();
        property.setName(tProperty.getName());
        property.setType(fqName);
        return property;
    }

    private static Facets toFacets(final AbstractFaceteable tProperty) {
        Facets facets = new Facets();
        facets.setNullable(tProperty.isNullable());
        if (EdmParserConstants.EDM_PROPERTY_MAX_LENGTH_MAX_VALUE.equals(tProperty.getMaxLength())) {
            facets.setMaxLength(Integer.MAX_VALUE);
        } else {
            facets.setMaxLength(tProperty.getMaxLength() == null ? null : Integer.parseInt(tProperty.getMaxLength()));
        }
        facets.setPrecision(tProperty.getPrecision() == null ? null : tProperty.getPrecision().intValue());
        facets.setScale(tProperty.getScale() == null ? null : tProperty.getScale().intValue());
        facets.setFixedLength(tProperty.isFixedLength());
        facets.setUnicode(tProperty.isUnicode());
        if (tProperty.getConcurrencyMode() != null) {
            for (int i = 0; i < EdmConcurrencyMode.values().length; i++) {
                if (EdmConcurrencyMode.values()[i].name().equalsIgnoreCase(tProperty.getConcurrencyMode().name())) {
                    facets.setConcurrencyMode(EdmConcurrencyMode.values()[i]);
                }
            }
        }
        facets.setDefaultValue(tProperty.getDefaultValue());
        facets.setCollation(tProperty.getCollation());
        return facets;
    }

    private static CustomizableFeedMappings toCustomizableFeedMappings(Map<String, String> attributes) {
        String targetPath = attributes.get(EdmParserConstants.M_FC_TARGET_PATH);
        String sourcePath = attributes.get(EdmParserConstants.M_FC_SOURCE_PATH);
        String nsUri = attributes.get(EdmParserConstants.M_FC_NS_URI);
        String nsPrefix = attributes.get(EdmParserConstants.M_FC_PREFIX);
        String keepInContent = attributes.get(EdmParserConstants.M_FC_KEEP_IN_CONTENT);
        String contentKind = attributes.get(EdmParserConstants.M_FC_CONTENT_KIND);

        if (targetPath != null || sourcePath != null || nsUri != null || nsPrefix != null || keepInContent != null
                || contentKind != null) {

            CustomizableFeedMappings feedMapping = new CustomizableFeedMappings();
            if (keepInContent != null) {
                feedMapping.setFcKeepInContent("true".equals(keepInContent));
            }
            for (int i = 0; i < EdmContentKind.values().length; i++) {
                if (EdmContentKind.values()[i].name().equalsIgnoreCase(contentKind)) {
                    feedMapping.setFcContentKind(EdmContentKind.values()[i]);
                }
            }
            feedMapping.setFcTargetPath(targetPath).setFcSourcePath(sourcePath).setFcNsUri(nsUri).
                    setFcNsPrefix(nsPrefix);
            return feedMapping;
        } else {
            return null;
        }
    }

    /**
     * TODO: SAP's AnnotationElement should be upgraded to V3 TypeAnnotations / ValueAnnotations.
     */
    private static List<AnnotationElement> toAnnotationElements(final AbstractAnnotated annotated) {
        List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
        for (TTypeAnnotation tAnnotation : annotated.getTypeAnnotations()) {
            AnnotationElement annotationElement = new AnnotationElement();
            annotationElement.setAttributes(toAnnotationAttributes(tAnnotation.getOtherAttributes()));
            annotationElements.add(annotationElement);
        }
        for (TValueAnnotation tAnnotation : annotated.getValueAnnotations()) {
            AnnotationElement annotationElement = new AnnotationElement();
            annotationElement.setAttributes(toAnnotationAttributes(tAnnotation.getOtherAttributes()));
            annotationElements.add(annotationElement);
        }

        return annotationElements;
    }

    private static Property toProperty(AbstractFaceteable tProperty) throws EntityProviderException {
        Property property;

        if (tProperty.getType() == null) {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_TYPE).addContent(EdmParserConstants.EDM_PROPERTY));
        }
        FullQualifiedName fqName = extractFQName(tProperty.getType());

        if (EdmSimpleType.EDM_NAMESPACE.equals(fqName.getNamespace())) {
            property = toSimpleProperty(tProperty, fqName);
        } else {
            property = toComplexProperty(tProperty, fqName);
        }

        property.setFacets(toFacets(tProperty));

        Map<String, String> fcAttributes = new HashMap<String, String>();
        Map<QName, String> annotationAttributes = new HashMap<QName, String>();
        for (Map.Entry<QName, String> attribute : tProperty.getOtherAttributes().entrySet()) {
            if (Edm.NAMESPACE_M_2007_08.equals(attribute.getKey().getNamespaceURI())) {
                if (EdmParserConstants.M_MIMETYPE.equals(attribute.getKey().getLocalPart())) {
                    property.setMimeType(attribute.getValue());
                } else {
                    fcAttributes.put(attribute.getKey().getLocalPart(), attribute.getValue());
                }
            } else {
                annotationAttributes.put(attribute.getKey(), attribute.getValue());
            }
        }
        property.setCustomizableFeedMappings(toCustomizableFeedMappings(fcAttributes));
        property.setAnnotationAttributes(toAnnotationAttributes(annotationAttributes));

        return property.setAnnotationElements(toAnnotationElements(tProperty));
    }

    public static Using toUsing(TUsing tUsing) {
        Using using = new Using();
        using.setNamespace(tUsing.getNamespace());
        using.setAlias(tUsing.getAlias());
        using.setAnnotationAttributes(toAnnotationAttributes(tUsing.getOtherAttributes()));

        return using;
    }

    public static ComplexType toComplexType(TComplexType tComplexType) throws EntityProviderException {
        ComplexType complexType = new ComplexType();
        complexType.setName(tComplexType.getName());
        complexType.setAnnotationAttributes(toAnnotationAttributes(tComplexType.getOtherAttributes()));

        List<Property> properties = new ArrayList<Property>();
        for (TComplexTypeProperty tProperty : tComplexType.getProperties()) {
            properties.add(toProperty(tProperty));
        }

        return complexType.setProperties(properties).setAnnotationElements(
                toAnnotationElements(tComplexType));
    }

    private static NavigationProperty toNavigationProperty(final TNavigationProperty tNavigationProperty)
            throws EntityProviderException {

        NavigationProperty navProperty = new NavigationProperty();
        navProperty.setName(tNavigationProperty.getName());
        navProperty.setFromRole(tNavigationProperty.getFromRole());
        navProperty.setToRole(tNavigationProperty.getToRole());

        FullQualifiedName relationship = extractFQName(tNavigationProperty.getRelationship());
        if (relationship == null) {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_NAVIGATION_RELATIONSHIP).
                    addContent(EdmParserConstants.EDM_NAVIGATION_PROPERTY));
        }
        navProperty.setRelationship(relationship);

        navProperty.setAnnotationAttributes(toAnnotationAttributes(tNavigationProperty.getOtherAttributes()));

        return navProperty.setAnnotationElements(toAnnotationElements(tNavigationProperty));
    }

    private static PropertyRef toPropertyRef(final TPropertyRef tPropertyRef) {
        PropertyRef propertyRef = new PropertyRef();
        propertyRef.setName(tPropertyRef.getName());
        return propertyRef;
    }

    private static Key toEntityTypeKey(final TEntityKeyElement tEntityKeyElement) {
        List<PropertyRef> keys = new ArrayList<PropertyRef>();
        for (TPropertyRef tPropertyRef : tEntityKeyElement.getPropertyRef()) {
            keys.add(toPropertyRef(tPropertyRef));
        }
        return new Key().setKeys(keys);
    }

    public static EntityType toEntityType(final TEntityType tEntityType) throws EntityProviderException {
        EntityType entityType = new EntityType();
        entityType.setName(tEntityType.getName());
        entityType.setHasStream(entityType.isHasStream());
        entityType.setAbstract(entityType.isAbstract());
        entityType.setBaseType(entityType.getBaseType());

        List<Property> properties = new ArrayList<Property>();
        for (TEntityProperty tProperty : tEntityType.getProperties()) {
            properties.add(toProperty(tProperty));
        }
        entityType.setProperties(properties);

        List<NavigationProperty> navProperties = new ArrayList<NavigationProperty>();
        for (TNavigationProperty tProperty : tEntityType.getNavigationProperties()) {
            navProperties.add(toNavigationProperty(tProperty));
        }
        entityType.setNavigationProperties(navProperties);

        if (tEntityType.getKey() != null) {
            Key key = toEntityTypeKey(tEntityType.getKey());
            entityType.setKey(key);
        }

        Map<String, String> fcAttributes = new HashMap<String, String>();
        Map<QName, String> annotationAttributes = new HashMap<QName, String>();
        for (Map.Entry<QName, String> attribute : tEntityType.getOtherAttributes().entrySet()) {
            if (Edm.NAMESPACE_M_2007_08.equals(attribute.getKey().getNamespaceURI())) {
                fcAttributes.put(attribute.getKey().getLocalPart(), attribute.getValue());
            } else {
                annotationAttributes.put(attribute.getKey(), attribute.getValue());
            }
        }
        entityType.setCustomizableFeedMappings(toCustomizableFeedMappings(fcAttributes));
        entityType.setAnnotationAttributes(toAnnotationAttributes(annotationAttributes));

        return entityType.setAnnotationElements(toAnnotationElements(tEntityType));
    }

    private static AssociationEnd toAssociationEnd(final TAssociationEnd tAssociationEnd) throws
            EntityProviderException {
        AssociationEnd associationEnd = new AssociationEnd();
        associationEnd.setRole(tAssociationEnd.getRole());
        associationEnd.setMultiplicity(EdmMultiplicity.fromLiteral(tAssociationEnd.getMultiplicity()));

        String type = tAssociationEnd.getType();
        if (type == null) {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_TYPE).
                    addContent(EdmParserConstants.EDM_ASSOCIATION_END));
        }
        associationEnd.setType(extractFQName(type));

        associationEnd.setAnnotationAttributes(toAnnotationAttributes(tAssociationEnd.getOtherAttributes()));

        if (!tAssociationEnd.getTOperations().isEmpty()) {
            TOnAction tOnAction = tAssociationEnd.getTOperations().get(0);
            OnDelete onDelete = new OnDelete();
            for (int i = 0; i < EdmAction.values().length; i++) {
                if (EdmAction.values()[i].name().equalsIgnoreCase(tOnAction.getAction().name())) {
                    onDelete.setAction(EdmAction.values()[i]);
                }
            }
            associationEnd.setOnDelete(onDelete);
        }

        return associationEnd;
    }

    public static Association toAssociation(final TAssociation tAssociation) throws EntityProviderException {
        Association association = new Association();
        association.setName(tAssociation.getName());
        association.setAnnotationAttributes(toAnnotationAttributes(tAssociation.getOtherAttributes()));

        List<TAssociationEnd> ends = tAssociation.getEnd();
        if (ends.size() != 2) {
            throw new EntityProviderException(
                    EntityProviderException.ILLEGAL_ARGUMENT.addContent("Count of association ends should be 2"));
        }

        return association.setEnd1(toAssociationEnd(ends.get(0))).setEnd2(toAssociationEnd(ends.get(1)));
    }

    private static EntitySet toEntitySet(final TEntitySet tEntitySet) throws EntityProviderException {
        EntitySet entitySet = new EntitySet();
        entitySet.setName(tEntitySet.getName());

        String entityType = tEntitySet.getEntityType();
        if (entityType != null) {
            FullQualifiedName fqName = extractFQName(entityType);
            entitySet.setEntityType(fqName);
        } else {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_ENTITY_TYPE).
                    addContent(EdmParserConstants.EDM_ENTITY_SET));
        }

        entitySet.setAnnotationAttributes(toAnnotationAttributes(tEntitySet.getOtherAttributes()));

        return entitySet.setAnnotationElements(toAnnotationElements(tEntitySet));
    }

    private static AssociationSet toAssociationSet(final TAssociationSet tAssociationSet) throws
            EntityProviderException {
        AssociationSet associationSet = new AssociationSet();
        associationSet.setName(tAssociationSet.getName());

        String association = tAssociationSet.getAssociation();
        if (association != null) {
            associationSet.setAssociation(extractFQName(association));
        } else {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_ASSOCIATION).
                    addContent(EdmParserConstants.EDM_ASSOCIATION_SET));
        }

        associationSet.setAnnotationAttributes(toAnnotationAttributes(tAssociationSet.getOtherAttributes()));

        if (tAssociationSet.getEnd().size() != 2) {
            throw new EntityProviderException(EntityProviderException.ILLEGAL_ARGUMENT.
                    addContent("Count of AssociationSet ends should be 2"));
        }

        AssociationSetEnd associationSetEnd = new AssociationSetEnd();
        associationSetEnd.setEntitySet(tAssociationSet.getEnd().get(0).getEntitySet());
        associationSetEnd.setRole(tAssociationSet.getEnd().get(0).getRole());
        associationSet.setEnd1(associationSetEnd);

        associationSetEnd = new AssociationSetEnd();
        associationSetEnd.setEntitySet(tAssociationSet.getEnd().get(1).getEntitySet());
        associationSetEnd.setRole(tAssociationSet.getEnd().get(1).getRole());
        associationSet.setEnd2(associationSetEnd);

        return associationSet;
    }

    private static FunctionImportParameter toFunctionImportParameter(
            final TFunctionImportParameter tFunctionImportParameter)
            throws EntityProviderException {

        FunctionImportParameter functionParameter = new FunctionImportParameter();
        functionParameter.setName(tFunctionImportParameter.getName());

        String type = tFunctionImportParameter.getType();
        if (type == null) {
            throw new EntityProviderException(EntityProviderException.MISSING_ATTRIBUTE.
                    addContent(EdmParserConstants.EDM_TYPE).
                    addContent(EdmParserConstants.EDM_FUNCTION_PARAMETER));
        }
        // TODO: FunctionImport might return complex types, or even collections
        try {
            functionParameter.setType(EdmSimpleTypeKind.valueOf(extractFQName(type).getName()));
        } catch (IllegalArgumentException e) {
            // ignore
        }

        functionParameter.setFacets(toFacets(tFunctionImportParameter));

        functionParameter.setAnnotationAttributes(
                toAnnotationAttributes(tFunctionImportParameter.getOtherAttributes()));

        functionParameter.setAnnotationElements(toAnnotationElements(tFunctionImportParameter));
        return functionParameter;
    }

    private static FunctionImport toFunctionImport(final TFunctionImport tFunctionImport)
            throws EntityProviderException {

        FunctionImport function = new FunctionImport();
        function.setName(tFunctionImport.getName());
        function.setEntitySet(tFunctionImport.getEntitySet());

        Map<QName, String> annotationAttributes = new HashMap<QName, String>();
        for (Map.Entry<QName, String> attribute : tFunctionImport.getOtherAttributes().entrySet()) {
            if (Edm.NAMESPACE_M_2007_08.equals(attribute.getKey().getNamespaceURI())
                    && EdmParserConstants.EDM_FUNCTION_IMPORT_HTTP_METHOD.equals(attribute.getKey().getLocalPart())) {

                function.setHttpMethod(attribute.getValue());
            } else {
                annotationAttributes.put(attribute.getKey(), attribute.getValue());
            }
        }
        function.setAnnotationAttributes(toAnnotationAttributes(annotationAttributes));

        ReturnType returnType = new ReturnType();
        String returnTypeString = tFunctionImport.getFunctionImportReturnType();
        if (returnTypeString != null) {
            if (returnTypeString.startsWith("Collection") || returnTypeString.startsWith("collection")) {
                returnType.setMultiplicity(EdmMultiplicity.MANY);
                returnTypeString = returnTypeString.substring(returnTypeString.indexOf("(") + 1,
                        returnTypeString.length() - 1);
            }
            FullQualifiedName fqName = extractFQName(returnTypeString);
            returnType.setTypeName(fqName);
            function.setReturnType(returnType);
        }

        List<FunctionImportParameter> functionParameters = new ArrayList<FunctionImportParameter>();
        for (TFunctionImportParameter tFunctionImportParameter : tFunctionImport.getParameters()) {
            functionParameters.add(toFunctionImportParameter(tFunctionImportParameter));
        }
        function.setParameters(functionParameters);

        return function.setAnnotationElements(toAnnotationElements(tFunctionImport));
    }

    public static EntityContainer toEntityContainer(final TEntityContainer tEntityContainer)
            throws EntityProviderException {

        EntityContainer container = new EntityContainer();
        container.setName(tEntityContainer.getName());
        container.setExtendz(tEntityContainer.getExtends());

        Map<QName, String> annotationAttributes = new HashMap<QName, String>();
        for (Map.Entry<QName, String> attribute : tEntityContainer.getOtherAttributes().entrySet()) {
            if (Edm.NAMESPACE_M_2007_08.equals(attribute.getKey().getNamespaceURI())
                    && "IsDefaultEntityContainer".equals(attribute.getKey().getLocalPart())) {

                container.setDefaultEntityContainer("true".equalsIgnoreCase(attribute.getValue()));
            } else {
                annotationAttributes.put(attribute.getKey(), attribute.getValue());
            }
        }
        container.setAnnotationAttributes(toAnnotationAttributes(annotationAttributes));

        List<EntitySet> entitySets = new ArrayList<EntitySet>();
        for (TEntitySet tEntitySet : tEntityContainer.getEntitySets()) {
            entitySets.add(toEntitySet(tEntitySet));
        }
        container.setEntitySets(entitySets);

        List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
        for (TAssociationSet tAssociationSet : tEntityContainer.getAssociationSets()) {
            associationSets.add(toAssociationSet(tAssociationSet));
        }
        container.setAssociationSets(associationSets);

        List<FunctionImport> functionImports = new ArrayList<FunctionImport>();
        for (TFunctionImport tFunctionImport : tEntityContainer.getFunctionImports()) {
            functionImports.add(toFunctionImport(tFunctionImport));
        }
        container.setFunctionImports(functionImports);

        container.setAnnotationElements(toAnnotationElements(tEntityContainer));

        return container;
    }
}
