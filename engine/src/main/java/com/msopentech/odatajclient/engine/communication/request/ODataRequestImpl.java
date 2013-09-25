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
import com.msopentech.odatajclient.engine.client.http.HttpMethod;
import com.msopentech.odatajclient.engine.communication.ODataClientErrorException;
import com.msopentech.odatajclient.engine.communication.ODataServerErrorException;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaderValues;
import com.msopentech.odatajclient.engine.communication.header.ODataHeaders;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.cud.ODataCUDRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.invoke.ODataInvokeRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.streamed.ODataStreamedRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataResponse;
import com.msopentech.odatajclient.engine.data.ODataError;
import com.msopentech.odatajclient.engine.data.ODataReader;
import com.msopentech.odatajclient.engine.data.json.error.JSONODataError;
import com.msopentech.odatajclient.engine.data.xml.error.XMLODataError;
import com.msopentech.odatajclient.engine.utils.Configuration;
import com.msopentech.odatajclient.engine.utils.ODataConstants;
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
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract representation of an OData request.
 * Get instance by using factories.
 *
 * @see ODataCUDRequestFactory
 * @see ODataRetrieveRequestFactory
 * @see ODataBatchRequestFactory
 * @see ODataInvokeRequestFactory
 * @see ODataStreamedRequestFactory
 */
public class ODataRequestImpl implements ODataRequest {

    /**
     * Logger.
     */
    protected static final Logger LOG = LoggerFactory.getLogger(ODataRequest.class);

    /**
     * OData request method.
     */
    protected final HttpMethod method;

    /**
     * OData request header.
     */
    protected final ODataHeaders odataHeaders;

    /**
     * Target URI.
     */
    protected final URI uri;

    /**
     * HTTP client.
     */
    protected final HttpClient client;

    /**
     * HTTP request.
     */
    protected final HttpUriRequest request;

    /**
     * Constructor.
     *
     * @param method HTTP request method. If configured X-HTTP-METHOD header will be used.
     * @param uri OData request URI.
     */
    protected ODataRequestImpl(final HttpMethod method, final URI uri) {
        this.method = method;

        // initialize default headers
        this.odataHeaders = new ODataHeaders();
        this.odataHeaders.setHeader(ODataHeaders.HeaderName.minDataServiceVersion, ODataConstants.V30);
        this.odataHeaders.setHeader(ODataHeaders.HeaderName.maxDataServiceVersion, ODataConstants.V30);
        this.odataHeaders.setHeader(ODataHeaders.HeaderName.dataServiceVersion, ODataConstants.V30);

        // target uri
        this.uri = uri;

        this.client = Configuration.getHttpClientFactory().createHttpClient(this.method, this.uri);
        this.request = Configuration.getHttpUriRequestFactory().createHttpUriRequest(this.method, this.uri);
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
    public void setSlug(final String value) {
        odataHeaders.setHeader(ODataHeaders.HeaderName.slug, value);
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
        return StringUtils.isBlank(acceptHead) ? Configuration.getDefaultPubFormat().toString() : acceptHead;
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
        return StringUtils.isBlank(contentTypeHead) ? Configuration.getDefaultPubFormat().toString() : contentTypeHead;
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
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Gets request headers.
     *
     * @return request headers.
     */
    public ODataHeaders getHeader() {
        return odataHeaders;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public byte[] toByteArray() {
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

    /**
     * {@inheritDoc }
     */
    @Override
    public InputStream rawExecute() {
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

    /**
     * Builds the request and execute it.
     *
     * @return HttpReponse object.
     */
    protected HttpResponse doExecute() {
        // Set Content-Type and Accept headers with default values, if not yet set
        if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.contentType))) {
            setContentType(getContentType());
        }
        if (StringUtils.isBlank(odataHeaders.getHeader(ODataHeaders.HeaderName.accept))) {
            setAccept(getAccept());
        }

        // Add header for KeyAsSegment management
        if (Configuration.isKeyAsSegment()) {
            addCustomHeader(
                    ODataHeaders.HeaderName.dataServiceUrlConventions.toString(), ODataHeaderValues.keyAsSegment);
        }

        // Add all available headers
        for (String key : getHeaderNames()) {
            this.request.addHeader(key, odataHeaders.getHeader(key));
        }

        if (LOG.isDebugEnabled()) {
            for (Header header : this.request.getAllHeaders()) {
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
                    final boolean isXML = getAccept().indexOf("json") == -1;
                    ODataError error;

                    try {
                        error = ODataReader.readError(httpEntity.getContent(), isXML);
                    } catch (IllegalArgumentException e) {
                        LOG.warn("Error deserializing error response", e);
                        error = getGenericError(
                                response.getStatusLine().getStatusCode(),
                                response.getStatusLine().getReasonPhrase(),
                                isXML);
                    }

                    throw new ODataClientErrorException(response.getStatusLine(), error);
                }
            } catch (IOException e) {
                throw new HttpClientException(
                        "Received '" + response.getStatusLine() + "' but could not extract error body", e);
            }
        }

        return response;
    }

    /**
     * Gets an empty response that can be initialized by a stream.
     * <p>
     * This method has to be used to build response items about a batch request.
     *
     * @param <V> ODataResppnse type.
     * @return empty OData response instance.
     */
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

    private ODataError getGenericError(final int code, final String errorMsg, final boolean isXML) {
        final ODataError error;
        if (isXML) {
            error = new XMLODataError();
            final XMLODataError.Message msg = new XMLODataError.Message();
            msg.setValue(errorMsg);

            ((XMLODataError) error).setMessage(msg);
            ((XMLODataError) error).setCode(String.valueOf(code));
        } else {
            error = new JSONODataError();
            final JSONODataError.Message msg = new JSONODataError.Message();
            msg.setValue(errorMsg);

            ((JSONODataError) error).setMessage(msg);
            ((JSONODataError) error).setCode(String.valueOf(code));
        }

        return error;
    }
}
