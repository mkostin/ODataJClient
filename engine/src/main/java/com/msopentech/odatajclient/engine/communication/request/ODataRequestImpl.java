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

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.ODataServerErrorException;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.engine.utils.URIUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
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
    protected final ODataHeaders odataHeaders;

    /**
     * HTTP client.
     */
    protected final HttpClient client;

    /**
     * HTTP request.
     */
    protected final HttpRequestBase request;

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
        this.odataHeaders = new ODataHeaders();

        this.client = new DefaultHttpClient();
        this.request = URIUtils.toHttpRequest(this.method);
    }

    @Override
    public void setURI(final URI uri) {
        this.uri = uri;
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
        return odataHeaders.getHeaderNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(final String name) {
        return odataHeaders.getHeader(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxDataServiceVersion(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.maxDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinDataServiceVersion(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.minDataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccept(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.accept, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfMatch(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.ifMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIfNoneMatch(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.ifNoneMatch, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrefer(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.prefer, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXHTTPMethod(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.xHttpMethod, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentType(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.contentType, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataServiceVersion(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.dataServiceVersion, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCustomHeader(final String name, final String value) {
        odataHeaders.setHeader(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxDataServiceVersion() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.maxDataServiceVersion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinDataServiceVersion() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.minDataServiceVersion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccept() {
        final String acceptHead = odataHeaders.getHeader(ODataHeaders.HeaderName.accept);
        return StringUtils.isBlank(acceptHead) ? Configuration.getFormat().toString() : acceptHead;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIfMatch() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.ifMatch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIfNoneMatch() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.ifNoneMatch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefer() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.prefer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType() {
        final String contentTypeHead = odataHeaders.getHeader(ODataHeaders.HeaderName.contentType);
        return StringUtils.isBlank(contentTypeHead) ? Configuration.getFormat().toString() : contentTypeHead;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataServiceVersion() {
        return odataHeaders.getHeader(ODataHeaders.HeaderName.dataServiceVersion);
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
    public ODataHeaders getHeader() {
        return odataHeaders;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public byte[] getFullHeaders() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(getMethod().toString()).append(" ").
                    append(uri.toString()).append(" ").append("HTTP/1.1");

            baos.write(requestBuilder.toString().getBytes());

            baos.write(ODataStreamer.CRLF);

            // Set Content-Type and Accept headers with default values, if not yet set
            if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.contentType))) {
                setContentType(getContentType());
            }
            if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.accept))) {
                setAccept(getAccept());
            }

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
            IOUtils.closeQuietly(baos);
        }
    }

    protected void validate() {
        if (this.uri == null) {
            throw new IllegalStateException("No target URI provided");
        }
        this.request.setURI(this.uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream rawExecute() {
        validate();

        try {
            final HttpEntity httpEntity = doExecute().getEntity();
            return httpEntity == null ? null : httpEntity.getContent();
        } catch (IOException e) {
            throw new HttpClientException(e);
        } catch (RuntimeException e) {
            this.request.abort();
            throw new HttpClientException(e);
        }
    }

    protected HttpResponse doExecute() {
        // Set Content-Type and Accept headers with default values, if not yet set
        if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.contentType))) {
            setContentType(getContentType());
        }
        if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.accept))) {
            setAccept(getAccept());
        }

        // Add all available headers
        for (String key : getHeaderNames()) {
            this.request.addHeader(key, odataHeaders.getHeader(key));
        }

        if (LOG.isDebugEnabled()) {
            for (Header header : request.getAllHeaders()) {
                LOG.debug("HTTP header being sent: " + header);
            }
        }

        final HttpResponse response;
        try {
            response = this.client.execute(this.request);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } catch (RuntimeException e) {
            this.request.abort();
            throw new HttpClientException(e);
        }


        if (response.getStatusLine().getStatusCode() >= 500) {
            throw new ODataServerErrorException(response.getStatusLine());
        } else if (response.getStatusLine().getStatusCode() >= 400) {
            try {
                final HttpEntity httpEntity = response.getEntity();
                if (httpEntity == null) {
                    throw new ODataClientErrorException(response.getStatusLine());
                } else {
                    throw new ODataClientErrorException(response.getStatusLine(),
                            ODataReader.readError(httpEntity.getContent(), getAccept().indexOf("xml") != -1));
                }
            } catch (IOException e) {
                throw new HttpClientException(
                        "Received '" + response.getStatusLine() + "' but could not extract error body", e);
            }
        }

        return response;
    }

    @SuppressWarnings("unchecked")
    public <V extends ODataResponse> V getResponseTemplate() {

        for (Class<?> clazz : this.getClass().getDeclaredClasses()) {
            if (ODataResponse.class.isAssignableFrom(clazz)) {
                try {
                    final Constructor<?> constructor = clazz.getDeclaredConstructor(this.getClass());
                    constructor.setAccessible(true);
                    return (V) constructor.newInstance(this);
                } catch (Exception e) {
                    LOG.error("Error retrieving response class template instance", e);
                }
            }
        }

        throw new IllegalStateException("No response class template has been found");
    }
}
