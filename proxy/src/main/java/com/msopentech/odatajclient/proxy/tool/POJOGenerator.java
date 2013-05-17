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
package com.msopentech.odatajclient.proxy.tool;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Read OData metadata from given URL and generate all Java classes and interfaces needed to work with proxy.
 */
public class POJOGenerator {

    private final URL metadataURL;

    private final String packageName;

    private final File baseDirectory;

    public POJOGenerator(final URL metadataURL, final String packageName, final File baseDirectory) {
        this.metadataURL = metadataURL;
        this.packageName = packageName;
        this.baseDirectory = baseDirectory;
    }

    public void generate() {
    }

    public static void main(final String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: <metadata URL> <packageName> <base directory path>");
        }

        final URL metadataURL;
        try {
            metadataURL = new URL(args[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + args[0]);
        }

        final String packageName = args[1];

        final File baseDirectory = new File(args[2]);
        if (!baseDirectory.isDirectory()) {
            throw new IllegalArgumentException("Not existing, or not directory: " + args[2]);
        }

        POJOGenerator generator = new POJOGenerator(metadataURL, packageName, baseDirectory);
        generator.generate();
    }
}
