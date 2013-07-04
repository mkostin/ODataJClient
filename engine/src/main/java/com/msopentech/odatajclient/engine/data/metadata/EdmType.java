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
package com.msopentech.odatajclient.engine.data.metadata;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import com.msopentech.odatajclient.engine.data.metadata.edm.ComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EnumType;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import org.apache.commons.lang3.StringUtils;

/**
 * Parse type information from metadata into semantic data.
 */
public class EdmType {

    private final String typeExpression;

    private final String baseType;

    private boolean collection;

    private EdmSimpleType simpleType;

    private EnumType enumType;

    private ComplexType complexType;

    private EntityType entityType;

    public EdmType(final String typeExpression) {
        this(null, typeExpression);
    }

    public EdmType(final EdmMetadata metadata, final String typeExpression) {
        this.typeExpression = typeExpression;

        final int collectionStartIdx = typeExpression.indexOf("Collection(");
        final int collectionEndIdx = typeExpression.lastIndexOf(')');
        if (collectionStartIdx == -1) {
            baseType = typeExpression;
        } else {
            if (collectionEndIdx == -1) {
                throw new IllegalArgumentException("Malformed type: " + typeExpression);
            }

            this.collection = true;
            baseType = typeExpression.substring(collectionStartIdx + 11, collectionEndIdx);
        }

        final int lastDotIdx = baseType.lastIndexOf('.');
        if (lastDotIdx == -1) {
            throw new IllegalArgumentException("Cannot find namespace or alias in " + typeExpression);
        }
        final String namespaceOrAlias = baseType.substring(0, lastDotIdx);
        final String typeName = baseType.substring(lastDotIdx + 1);
        if (StringUtils.isBlank(typeName)) {
            throw new IllegalArgumentException("Null or empty type name in " + typeExpression);
        }

        if (namespaceOrAlias.equals(EdmSimpleType.namespace())) {
            this.simpleType = EdmSimpleType.fromValue(typeName);
        } else if (metadata != null) {
            if (!metadata.isNsOrAlias(namespaceOrAlias)) {
                throw new IllegalArgumentException("Illegal namespace or alias: " + namespaceOrAlias);
            }
            final Schema schema = metadata.getSchema(namespaceOrAlias);

            for (EnumType type : schema.getEnumTypes()) {
                if (typeName.equals(type.getName())) {
                    this.enumType = type;
                }
            }
            if (this.enumType == null) {
                for (ComplexType type : schema.getComplexTypes()) {
                    if (typeName.equals(type.getName())) {
                        this.complexType = type;
                    }
                }
                if (this.complexType == null) {
                    for (EntityType type : schema.getEntityTypes()) {
                        if (typeName.equals(type.getName())) {
                            this.entityType = type;
                        }
                    }
                }
            }

            if (!isSimpleType() && !isEnumType() && !isComplexType() && !isEntityType()) {
                throw new IllegalArgumentException("Could not parse type information out of " + typeExpression);
            }
        }
    }

    public final boolean isCollection() {
        return this.collection;
    }

    public final boolean isSimpleType() {
        return this.simpleType != null;
    }

    public final EdmSimpleType getSimpleType() {
        if (!isSimpleType()) {
            throw new EdmTypeNotFoundException(EdmSimpleType.class, this.typeExpression);
        }

        return this.simpleType;
    }

    public final boolean isEnumType() {
        return this.enumType != null;
    }

    public final EnumType getEnumType() {
        if (!isEnumType()) {
            throw new EdmTypeNotFoundException(EnumType.class, this.typeExpression);
        }

        return this.enumType;
    }

    public final boolean isComplexType() {
        return this.complexType != null;
    }

    public final ComplexType getComplexType() {
        if (!isComplexType()) {
            throw new EdmTypeNotFoundException(ComplexType.class, this.typeExpression);
        }

        return this.complexType;
    }

    public final boolean isEntityType() {
        return this.entityType != null;
    }

    public final EntityType getEntityType() {
        if (!isEntityType()) {
            throw new EdmTypeNotFoundException(EntityType.class, this.typeExpression);
        }

        return this.entityType;
    }

    public String getBaseType() {
        return baseType;
    }

    public String getTypeExpression() {
        return typeExpression;
    }
}
