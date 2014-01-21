package com.msopentech.odatajclient.engine.data.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.msopentech.odatajclient.engine.data.AbstractLinkResource;
import com.msopentech.odatajclient.engine.data.ODataLinkType;
import com.msopentech.odatajclient.engine.utils.ODataConstants;

public class JSONV4EntrySerializer extends ODataJsonSerializer<JSONV4Entry> {
    
    @SuppressWarnings("rawtypes")
    @Override
    protected void doSerialize(
            final JSONV4Entry entry, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException, JsonProcessingException {

        final String annotationPrefix = "@";
        
        jgen.writeStartObject();

        if (entry.getMetadata() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_METADATA, entry.getMetadata().toASCIIString());
        }
        if (StringUtils.isNotBlank(entry.getType())) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_TYPE, entry.getType());
        }
        if (entry.getId() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_ID, entry.getId());
        }

        if (entry.getSelfLink() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_READ_LINK, entry.getSelfLink().getHref());
        }

        if (entry.getEditLink() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_EDIT_LINK, entry.getEditLink().getHref());
        }

        if (entry.getMediaContentSource() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_MEDIAREAD_LINK, entry.getMediaContentSource());
        }
        if (entry.getMediaContentType() != null) {
            jgen.writeStringField(annotationPrefix + ODataConstants.JSON_MEDIA_CONTENT_TYPE, entry.getMediaContentType());
        }

        final Map<String, List<String>> entitySetLinks = new HashMap<String, List<String>>();

        for (AbstractLinkResource link : entry.getNavigationLinks()) {
            if (link.getInlineEntry() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineEntry());
            } else if (link.getInlineFeed() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineFeed());
            } else {
                ODataLinkType type = null;
                try {
                    type = ODataLinkType.fromString(link.getRel(), link.getType());
                } catch (IllegalArgumentException e) {
                    // ignore   
                }

                if (type == ODataLinkType.ENTITY_SET_NAVIGATION) {
                    final List<String> uris;
                    if (entitySetLinks.containsKey(link.getTitle())) {
                        uris = entitySetLinks.get(link.getTitle());
                    } else {
                        uris = new ArrayList<String>();
                        entitySetLinks.put(link.getTitle(), uris);
                    }
                    uris.add(link.getHref());
                } else {
                    jgen.writeStringField(link.getTitle() + ODataConstants.JSON_BIND_LINK_SUFFIX, link.getHref());
                }
            }
        }
        for (Map.Entry<String, List<String>> entitySetLink : entitySetLinks.entrySet()) {
            jgen.writeArrayFieldStart(entitySetLink.getKey() + ODataConstants.JSON_BIND_LINK_SUFFIX);
            for (String uri : entitySetLink.getValue()) {
                jgen.writeString(uri);
            }
            jgen.writeEndArray();
        }

        for (AbstractLinkResource link : entry.getMediaEditLinks()) {
            if (link.getTitle() == null) {
                jgen.writeStringField(annotationPrefix + ODataConstants.JSON_MEDIAEDIT_LINK, link.getHref());
            }

            if (link.getInlineEntry() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineEntry());
            }
            if (link.getInlineFeed() != null) {
                jgen.writeObjectField(link.getTitle(), link.getInlineFeed());
            }
        }

        if (entry.getMediaEntryProperties() == null) {
            DOMTreeUtils.writeSubtree(client, jgen, entry.getContent());
        } else {
            DOMTreeUtils.writeSubtree(client, jgen, entry.getMediaEntryProperties());
        }

        jgen.writeEndObject();
    }
}
