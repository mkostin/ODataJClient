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
package com.msopentech.odatajclient.engine.communication.request.streamed;

import com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method;
import com.msopentech.odatajclient.engine.utils.Configuration;
import java.io.InputStream;
import java.net.URI;

/**
 * OData request factory class.
 */
public final class ODataStreamedRequestFactory {

    private ODataStreamedRequestFactory() {
        // Empty private constructor for static utility classes
    }

    /**
     * Gets a media entity create request object instance.
     * <p>
     * Use this kind of request to create a new media entity.
     *
     * @param targetURI entity set URI.
     * @param entity entity blob to be created.
     * @return new ODataMediaEntityCreateRequest instance.
     */
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(
            final URI targetURI, final InputStream media) {

        return new ODataMediaEntityCreateRequest(targetURI, media);
    }

    /**
     * Gets a stream update request object instance.
     * <p>
     * Use this kind of request to update a named stream property.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     * @return new ODataUpdateStreamRequest instance.
     */
    public static ODataStreamUpdateRequest getStreamUpdateRequest(final URI targetURI, final InputStream stream) {
        final ODataStreamUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataStreamUpdateRequest(Method.POST, targetURI, stream);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataStreamUpdateRequest(Method.PUT, targetURI, stream);
        }

        return req;
    }

    /**
     * Gets a media entity update request object instance.
     * <p>
     * Use this kind of request to update a media entity.
     *
     * @param editURI media entity edit link URI.
     * @param entity entity blob to be updated.
     * @return new ODataMediaEntityUpdateRequest instance.
     */
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(
            final URI editURI, final InputStream media) {

        final ODataMediaEntityUpdateRequest req;

        if (Configuration.isUseXHTTPMethod()) {
            req = new ODataMediaEntityUpdateRequest(Method.POST, editURI, media);
            req.setXHTTPMethod(Method.PUT.name());
        } else {
            req = new ODataMediaEntityUpdateRequest(Method.PUT, editURI, media);
        }

        return req;
    }
}
