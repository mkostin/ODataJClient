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

import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataURI;
import java.io.InputStream;

/**
 * This class implements a link set OData request.
 * It encapsulates two different request: the former remove link request and the latter add link request.
 * <p>
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getSetLinkRequest(com.msopentech.odatajclient.engine.data.ODataURI,
 * com.msopentech.odatajclient.engine.data.ODataURI, com.msopentech.odatajclient.engine.data.ODataLink)
 */
public interface ODataSetLinkRequest extends ODataRequest {

    /**
     * Gets the body for the remove link request.
     *
     * @return link to be removed.
     */
    InputStream getLinkToBeRemoved();

    /**
     * Gets the body for the add link request.
     *
     * @return link to be created.
     */
    InputStream getEntityToBeAdded();

    /**
     * {@inheritDoc}
     */
    @Override
    InputStream getBody();
}
