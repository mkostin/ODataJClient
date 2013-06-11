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
    public TOptions createTOptions() {
        return new TOptions();
    }

    /**
     * Create an instance of {@link TRuntimeStorageModels }
     *
     */
    public TRuntimeStorageModels createTRuntimeStorageModels() {
        return new TRuntimeStorageModels();
    }

    /**
     * Create an instance of {@link TEntityTypeShape }
     *
     */
    public TEntityTypeShape createTEntityTypeShape() {
        return new TEntityTypeShape();
    }

    /**
     * Create an instance of {@link TConnection }
     *
     */
    public TConnection createTConnection() {
        return new TConnection();
    }

    /**
     * Create an instance of {@link TEdmx }
     *
     */
    public TEdmx createTEdmx() {
        return new TEdmx();
    }

    /**
     * Create an instance of {@link TDataServices }
     *
     */
    public TDataServices createTDataServices() {
        return new TDataServices();
    }

    /**
     * Create an instance of {@link TDiagram }
     *
     */
    public TDiagram createTDiagram() {
        return new TDiagram();
    }

    /**
     * Create an instance of {@link TConnectorPoint }
     *
     */
    public TConnectorPoint createTConnectorPoint() {
        return new TConnectorPoint();
    }

    /**
     * Create an instance of {@link TDesignerInfoPropertySet }
     *
     */
    public TDesignerInfoPropertySet createTDesignerInfoPropertySet() {
        return new TDesignerInfoPropertySet();
    }

    /**
     * Create an instance of {@link TAssociationConnector }
     *
     */
    public TAssociationConnector createTAssociationConnector() {
        return new TAssociationConnector();
    }

    /**
     * Create an instance of {@link TRuntimeConceptualModels }
     *
     */
    public TRuntimeConceptualModels createTRuntimeConceptualModels() {
        return new TRuntimeConceptualModels();
    }

    /**
     * Create an instance of {@link TRuntime }
     *
     */
    public TRuntime createTRuntime() {
        return new TRuntime();
    }

    /**
     * Create an instance of {@link TDiagrams }
     *
     */
    public TDiagrams createTDiagrams() {
        return new TDiagrams();
    }

    /**
     * Create an instance of {@link TDesigner }
     *
     */
    public TDesigner createTDesigner() {
        return new TDesigner();
    }

    /**
     * Create an instance of {@link TRuntimeMappings }
     *
     */
    public TRuntimeMappings createTRuntimeMappings() {
        return new TRuntimeMappings();
    }

    /**
     * Create an instance of {@link TInheritanceConnector }
     *
     */
    public TInheritanceConnector createTInheritanceConnector() {
        return new TInheritanceConnector();
    }

    /**
     * Create an instance of {@link TDesignerProperty }
     *
     */
    public TDesignerProperty createTDesignerProperty() {
        return new TDesignerProperty();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TEdmx }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Edmx")
    public JAXBElement<TEdmx> createEdmx(TEdmx value) {
        return new JAXBElement<TEdmx>(_Edmx_QNAME, TEdmx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TDesigner }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Designer", scope = TEdmx.class)
    public JAXBElement<TDesigner> createTEdmxDesigner(TDesigner value) {
        return new JAXBElement<TDesigner>(_TEdmxDesigner_QNAME, TDesigner.class, TEdmx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TDataServices }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "DataServices", scope =
            TEdmx.class)
    public JAXBElement<TDataServices> createTEdmxDataServices(TDataServices value) {
        return new JAXBElement<TDataServices>(_TEdmxDataServices_QNAME, TDataServices.class, TEdmx.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TRuntime }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/ado/2007/06/edmx", name = "Runtime", scope = TEdmx.class)
    public JAXBElement<TRuntime> createTEdmxRuntime(TRuntime value) {
        return new JAXBElement<TRuntime>(_TEdmxRuntime_QNAME, TRuntime.class, TEdmx.class, value);
    }
}
