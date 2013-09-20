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
package com.msopentech.odatajclient.plugin;

import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractAnnotated;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.AssociationEnd;
import com.msopentech.odatajclient.engine.data.metadata.edm.Documentation;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.EntitySet;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.OnAction;
import com.msopentech.odatajclient.engine.data.metadata.edm.PropertyRef;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.commons.lang.StringUtils;

public class Utility {

    private static final String FC_TARGET_PATH = "FC_TargetPath";

    private static final String FC_TARGET_PATH_ANN = "fcTargetPath";

    private static final String FC_SOURCE_PATH = "FC_SourcePath";

    private static final String FC_SOURCE_PATH_ANN = "fcSourcePath";

    private static final String FC_KEEP_IN_CONTENT = "FC_KeepInContent";

    private static final String FC_KEEP_IN_CONTENT_ANN = "fcKeepInContent";

    private static final String FC_CONTENT_KIND = "FC_ContentKind";

    private static final String FC_CONTENT_KIND_ANN = "fcContentKind";

    private static final String FC_NS_PREFIX = "FC_NSPrefix";

    private static final String FC_NS_PREFIX_ANN = "fcNSPrefix";

    private static final String FC_NS_URI = "FC_NSURI";

    private static final String FC_NS_URI_ANN = "fcNSURI";

    private static final String MIME_TYPE = "MimeType";

    private static final String MIME_TYPE_ANN = "mimeType";

    private static final String TYPE_SUB_PKG = "types";

    private final EdmMetadata metadata;

    private final Schema schema;

    private final String basePackage;

    private final String schemaName;

    private final String namespace;

