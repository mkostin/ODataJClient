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
package com.msopentech.odatajclient.engine.utils;

import java.io.InputStream;
import java.io.Writer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

public class DefaultDOMParserImpl extends AbstractDOMParser {

    @Override
    public Element parse(final InputStream input) {
        try {
            final DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
            final LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

            final LSInput lsinput = impl.createLSInput();
            lsinput.setByteStream(input);

            return parser.parse(lsinput).getDocumentElement();
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not parse DOM", e);
        }
    }

    @Override
    public void serialize(final Node content, final Writer writer) {
        try {
            final DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
            final LSSerializer serializer = impl.createLSSerializer();
            final LSOutput lso = impl.createLSOutput();
            lso.setCharacterStream(writer);
            serializer.write(content, lso);
        } catch (Exception e) {
            throw new IllegalArgumentException("While serializing DOM element", e);
        }
    }
}
