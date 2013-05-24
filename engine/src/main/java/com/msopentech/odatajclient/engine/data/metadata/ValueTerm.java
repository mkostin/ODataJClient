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
package com.msopentech.odatajclient.engine.data.metadata;

import java.io.Serializable;

/**
 * Metadata elements: <tt>&lt;ValueTerm/&gt;</tt>
 */
public class ValueTerm implements Serializable {

    private static final long serialVersionUID = -2248000265836848161L;

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
