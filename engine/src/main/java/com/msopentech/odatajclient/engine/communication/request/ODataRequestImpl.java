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
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataRequest.class);

    /**
     * OData resource representation format.
     */
    private ODataFormat format;

    /**
     * OData request method.
     * <p>
     * If configured a X-HTTP-METHOD header will be used.
     * In this case the actual method will be
     * <code>POST</code>.
     */
    final protected Method method;

    /**
     * OData request header.
     */
    protected ODataHeader header;

    /**
     * Target URI.
     */
    protected URI uri;

    /**
     * Constructor.
     *
     * @param method HTTP request method. If configured X-HTTP-METHOD header will be used.
     */
    protected ODataRequestImpl(final Method method) {
        this.method = method;
        // initialize a default header from configuration
        this.header = new ODataHeader();
        // take format from configuration
        this.format = new Configuration().getFormat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ODataFormat getFormat() {
        return format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormat(final ODataFormat format) {
        this.format = format;
        setContentType(format.toString());
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
    public void setContentType(String value) {
        header.setHeader(ODataHeader.HeaderName.contentType, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataServiceVersion(String value) {
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
        final String ct = header.getHeader(ODataHeader.HeaderName.contentType);
        return StringUtils.isBlank(ct) ? ODataFormat.JSON.toString() : ct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataServiceVersion() {
        return header.getHeader(ODataHeader.HeaderName.dataServiceVersion);
    }

    /**
     * Gets request HTTP method.
     *
     * @return HTTP method.
     */
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
     * Gets request URI.
     *
     * @return request URI.
     */
    public URI getUri() {
        return uri;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public byte[] toByteArray() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            final StringBuilder request = new StringBuilder();
            request.append(getMethod().toString()).append(" ").append(uri.toString()).append(" ").append("HTTP/1.1");

            os.write(request.toString().getBytes());

            os.write(ODataStreamer.CRLF);

            for (String name : getHeaderNames()) {
                final String value = getHeader(name);

                if (StringUtils.isNotBlank(value)) {
                    os.write((name + ": " + value).getBytes());
                    os.write(ODataStreamer.CRLF);
                }
            }

            return os.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                os.close();
            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream rowExecute() {
        final WebClient client = WebClient.create(this.uri);
        return client.accept(getContentType()).get(InputStream.class);
    }
}
