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
package com.msopentech.odatajclient.engine.utils;

import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.DELETE;
import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.GET;
import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.MERGE;
import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.PATCH;
import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.POST;
import static com.msopentech.odatajclient.engine.communication.request.ODataRequest.Method.PUT;

import com.msopentech.odatajclient.engine.client.http.HttpMerge;
import com.msopentech.odatajclient.engine.client.http.HttpPatch;
import com.msopentech.odatajclient.engine.communication.request.ODataRequest;
import com.msopentech.odatajclient.engine.data.ODataURIBuilder;
import java.net.URI;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

public class URIUtils {

    public static URI getURI(final String base, final String href) {
        if (href == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(href);

        if (!uri.isAbsolute() && base != null) {
            uri = new ODataURIBuilder(base).appendEntityTypeSegment(href).build();
        }

        return uri.normalize();
    }

    public static URI getURI(final URI base, final String href) {
        if (href == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(href);

        if (!uri.isAbsolute() && base != null) {
            uri = new ODataURIBuilder(base.toString()).appendEntityTypeSegment(href).build();
        }

        return uri.normalize();
    }

    public static HttpUriRequest toHttpURIRequest(final ODataRequest.Method method, final URI uri) {
        HttpUriRequest result;

        switch (method) {
            case POST:
                result = new HttpPost(uri);
                break;

            case PUT:
                result = new HttpPut(uri);
                break;

            case PATCH:
                result = new HttpPatch(uri);
                break;

            case MERGE:
                result = new HttpMerge(uri);
                break;

            case DELETE:
                result = new HttpDelete(uri);
                break;

            case GET:
            default:
                result = new HttpGet(uri);
                break;
        }

        return result;
    }
}
