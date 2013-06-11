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

import com.msopentech.odatajclient.engine.data.metadata.edm.TAnnotations;
import com.msopentech.odatajclient.engine.data.metadata.edm.TAssociation;
import com.msopentech.odatajclient.engine.data.metadata.edm.TComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.TEntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.TSchema;
import com.sap.core.odata.api.edm.FullQualifiedName;
import com.sap.core.odata.api.edm.provider.AnnotationElement;
import com.sap.core.odata.api.edm.provider.Association;
import com.sap.core.odata.api.edm.provider.AssociationSet;
import com.sap.core.odata.api.edm.provider.ComplexType;
import com.sap.core.odata.api.edm.provider.EdmProvider;
import com.sap.core.odata.api.edm.provider.EntityContainer;
import com.sap.core.odata.api.edm.provider.EntityContainerInfo;
import com.sap.core.odata.api.edm.provider.EntitySet;
import com.sap.core.odata.api.edm.provider.EntityType;
import com.sap.core.odata.api.edm.provider.FunctionImport;
import com.sap.core.odata.api.edm.provider.Schema;
import com.sap.core.odata.api.exception.ODataException;
import java.util.ArrayList;
import java.util.List;

/**
 * SAP's EdmProvider implementation based on JAXB model objects.
 */
public class JAXBEdmProvider extends EdmProvider {

    private final List<TSchema> tschemas;

    private List<Schema> schemas;

    public JAXBEdmProvider(List<TSchema> tschemas) {
        super();

        this.tschemas = tschemas;
    }

    @Override
    public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {
        if (name != null) {
            for (Schema schema : getSchemas()) {
                for (EntityContainer container : schema.getEntityContainers()) {
                    if (container.getName().equals(name)) {
                        return container;
                    }
                }
            }
        } else {
            for (Schema schema : getSchemas()) {
                for (EntityContainer container : schema.getEntityContainers()) {
                    if (container.isDefaultEntityContainer()) {
                        return container;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {
        for (Schema schema : getSchemas()) {
            if (schema.getNamespace().equals(edmFQName.getNamespace())) {
                for (EntityType entityType : schema.getEntityTypes()) {
                    if (entityType.getName().equals(edmFQName.getName())) {
                        return entityType;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public ComplexType getComplexType(final FullQualifiedName edmFQName) throws ODataException {
        for (Schema schema : getSchemas()) {
            if (schema.getNamespace().equals(edmFQName.getNamespace())) {
                for (ComplexType complexType : schema.getComplexTypes()) {
                    if (complexType.getName().equals(edmFQName.getName())) {
                        return complexType;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Association getAssociation(final FullQualifiedName edmFQName) throws ODataException {
        for (Schema schema : getSchemas()) {
            if (schema.getNamespace().equals(edmFQName.getNamespace())) {
                for (Association association : schema.getAssociations()) {
                    if (association.getName().equals(edmFQName.getName())) {
                        return association;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {
        for (Schema schema : getSchemas()) {
            for (EntityContainer container : schema.getEntityContainers()) {
                if (container.getName().equals(entityContainer)) {
                    for (EntitySet entitySet : container.getEntitySets()) {
                        if (entitySet.getName().equals(name)) {
                            return entitySet;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public AssociationSet getAssociationSet(final String entityContainer, final FullQualifiedName association,
            final String sourceEntitySetName, final String sourceEntitySetRole) throws ODataException {

        for (Schema schema : getSchemas()) {
            for (EntityContainer container : schema.getEntityContainers()) {
                if (container.getName().equals(entityContainer)) {
                    for (AssociationSet associationSet : container.getAssociationSets()) {
                        if (associationSet.getAssociation().equals(association)
                                && ((associationSet.getEnd1().getEntitySet().equals(sourceEntitySetName)
                                && associationSet.getEnd1().getRole().equals(sourceEntitySetRole))
                                || (associationSet.getEnd2().getEntitySet().equals(sourceEntitySetName)
                                && associationSet.getEnd2().getRole().equals(sourceEntitySetRole)))) {
                            return associationSet;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public FunctionImport getFunctionImport(final String entityContainer, final String name) throws ODataException {
        for (Schema schema : getSchemas()) {
            for (EntityContainer container : schema.getEntityContainers()) {
                if (container.getName().equals(entityContainer)) {
                    for (FunctionImport function : container.getFunctionImports()) {
                        if (function.getName().equals(name)) {
                            return function;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Schema> getSchemas() throws ODataException {
        if (this.schemas != null) {
            return this.schemas;
        }

        this.schemas = new ArrayList<Schema>(this.tschemas.size());

        for (TSchema tschema : this.tschemas) {
            Schema schema = new Schema();
            schema.setNamespace(tschema.getNamespace());
            schema.setAlias(tschema.getAlias());
            schema.setAnnotationAttributes(JAXB2SAPBridge.toAnnotationAttributes(tschema.getOtherAttributes()));

            List<ComplexType> complexTypes = new ArrayList<ComplexType>();
            for (TComplexType tComplexType : tschema.getComplexTypes()) {
                complexTypes.add(JAXB2SAPBridge.toComplexType(tComplexType));
            }
            schema.setComplexTypes(complexTypes);

            List<EntityType> entityTypes = new ArrayList<EntityType>();
            for (TEntityType tEntityType : tschema.getEntityTypes()) {
                entityTypes.add(JAXB2SAPBridge.toEntityType(tEntityType));
            }
            schema.setEntityTypes(entityTypes);

            List<Association> associations = new ArrayList<Association>();
            for (TAssociation tAssociation : tschema.getAssociations()) {
                associations.add(JAXB2SAPBridge.toAssociation(tAssociation));
            }
            schema.setAssociations(associations);

            List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
            for (TEntityContainer tEntityContainer : tschema.getEntityContainers()) {
                entityContainers.add(JAXB2SAPBridge.toEntityContainer(tEntityContainer));
            }
            schema.setEntityContainers(entityContainers);

            List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
            for (TAnnotations tAnnotation : tschema.getAnnotations()) {
            }
            schema.setAnnotationElements(annotationElements);

            schemas.add(schema);
        }

        return schemas;
    }
}
