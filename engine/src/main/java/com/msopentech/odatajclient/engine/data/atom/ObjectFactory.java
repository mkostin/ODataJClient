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
package com.msopentech.odatajclient.engine.data.atom;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.w3._2005.atom package.
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

    private final static QName _Title_QNAME = new QName("http://www.w3.org/2005/Atom", "title");

    private final static QName _Author_QNAME = new QName("http://www.w3.org/2005/Atom", "author");

    private final static QName _Summary_QNAME = new QName("http://www.w3.org/2005/Atom", "summary");

    private final static QName _Entry_QNAME = new QName("http://www.w3.org/2005/Atom", "entry");

    private final static QName _Name_QNAME = new QName("http://www.w3.org/2005/Atom", "name");

    private final static QName _Contributor_QNAME = new QName("http://www.w3.org/2005/Atom", "contributor");

    private final static QName _Email_QNAME = new QName("http://www.w3.org/2005/Atom", "email");

    private final static QName _Updated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");

    private final static QName _Feed_QNAME = new QName("http://www.w3.org/2005/Atom", "feed");

    private final static QName _Subtitle_QNAME = new QName("http://www.w3.org/2005/Atom", "subtitle");

    private final static QName _Rights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");

    private final static QName _Uri_QNAME = new QName("http://www.w3.org/2005/Atom", "uri");

    private final static QName _Published_QNAME = new QName("http://www.w3.org/2005/Atom", "published");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * org.w3._2005.atom
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AtomTextConstruct }
     *
     */
    public AtomText createAtomTextConstruct() {
        return new AtomText();
    }

    /**
     * Create an instance of {@link Icon }
     *
     */
    public Icon createIcon() {
        return new Icon();
    }

    /**
     * Create an instance of {@link Content }
     *
     */
    public AtomContent createContent() {
        return new AtomContent();
    }

    /**
     * Create an instance of {@link UndefinedContent }
     *
     */
    public UndefinedContent createUndefinedContent() {
        return new UndefinedContent();
    }

    /**
     * Create an instance of {@link AtomPersonConstruct }
     *
     */
    public AtomPerson createAtomPersonConstruct() {
        return new AtomPerson();
    }

    /**
     * Create an instance of {@link Generator }
     *
     */
    public Generator createGenerator() {
        return new Generator();
    }

    /**
     * Create an instance of {@link EntryType }
     *
     */
    public AtomEntry createEntryType() {
        return new AtomEntry();
    }

    /**
     * Create an instance of {@link Category }
     *
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Source }
     *
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link Link }
     *
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link AtomDateConstruct }
     *
     */
    public AtomDate createAtomDateConstruct() {
        return new AtomDate();
    }

    /**
     * Create an instance of {@link Id }
     *
     */
    public Id createId() {
        return new Id();
    }

    /**
     * Create an instance of {@link Logo }
     *
     */
    public Logo createLogo() {
        return new Logo();
    }

    /**
     * Create an instance of {@link FeedType }
     *
     */
    public AtomFeed createFeedType() {
        return new AtomFeed();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title")
    public JAXBElement<AtomText> createTitle(AtomText value) {
        return new JAXBElement<AtomText>(_Title_QNAME, AtomText.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author")
    public JAXBElement<AtomPerson> createAuthor(AtomPerson value) {
        return new JAXBElement<AtomPerson>(_Author_QNAME, AtomPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "summary")
    public JAXBElement<AtomText> createSummary(AtomText value) {
        return new JAXBElement<AtomText>(_Summary_QNAME, AtomText.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntryType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "entry")
    public JAXBElement<AtomEntry> createEntry(AtomEntry value) {
        return new JAXBElement<AtomEntry>(_Entry_QNAME, AtomEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor")
    public JAXBElement<AtomPerson> createContributor(AtomPerson value) {
        return new JAXBElement<AtomPerson>(_Contributor_QNAME, AtomPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated")
    public JAXBElement<AtomDate> createUpdated(AtomDate value) {
        return new JAXBElement<AtomDate>(_Updated_QNAME, AtomDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeedType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "feed")
    public JAXBElement<AtomFeed> createFeed(AtomFeed value) {
        return new JAXBElement<AtomFeed>(_Feed_QNAME, AtomFeed.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "subtitle")
    public JAXBElement<AtomText> createSubtitle(AtomText value) {
        return new JAXBElement<AtomText>(_Subtitle_QNAME, AtomText.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights")
    public JAXBElement<AtomText> createRights(AtomText value) {
        return new JAXBElement<AtomText>(_Rights_QNAME, AtomText.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "uri")
    public JAXBElement<String> createUri(String value) {
        return new JAXBElement<String>(_Uri_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "published")
    public JAXBElement<AtomDate> createPublished(AtomDate value) {
        return new JAXBElement<AtomDate>(_Published_QNAME, AtomDate.class, null, value);
    }
}
