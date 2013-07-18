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
package com.msopentech.odatajclient.engine.data.metadata.edm;

import java.math.BigInteger;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 * Abstract representation of elements that supports facets.
 */
public abstract class AbstractFaceteable extends AbstractAnnotated {

    /**
     * Gets name facet.
     *
     * @return name.
     */
    public abstract String getName();

    /**
     * Gets type facet.
     *
     * @return type.
     */
    public abstract String getType();

    /**
     * Gets other attributes facet.
     *
     * @return other attributes.
     */
    public abstract Map<QName, String> getOtherAttributes();

    /**
     * Gets is-nullable facet.
     *
     * @return is-nullable facet value.
     */
    public abstract boolean isNullable();

    /**
     * Gets max-length facet.
     *
     * @return max-length.
     */
    public abstract String getMaxLength();

    /**
     * Gets precision facet.
     *
     * @return precision.
     */
    public abstract BigInteger getPrecision();

    /**
     * Gets scale facet.
     *
     * @return scale.
     */
    public abstract BigInteger getScale();

    /**
     * Gets is-fixed-length facet.
     *
     * @return is-fixed-length value.
     */
    public abstract Boolean isFixedLength();

    /**
     * Gets is-unicode facet.
     *
     * @return is-unicode value.
     */
    public abstract Boolean isUnicode();

    /**
     * Gets concurrency mode facet.
     *
     * @return concurrency mode.
     */
    public abstract ConcurrencyMode getConcurrencyMode();

    /**
     * Gets default value facet.
     *
     * @return default value.
     */
    public abstract String getDefaultValue();

    /**
     * Gets collation facet.
     *
     * @return collation.
     */
    public abstract String getCollation();
}