    public Utility(final EdmMetadata metadata, final Schema schema, final String basePackage) {
        this.metadata = metadata;
        this.schema = schema;
        this.basePackage = basePackage;
        this.namespace = schema.getNamespace();
        this.schemaName = schema.getAlias() == null ? getNameFromNS(namespace) : schema.getAlias();
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String capitalize(final String str) {
        return StringUtils.capitalize(str);
    }

    public String uncapitalize(final String str) {
        return StringUtils.uncapitalize(str);
    }

    public String getJavaType(final String typeExpression) {
        final StringBuilder res = new StringBuilder();

        final EdmType edmType = new EdmType(metadata, typeExpression);

        if (edmType.isCollection() && !edmType.isEntityType()) {
            res.append("Collection<");
        }

        if ("Edm.Stream".equals(typeExpression)) {
            res.append(InputStream.class.getName());
        } else if (edmType.isSimpleType()) {
            res.append(edmType.getSimpleType().javaType().getSimpleName());
        } else if (edmType.isComplexType()) {
            res.append(basePackage).append('.').append(edmType.getNamespaceOrAlias().toLowerCase()).append('.').
                    append(TYPE_SUB_PKG).append('.').append(capitalize(edmType.getComplexType().getName()));
        } else if (edmType.isEntityType()) {
            res.append(basePackage).append('.').append(edmType.getNamespaceOrAlias().toLowerCase()).append('.').
                    append(TYPE_SUB_PKG).append('.').append(capitalize(edmType.getEntityType().getName()));
        } else if (edmType.isEnumType()) {
            res.append(basePackage).append('.').append(edmType.getNamespaceOrAlias().toLowerCase()).
                    append('.').append(TYPE_SUB_PKG).append('.').append(capitalize(edmType.getEnumType().getName()));
        } else {
            throw new IllegalArgumentException("Invalid type expression '" + typeExpression + "'");
        }

        if (edmType.isCollection()) {
            if (edmType.isEntityType()) {
                res.append("Collection");
            } else {
                res.append(">");
            }
        }

        return res.toString();
    }

    public Map<String, String> getFcProperties(final AbstractAnnotated annotated) {
        final Map<String, String> res = new HashMap<String, String>();

        for (Map.Entry<QName, String> fcprop : annotated instanceof EntityType
                ? ((EntityType) annotated).getOtherAttributes().entrySet()
                : ((EntityProperty) annotated).getOtherAttributes().entrySet()) {
            if (FC_TARGET_PATH.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_TARGET_PATH_ANN, fcprop.getValue());
            } else if (FC_SOURCE_PATH.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_SOURCE_PATH_ANN, fcprop.getValue());
            } else if (FC_NS_PREFIX.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_NS_PREFIX_ANN, fcprop.getValue());
            } else if (FC_NS_URI.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_NS_URI_ANN, fcprop.getValue());
            } else if (FC_CONTENT_KIND.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_CONTENT_KIND_ANN, fcprop.getValue());
            } else if (FC_KEEP_IN_CONTENT.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(FC_KEEP_IN_CONTENT_ANN, fcprop.getValue());
            } else if (MIME_TYPE.equalsIgnoreCase(fcprop.getKey().getLocalPart())) {
                res.put(MIME_TYPE_ANN, fcprop.getValue());
            }
        }

        return res;
    }

    public EntityType getEntityType(final EntitySet entitySet) {
        return new EdmType(metadata, entitySet.getEntityType()).getEntityType();
    }

    public Map<String, String> getEntityKeyType(final EntitySet entitySet) {
        return getEntityKeyType(getEntityType(entitySet));
    }

    public Map<String, String> getEntityKeyType(final EntityType entityType) {
        EntityType baseType = entityType;
        while (baseType.getKey() == null && baseType.getBaseType() != null) {
            baseType = new EdmType(metadata, baseType.getBaseType()).getEntityType();
        }

        final List<String> properties = new ArrayList<String>();
        for (PropertyRef pref : baseType.getKey().getPropertyRef()) {
            properties.add(pref.getName());
        }

        final Map<String, String> res = new HashMap<String, String>();

        for (EntityProperty prop : baseType.getProperties()) {
            if (properties.contains(prop.getName())) {
                res.put(prop.getName(), getJavaType(prop.getType()));
            }
        }

        return res;
    }

    public Map.Entry<String, Action> getNavigationRoleType(final String associationName, final String associationRole) {
        final String name = getNameFromNS(associationName);
        final Association association = schema.getAssociation(name);

        if (association != null) {
            for (AssociationEnd end : association.getEnd()) {
                if (end.getRole().equalsIgnoreCase(associationRole)) {
                    final List<OnAction> actions = end.getTOperations();
                    return new AbstractMap.SimpleEntry<String, Action>(
                            "*".equals(end.getMultiplicity())
                            ? "Collection(" + end.getType() + ")"
                            : end.getType(),
                            actions.isEmpty()
                            ? Action.NONE
                            : actions.get(0).getAction());
                }
            }
        }

        return new AbstractMap.SimpleEntry<String, Action>(associationRole, Action.NONE);
    }

    public final String getNameInNamespace(final String name) {
        return schema.getNamespace() + "." + name;
    }

    public final String getNameFromNS(final String ns) {
        return getNameFromNS(ns, false);
    }

    public final String getNameFromNS(final String ns, final boolean toLowerCase) {
        final int lastpt = ns.lastIndexOf('.');
        final String res = ns.substring(lastpt < 0 ? 0 : lastpt + 1);
        return toLowerCase ? res.toLowerCase() : res;
    }

    public boolean isSameType(
            final String entityTypeExpression, final String fullTypeExpression, final boolean collection) {

        final Set<String> types = new HashSet<String>(2);

        types.add((collection ? "Collection(" : StringUtils.EMPTY)
                + getNameInNamespace(entityTypeExpression)
                + (collection ? ")" : StringUtils.EMPTY));
        if (StringUtils.isNotBlank(schema.getAlias())) {
            types.add((collection ? "Collection(" : StringUtils.EMPTY)
                    + schema.getAlias() + "." + entityTypeExpression
                    + (collection ? ")" : StringUtils.EMPTY));
        }

        return types.contains(fullTypeExpression);
    }

    private void populateDescendants(final EntityType base, final List<String> descendants) {
        for (EntityType type : schema.getEntityTypes()) {
            if (StringUtils.isNotBlank(type.getBaseType())
                    && base.getName().equals(getNameFromNS(type.getBaseType()))) {

                descendants.add(getNameInNamespace(type.getName()));
                populateDescendants(type, descendants);
            }
        }

    }

    public List<String> getDescendantsOrSelf(final EntityType base) {
        final List<String> descendants = new ArrayList<String>();

        descendants.add(getNameInNamespace(base.getName()));
        populateDescendants(base, descendants);

        return descendants;
    }

    public List<EntityContainer.FunctionImport> getFunctionImportsBoundTo(
            final String typeExpression, final boolean collection) {

        final List<EntityContainer.FunctionImport> result = new ArrayList<EntityContainer.FunctionImport>();

        for (EntityContainer entityContainer : schema.getEntityContainers()) {
            for (EntityContainer.FunctionImport functionImport : entityContainer.getFunctionImports()) {
                if (functionImport.isIsBindable()) {
                    for (int i = 0; i < functionImport.getParameters().size(); i++) {
                        if (isSameType(typeExpression, functionImport.getParameters().get(i).getType(), collection)) {
                            result.add(functionImport);
                        }
                    }
                }
            }
        }

        return result;
    }

    public static Map.Entry<String, String> getDocumentation(final Object obj) {
        final String summary;
        final String description;

        try {
            final Method method = obj.getClass().getMethod("getDocumentation");

            if (method == null || method.getReturnType() != Documentation.class) {
                throw new Exception("Documentation not found");
            }

            final Documentation doc = (Documentation) method.invoke(obj);

            summary = doc.getSummary() == null ? "" : doc.getSummary().getContentAsString();
            description = doc.getLongDescription() == null ? "" : doc.getLongDescription().getContentAsString();

            return new AbstractMap.SimpleEntry<String, String>(summary, description);
        } catch (Exception e) {
            return null;
        }

    }
}
