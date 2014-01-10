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

import com.msopentech.odatajclient.engine.client.ODataV3Client;

public class ODataV3Binder extends AbstractODataBinder {

    private static final Object MONITOR = new Object();

    private static ODataV3Binder instance;

    public static ODataV3Binder getInstance(final ODataV3Client client) {
        synchronized (MONITOR) {
            if (instance == null) {
                instance = new ODataV3Binder(client);
            }
        }

        return instance;
    }

    private ODataV3Binder(final ODataV3Client client) {
        super(client);
    }
}
