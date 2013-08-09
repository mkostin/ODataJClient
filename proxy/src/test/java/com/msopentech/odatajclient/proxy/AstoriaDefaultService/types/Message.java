package com.msopentech.odatajclient.proxy.AstoriaDefaultService.types;

import com.msopentech.odatajclient.proxy.api.annotations.EntityType;
import com.msopentech.odatajclient.proxy.api.annotations.Key;
import com.msopentech.odatajclient.proxy.api.annotations.NavigationProperty;
import com.msopentech.odatajclient.proxy.api.annotations.Property;
import com.msopentech.odatajclient.proxy.api.impl.AbstractType;
import com.msopentech.odatajclient.engine.data.metadata.EdmContentKind;
import com.msopentech.odatajclient.engine.data.metadata.edm.ConcurrencyMode;
import com.msopentech.odatajclient.engine.data.metadata.edm.Action;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.*;

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
import java.util.Collection;

@EntityType(value="Message",
        openType = false,
        hasStream = false,
        isAbstract = false,
        baseType = "",
        fcSourcePath = "",
        fcTargetPath = "",
        fcContentKind = EdmContentKind.text,
        fcNSPrefix = "",
        fcNSURI = "",
        fcKeepInContent = false)
public class Message extends AbstractType {

        
    @Key
    @Property(name = "MessageId", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Integer _messageId;
    
    public Integer getMessageId() {
        return _messageId;
    }

    public void setMessageId(final Integer _messageId) {
        this._messageId = _messageId;
    }

    @Key
    @Property(name = "FromUsername", 
                type = "Edm.String", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private String _fromUsername;
    
    public String getFromUsername() {
        return _fromUsername;
    }

    public void setFromUsername(final String _fromUsername) {
        this._fromUsername = _fromUsername;
    }

    
    @Property(name = "ToUsername", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private String _toUsername;
    
    public String getToUsername() {
        return _toUsername;
    }

    public void setToUsername(final String _toUsername) {
        this._toUsername = _toUsername;
    }

    
    @Property(name = "Sent", 
                type = "Edm.DateTimeOffset", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "SyndicationPublished",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private Timestamp _sent;
    
    public Timestamp getSent() {
        return _sent;
    }

    public void setSent(final Timestamp _sent) {
        this._sent = _sent;
    }

    
    @Property(name = "Subject", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "SyndicationTitle",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = true)
    private String _subject;
    
    public String getSubject() {
        return _subject;
    }

    public void setSubject(final String _subject) {
        this._subject = _subject;
    }

    
    @Property(name = "Body", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private String _body;
    
    public String getBody() {
        return _body;
    }

    public void setBody(final String _body) {
        this._body = _body;
    }

    
    @Property(name = "IsRead", 
                type = "Edm.Boolean", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = false,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.NONE,
                mimeType = "",
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    private Boolean _isRead;
    
    public Boolean getIsRead() {
        return _isRead;
    }

    public void setIsRead(final Boolean _isRead) {
        this._isRead = _isRead;
    }

    

    @NavigationProperty(name = "Sender", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Message_Sender", 
                fromRole = "Message", 
                toRole = "Sender",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _sender;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login getSender() {
        return _sender;
    }

    public void setSender(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _sender) {
        this._sender = _sender;
    }


    @NavigationProperty(name = "Recipient", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Message_Recipient", 
                fromRole = "Message", 
                toRole = "Recipient",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _recipient;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login getRecipient() {
        return _recipient;
    }

    public void setRecipient(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.Login _recipient) {
        this._recipient = _recipient;
    }


    @NavigationProperty(name = "Attachments", 
                relationship = "Microsoft.Test.OData.Services.AstoriaDefaultService.Message_Attachments", 
                fromRole = "Message", 
                toRole = "Attachments",
                containsTarget = false,
                onDelete = Action.NONE)
    private com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.MessageAttachment _attachments;

    public com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.MessageAttachment getAttachments() {
        return _attachments;
    }

    public void setAttachments(final com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.MessageAttachment _attachments) {
        this._attachments = _attachments;
    }

}
