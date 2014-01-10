/**
 * Copyright © Microsoft Open Technologies, Inc.
 *
 * All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 *
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 */
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.client.ODataV4Client;

public class ODataV4Writer extends AbstractODataWriter {

    private static final Object MONITOR = new Object();

    private static ODataV4Writer instance;

    public static ODataV4Writer getInstance(final ODataV4Client client) {
        synchronized (MONITOR) {
            if (instance == null) {
                instance = new ODataV4Writer(client);
            }
        }

        return instance;
    }

    private ODataV4Writer(final ODataV4Client client) {
        super(client);
    }
}
