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
package com.msopentech.odatajclient.engine.communication.request;

import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract representation of an OData request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory.
 */
public class ODataRequestImpl implements ODataRequest {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(ODataRequest.class);

    /**
     * OData request method.
     * <p>
     * If configured a X-HTTP-METHOD header will be used.
     * In this case the actual method will be
     * <code>POST</code>.
     */
    protected final Method method;

    /**
     * OData request header.
     */
    protected ODataHeader header;

    /**
     * Target URI.
     */
    protected URI uri;

    /**
     * CXF web client.
     */
    protected final WebClient client;

    /**
     * Constructor.
     *
     * @param method HTTP request method. If configured X-HTTP-METHOD header will be used.
     * @param uri OData request URI.
     */
    protected ODataRequestImpl(final Method method, final URI uri) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
        // target uri
        this.uri = uri;

        client = WebClient.create(this.uri);

        for (String key : header.getHeaderNames()) {
            client.header(key, header.getHeader(key));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URI getURI() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaderNames() {
        return header.getHeaderNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(final String name) {
        return header.getHeader(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.maxDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.minDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccept(final String value) {
        header.setHeader(ODataHeader.HeaderName.accept, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfNoneMatch(final String value) {
        header.setHeader(ODataHeader.HeaderName.ifNoneMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrefer(final String value) {
        header.setHeader(ODataHeader.HeaderName.prefer, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHTTPMethod(final String value) {
        header.setHeader(ODataHeader.HeaderName.xHttpMethod, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentType(final String value) {
        header.setHeader(ODataHeader.HeaderName.contentType, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataServiceVersion(final String value) {
        header.setHeader(ODataHeader.HeaderName.dataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomHeader(final String name, final String value) {
        header.setHeader(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxDataServiceVersion() {
        return header.getHeader(ODataHeader.HeaderName.maxDataServiceVersion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinDataServiceVersion() {
        return header.getHeader(ODataHeader.HeaderName.minDataServiceVersion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccept() {
        return header.getHeader(ODataHeader.HeaderName.accept);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIfMatch() {
        return header.getHeader(ODataHeader.HeaderName.ifMatch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIfNoneMatch() {
        return header.getHeader(ODataHeader.HeaderName.ifNoneMatch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefer() {
        return header.getHeader(ODataHeader.HeaderName.prefer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType() {
        final String contentTypeHead = header.getHeader(ODataHeader.HeaderName.contentType);
        return StringUtils.isBlank(contentTypeHead) ? ODataFormat.JSON.toString() : contentTypeHead;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataServiceVersion() {
        return header.getHeader(ODataHeader.HeaderName.dataServiceVersion);
    }

    /**
     * ${@inheritDoc }
     */
    @Override
    public Method getMethod() {
        return method;
    }

    /**
     * Gets request header.
     *
     * @return request header.
     */
    public ODataHeader getHeader() {
        return header;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public byte[] toByteArray() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final StringBuilder request = new StringBuilder();
            request.append(getMethod().toString()).append(" ").append(uri.toString()).append(" ").append("HTTP/1.1");

            baos.write(request.toString().getBytes());

            baos.write(ODataStreamer.CRLF);

            for (String name : getHeaderNames()) {
                final String value = getHeader(name);

                if (StringUtils.isNotBlank(value)) {
                    baos.write((name + ": " + value).getBytes());
                    baos.write(ODataStreamer.CRLF);
                }
            }

            return baos.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                baos.close();
            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream rawExecute() {
        return client.accept(getContentType()).get(InputStream.class);
    }
}
