package com.msopentech.odatajclient.engine.data.json;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msopentech.odatajclient.engine.data.AbstractPayloadObject;
import com.msopentech.odatajclient.engine.data.EntryResource;
import com.msopentech.odatajclient.engine.data.FeedResource;
import com.msopentech.odatajclient.engine.uri.SegmentType;

public abstract class AbstractJSONFeed extends AbstractPayloadObject implements FeedResource, Serializable {

    private static final long serialVersionUID = -3576372289800799417L;

    @JsonProperty(value = "odata.metadata", required = false)
    protected URI metadata;

    @JsonProperty(value = "odata.count", required = false)
    protected Integer count;

    @JsonProperty("value")
    protected final List<AbstractJSONEntry> entries;

    @JsonProperty(value = "odata.nextLink", required = false)
    protected String next;
    
    public AbstractJSONFeed() {
        entries = new ArrayList<AbstractJSONEntry>();
    }

    @JsonIgnore
    @Override
    public URI getBaseURI() {
        URI baseURI = null;
        if (metadata != null) {
            final String metadataURI = getMetadata().toASCIIString();
            baseURI = URI.create(metadataURI.substring(0, metadataURI.indexOf(SegmentType.METADATA.getValue())));
        }

        return baseURI;
    }
    
    /**
     * Gets the metadata URI.
     */
    public URI getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata URI.
     *
     * @param metadata metadata URI.
     */
    public void setMetadata(final URI metadata) {
        this.metadata = metadata;
    }

    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public Integer getCount() {
        return count;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AbstractJSONEntry> getEntries() {
        return entries;
    }

    /**
     * Add entry.
     *
     * @param entry entry.
     * @return 'TRUE' in case of success; 'FALSE' otherwise.
     */
    public boolean addEntry(final AbstractJSONEntry entry) {
        return this.entries.add(entry);
    }

    @Override
    public abstract void setEntries(List<EntryResource> entries);


    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public void setNext(final URI next) {
        this.next = next.toASCIIString();
    }

    /**
     * {@inheritDoc }
     */
    @JsonIgnore
    @Override
    public URI getNext() {
        return next == null ? null : URI.create(next);
    }

}
