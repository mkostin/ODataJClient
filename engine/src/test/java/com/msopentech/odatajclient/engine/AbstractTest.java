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
package com.msopentech.odatajclient.engine;

import com.msopentech.odatajclient.engine.format.ODataFormat;
import com.msopentech.odatajclient.engine.format.ODataPubFormat;
import java.util.Locale;
import org.junit.BeforeClass;

public abstract class AbstractTest {

    /**
     * This is needed for correct number handling (Double, for example).
     */
    @BeforeClass
    public static void setEnglishLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }

    protected String getSuffix(final ODataPubFormat format) {
        return format == ODataPubFormat.ATOM ? "xml" : "json";
    }

    protected String getSuffix(final ODataFormat format) {
        return format == ODataFormat.XML ? "xml" : "json";
    }
}
