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
package com.msopentech.odatajclient.engine.data.metadata.edmx;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.microsoft.schemas.ado._2007._06.edmx package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups. Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Edmx_QNAME = new QName("http://schemas.microsoft.com/ado/2007/06/edmx", "Edmx");

    private final static QName _TEdmxDesigner_QNAME = new QName("http://schemas.microsoft.com/ado/2007/06/edmx",
            "Designer");

    private final static QName _TEdmxDataServices_QNAME = new QName("http://schemas.microsoft.com/ado/2007/06/edmx",
            "DataServices");

    private final static QName _TEdmxRuntime_QNAME = new QName("http://schemas.microsoft.com/ado/2007/06/edmx",
            "Runtime");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * com.microsoft.schemas.ado._2007._06.edmx
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TOptions }
     *
     */
    public Options createTOptions() {
        return new Options();
    }

    /**
     * Create an instance of {@link TRuntimeStorageModels }
     *
     */
    public RuntimeStorageModels createTRuntimeStorageModels() {
        return new RuntimeStorageModels();
    }

    /**
     * Create an instance of {@link TEntityTypeShape }
     *
     */
    public EntityTypeShape createTEntityTypeShape() {
        return new EntityTypeShape();
    }

    /**
     * Create an instance of {@link TConnection }
     *
     */
    public Connection createTConnection() {
        return new Connection();
    }

    /**
     * Create an instance of {@link TEdmx }
     *
     */
    public Edmx createTEdmx() {
        return new Edmx();
    }

    /**
     * Create an instance of {@link TDataServices }
     *
     */
    public DataServices createTDataServices() {
        return new DataServices();
    }

    /**
     * Create an instance of {@link TDiagram }
     *
     */
    public Diagram createTDiagram() {
        return new Diagram();
    }

    /**
     * Create an instance of {@link TConnectorPoint }
     *
     */
    public ConnectorPoint createTConnectorPoint() {
        return new ConnectorPoint();
    }

    /**
     * Create an instance of {@link TDesignerInfoPropertySet }
     *
     */
    public DesignerInfoPropertySet createTDesignerInfoPropertySet() {
        return new DesignerInfoPropertySet();
    }

    /**
     * Create an instance of {@link TAssociationConnector }
     *
     */
    public AssociationConnector createTAssociationConnector() {
        return new AssociationConnector();
    }

    /**
     * Create an instance of {@link TRuntimeConceptualModels }
     *
     */
    public RuntimeConceptualModels createTRuntimeConceptualModels() {
        return new RuntimeConceptualModels();
    }

    /**
     * Create an instance of {@link TRuntime }
     *
     */
    public Runtime createTRuntime() {
        return new Runtime();
    }

    /**
     * Create an instance of {@link TDiagrams }
     *
     */
    public Diagrams createTDiagrams() {
        return new Diagrams();
    }

    /**
     * Create an instance of {@link TDesigner }
     *
     */
    public Designer createTDesigner() {
        return new Designer();
    }

    /**
     * Create an instance of {@link TRuntimeMappings }
     *
     */
    public RuntimeMappings createTRuntimeMappings() {
        return new RuntimeMappings();
    }

    /**
     * Create an instance of {@link TInheritanceConnector }
     *
     */
    public InheritanceConnector createTInheritanceConnector() {
        return new InheritanceConnector();
    }

    /**
     * Create an instance of {@link TDesignerProperty }
     *
     */
    public DesignerProperty createTDesignerProperty() {
        return new DesignerProperty();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TEdmx }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Edmx")
    public JAXBElement<Edmx> createEdmx(Edmx value) {
        return new JAXBElement<Edmx>(_Edmx_QNAME, Edmx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TDesigner }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Designer", scope = Edmx.class)
    public JAXBElement<Designer> createTEdmxDesigner(Designer value) {
        return new JAXBElement<Designer>(_TEdmxDesigner_QNAME, Designer.class, Edmx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TDataServices }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "DataServices", scope =
            Edmx.class)
    public JAXBElement<DataServices> createTEdmxDataServices(DataServices value) {
        return new JAXBElement<DataServices>(_TEdmxDataServices_QNAME, DataServices.class, Edmx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TRuntime }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Runtime", scope = Edmx.class)
    public JAXBElement<Runtime> createTEdmxRuntime(Runtime value) {
        return new JAXBElement<Runtime>(_TEdmxRuntime_QNAME, Runtime.class, Edmx.class, value);
    }
}
