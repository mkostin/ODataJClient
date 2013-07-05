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
package com.msopentech.odatajclient.engine.utils;

public class BatchController {

    private boolean validBatch = true;

    private final String boundary;

    private final BatchLineIterator batchLineIterator;

    public BatchController(final BatchLineIterator batchLineIterator, final String boundary) {
        this.batchLineIterator = batchLineIterator;
        this.boundary = boundary;
    }

    public boolean isValidBatch() {
        return validBatch;
    }

    public void setValidBatch(boolean validBatch) {
        this.validBatch = validBatch;
    }

    public String getBoundary() {
        return boundary;
    }

    public BatchLineIterator getBatchLineIterator() {
        return batchLineIterator;
    }
}
