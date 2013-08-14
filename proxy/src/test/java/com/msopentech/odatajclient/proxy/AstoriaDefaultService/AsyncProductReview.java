package com.msopentech.odatajclient.proxy.AstoriaDefaultService;

import com.msopentech.odatajclient.proxy.api.AbstractAsyncEntitySet;
import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.*;

// EdmSimpleType property imports
import com.msopentech.odatajclient.engine.data.ODataDuration;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;
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
import java.util.UUID;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;

import java.io.Serializable;
import com.msopentech.odatajclient.proxy.AstoriaDefaultService.ProductReview.ProductReviewKey;

@EntitySet("ProductReview")
public interface AsyncProductReview extends AbstractAsyncEntitySet<com.msopentech.odatajclient.proxy.AstoriaDefaultService.types.ProductReview, ProductReviewKey> {

    public static class ProductReviewKey implements Serializable {
    
        private String _revisionId;

        public String getRevisionId() {
            return _revisionId;
        }

        public void setRevisionId(final String _revisionId) {
            this._revisionId = _revisionId;
        }
    
        private Integer _reviewId;

        public Integer getReviewId() {
            return _reviewId;
        }

        public void setReviewId(final Integer _reviewId) {
            this._reviewId = _reviewId;
        }
    
        private Integer _productId;

        public Integer getProductId() {
            return _productId;
        }

        public void setProductId(final Integer _productId) {
            this._productId = _productId;
        }
    }
}
