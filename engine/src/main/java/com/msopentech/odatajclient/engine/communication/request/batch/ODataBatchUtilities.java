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

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

public class ODataBatchUtilities {

    private static final Pattern RESPONSE_PATTERN =
            Pattern.compile("HTTP/\\d\\.\\d (\\d+) (.*)", Pattern.CASE_INSENSITIVE);

    public static void writeLinesTillNextBoundary(final LineIterator lineIterator, final String boundary, final OutputStream os) 
            throws UnsupportedEncodingException, IOException {
        if (lineIterator.hasNext()) {
            final String line = lineIterator.nextLine();
            if (!line.startsWith(boundary)) {
                os.write(line.getBytes("UTF-8"));
                writeLinesTillNextBoundary(lineIterator, boundary, os);
            }
        }
    }
    
    public static void readHeaders(final LineIterator lineIterator, final Map<String, Collection<String>> targetMap) {
        if (lineIterator.hasNext()) {
            final String line = lineIterator.nextLine().trim();
            if (StringUtils.isNotBlank(line)) {
                addHeaderLine(line, targetMap);
                readHeaders(lineIterator, targetMap);
            }
        }
    }

    public static void addHeaderLine(final String headerLine, final Map<String, Collection<String>> targetMap) {
        int sep = headerLine.indexOf(":");
        if (sep > 0 && sep < headerLine.length() - 1) {
            final String key = headerLine.substring(0, sep).trim();
            final Collection<String> value;
            if (targetMap.containsKey(key)) {
                value = targetMap.get(key);
            } else {
                value = new HashSet<String>();
                targetMap.put(key, value);
            }
            value.add(headerLine.substring(sep + 1, headerLine.length()).trim());
        }
    }

    public static Map.Entry<Integer, String> readResponseLine(final LineIterator lineIterator) {
        final String line = lineIterator.nextLine();
        final Matcher matcher = RESPONSE_PATTERN.matcher(line.trim());

        if (matcher.matches()) {
            return new AbstractMap.SimpleEntry<Integer, String>(Integer.valueOf(matcher.group(2)), matcher.group(3));
        }

        throw new IllegalArgumentException("Invalid response line '" + line + "'");
    }
}
