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
import com.msopentech.odatajclient.engine.data.metadata.edm.TComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEnumType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TSchema;
import org.apache.commons.lang3.StringUtils;

/**
 * Parse type information from metadata into semantic data.
 */
public class EdmType {

    private final String typeExpression;

    private boolean collection;

    private EdmSimpleType simpleType;

    private TEnumType enumType;

    private TComplexType complexType;

    private TEntityType entityType;

    public EdmType(final String typeExpression) {
        this(null, typeExpression);
    }

    public EdmType(final EdmMetadata metadata, final String typeExpression) {
        this.typeExpression = typeExpression;

        String expression = typeExpression;
        int collectionStartIdx = expression.indexOf("Collection(");
        int collectionEndIdx = expression.lastIndexOf(')');
        if (collectionStartIdx != -1) {
            if (collectionEndIdx == -1) {
                throw new IllegalArgumentException("Malformed type: " + typeExpression);
            }

            this.collection = true;
            expression = expression.substring(collectionStartIdx + 11, collectionEndIdx);
        }

        int lastDotIdx = expression.lastIndexOf('.');
        if (lastDotIdx == -1) {
            throw new IllegalArgumentException("Cannot find namespace or alias in " + typeExpression);
        }
        String namespaceOrAlias = expression.substring(0, lastDotIdx);
        String typeName = expression.substring(lastDotIdx + 1);
        if (StringUtils.isBlank(typeName)) {
            throw new IllegalArgumentException("Null or empty type name in " + typeExpression);
        }

        if (namespaceOrAlias.equals(EdmSimpleType.namespace())) {
            this.simpleType = EdmSimpleType.fromValue(typeName);
        } else if (metadata != null) {
            if (!metadata.isNsOrAlias(namespaceOrAlias)) {
                throw new IllegalArgumentException("Illegal namespace or alias: " + namespaceOrAlias);
            }
            TSchema schema = metadata.getSchema(namespaceOrAlias);

            for (TEnumType type : schema.getEnumTypes()) {
                if (typeName.equals(type.getName())) {
                    this.enumType = type;
                }
            }
            if (this.enumType == null) {
                for (TComplexType type : schema.getComplexTypes()) {
                    if (typeName.equals(type.getName())) {
                        this.complexType = type;
                    }
                }
                if (this.complexType == null) {
                    for (TEntityType type : schema.getEntityTypes()) {
                        if (typeName.equals(type.getName())) {
                            this.entityType = type;
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Cannot parse to simple type and no metadata provided: " + typeExpression);
        }

        if (!isSimpleType() && !isEnumType() && !isComplexType() && !isEntityType()) {
            throw new IllegalArgumentException("Could not parse type information out of " + typeExpression);
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

    public final TEnumType getEnumType() {
        if (!isEnumType()) {
            throw new EdmTypeNotFoundException(TEnumType.class, this.typeExpression);
        }

        return this.enumType;
    }

    public final boolean isComplexType() {
        return this.complexType != null;
    }

    public final TComplexType getComplexType() {
        if (!isComplexType()) {
            throw new EdmTypeNotFoundException(TComplexType.class, this.typeExpression);
        }

        return this.complexType;
    }

    public final boolean isEntityType() {
        return this.entityType != null;
    }

    public final TEntityType getEntityType() {
        if (!isEntityType()) {
            throw new EdmTypeNotFoundException(TEntityType.class, this.typeExpression);
        }

        return this.entityType;
    }
}
