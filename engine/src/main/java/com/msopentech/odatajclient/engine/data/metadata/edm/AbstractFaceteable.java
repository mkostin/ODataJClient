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

public abstract class AbstractFaceteable extends AbstractAnnotated {

    public abstract String getName();

    public abstract String getType();

    public abstract Map<QName, String> getOtherAttributes();

    public abstract boolean isNullable();

    public abstract String getMaxLength();

    public abstract BigInteger getPrecision();

    public abstract BigInteger getScale();

    public abstract Boolean isFixedLength();

    public abstract Boolean isUnicode();

    public abstract TConcurrencyMode getConcurrencyMode();

    public abstract String getDefaultValue();

    public abstract String getCollation();
}
