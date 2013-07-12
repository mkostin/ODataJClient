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
package com.msopentech.odatajclient.engine.communication.request.batch;

import java.util.Iterator;
import org.apache.commons.io.LineIterator;

public class ODataBatchLineIterator implements Iterator<String> {

    private final LineIterator batchLineIterator;

    private String current;

    public ODataBatchLineIterator(final LineIterator batchLineIterator) {
        this.batchLineIterator = batchLineIterator;
        this.current = null;
    }

    @Override
    public boolean hasNext() {
        return batchLineIterator.hasNext();
    }

    @Override
    public String next() {
        current = batchLineIterator.next();
        return current;
    }

    public String nextLine() {
        current = batchLineIterator.nextLine();
        return current;
    }

    @Override
    public void remove() {
        batchLineIterator.remove();
    }

    public String getCurrent() {
        return current;
    }
}
