package com.msopentech.odatajclient.proxy.AstoriaDefaultService;

import com.msopentech.odatajclient.proxy.api.AbstractEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKey;
import com.msopentech.odatajclient.proxy.api.annotations.CompoundKeyElement;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.*;

// EdmSimpleType property imports
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Geospatial;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.GeospatialCollection;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.LineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiLineString;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPoint;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.MultiPolygon;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Point;
import com.msopentech.odatajclient.engine.data.metadata.edm.geospatial.Polygon;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.Message.MessageKey;

@EntitySet("Message")
public interface Message extends AbstractEntitySet<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Message, MessageKey> {
    
    @CompoundKey
    public static class MessageKey implements Serializable {
    
        private String _fromUsername;

        @CompoundKeyElement(name = "FromUsername", position = 0)
        public String getFromUsername() {
            return _fromUsername;
        }

        public void setFromUsername(final String _fromUsername) {
            this._fromUsername = _fromUsername;
        }
    
        private Integer _messageId;

        @CompoundKeyElement(name = "MessageId", position = 1)
        public Integer getMessageId() {
            return _messageId;
        }

        public void setMessageId(final Integer _messageId) {
            this._messageId = _messageId;
        }
    }
}
