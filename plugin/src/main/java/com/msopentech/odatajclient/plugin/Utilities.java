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
package com.msopentech.odatajclient.plugin;

import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.EdmType;
import com.msopentech.odatajclient.engine.data.metadata.edm.AbstractAnnotated;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.engine.data.metadata.edm.Association;
import com.msopentech.odatajclient.engine.data.metadata.edm.AssociationEnd;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.EntitySet;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityProperty;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.OnAction;
import com.msopentech.odatajclient.engine.data.metadata.edm.PropertyRef;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.commons.lang.StringUtils;

public class Utilities {

    private final String FC_TARGET_PATH = "FC_TargetPath";

    private final String FC_TARGET_PATH_ANN = "fcTargetPath";

    private final String FC_SOURCE_PATH = "FC_SourcePath";

    private final String FC_SOURCE_PATH_ANN = "fcSourcePath";

    private final String FC_KEEP_IN_CONTENT = "FC_KeepInContent";

    private final String FC_KEEP_IN_CONTENT_ANN = "fcKeepInContent";

    private final String FC_CONTENT_KIND = "FC_ContentKind";

    private final String FC_CONTENT_KIND_ANN = "fcContentKind";

    private final String FC_NS_PREFIX = "FC_NSPrefix";

    private final String FC_NS_PREFIX_ANN = "fcNSPrefix";

    private final String FC_NS_URI = "FC_NSURI";

    private final String FC_NS_URI_ANN = "fcNSURI";

    private final String MIME_TYPE = "MimeType";

    private final String MIME_TYPE_ANN = "mimeType";

    private final String TYPE_SUB_PKG = "types";

    final EdmMetadata metadata;

    final Schema schema;

    final String basePackage;

    final String schemaName;

    final String namespace;

    public Utilities(final EdmMetadata metadata, final Schema schema, final String basePackage) {
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

        if (edmType.isCollection()) {
            res.append("Collection<");
        }

        if (edmType.isSimpleType()) {
            res.append(edmType.getSimpleType().javaType().getSimpleName());
        } else if (edmType.isComplexType()) {
            res.append(basePackage).append('.').append(schemaName).append('.').append(TYPE_SUB_PKG).append('.').
                    append(capitalize(edmType.getComplexType().getName()));
        } else if (edmType.isEntityType()) {
            res.append(basePackage).append('.').append(schemaName).append('.').append(TYPE_SUB_PKG).append('.').
                    append(capitalize(edmType.getEntityType().getName()));
        } else if (edmType.isEnumType()) {
            res.append(basePackage).append('.').append(schemaName).append('.').append(TYPE_SUB_PKG).append('.').
                    append(capitalize(edmType.getEnumType().getName()));
        } else {
            throw new IllegalArgumentException("Invalid type expression '" + typeExpression + "'");
        }

        if (edmType.isCollection()) {
            res.append(">");
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
        return schema.getEntityType(getNameFromNS(entitySet.getEntityType()));
    }

    public Map<String, String> getEntityKeyType(final EntitySet entitySet) {
        return getEntityKeyType(schema.getEntityType(getNameFromNS(entitySet.getEntityType())));
    }

    public Map<String, String> getEntityKeyType(final EntityType entityType) {
        final List<String> properties = new ArrayList<String>();
        for (PropertyRef pref : entityType.getKey().getPropertyRef()) {
            properties.add(pref.getName());
        }

        final Map<String, String> res = new HashMap<String, String>();

        for (EntityProperty prop : entityType.getProperties()) {
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
                            "*".equals(end.getMultiplicity()) ? "Collection(" + end.getType() + ")" : end.getType(),
                            actions.isEmpty() ? Action.NONE : actions.get(0).getAction());
                }
            }
        }

        return new AbstractMap.SimpleEntry<String, Action>(associationRole, Action.NONE);
    }

    public String getNameFromNS(final String ns) {
        return getNameFromNS(ns, false);
    }

    public String getNameFromNS(final String ns, final boolean toLowerCase) {
        final int lastpt = ns.lastIndexOf('.');
        final String res = ns.substring(lastpt < 0 ? 0 : lastpt + 1);
        return toLowerCase ? res.toLowerCase() : res;
    }
}
