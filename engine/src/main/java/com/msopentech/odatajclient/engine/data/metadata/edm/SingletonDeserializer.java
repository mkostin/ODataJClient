package com.msopentech.odatajclient.engine.data.metadata.edm;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class SingletonDeserializer extends JsonDeserializer<Singleton> {

    @Override
    public Singleton deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
        final Singleton singleton = new Singleton();
        
        for (; jp.getCurrentToken() != JsonToken.END_OBJECT; jp.nextToken()) {
            final JsonToken token = jp.getCurrentToken();
            if (token == JsonToken.FIELD_NAME) {
                if ("Name".equals(jp.getCurrentName())) {
                    singleton.setName(jp.nextTextValue());
                } else if ("Type".equals(jp.getCurrentName())) {
                    singleton.setType(jp.nextTextValue());
                }
            }
        }
        
        return singleton;
    }

}
