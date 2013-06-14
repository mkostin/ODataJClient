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

import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.atom.AtomEntry;
import com.msopentech.odatajclient.engine.data.atom.Link;
import java.net.MalformedURLException;
import java.net.URI;
import org.slf4j.LoggerFactory;

public class ODataBinder {

    /**
     * Logger.
     */
    protected static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ODataBinder.class);

    public static ODataEntity getODataEntity(final AtomEntry entry) {
        final ODataEntity entity = EntityFactory.newEntity(entry.getCategory().getTerm());

        final Link edit = entry.getEditLink();

        try {
            URI uri = getURI(edit, entry.getBase());
            entity.setEditLink(uri);
        } catch (Exception e) {
            LOG.warn("Unparsable link", e);
        }

        final Link self = entry.getSelfLink();

        try {
            URI uri = getURI(self, entry.getBase());
            entity.setLink(uri);
        } catch (Exception e) {
            LOG.warn("Unparsable link", e);
        }

        return entity;
    }

    private static URI getURI(final Link link, final String base) throws MalformedURLException {
        if (link == null) {
            throw new IllegalArgumentException("Null link provided");
        }

        URI uri = URI.create(link.getHref());

        if (!uri.isAbsolute()) {
            uri = URI.create(base + "/" + link.getHref());
        }

        return uri.normalize();
    }
}
