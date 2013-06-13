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
package com.msopentech.odatajclient.engine.data.metadata.edm.geospatial;

import org.apache.commons.lang3.StringUtils;

/**
 * Class for building concrete Geospatial instances.
 */
public class GeospatialBuilder {

    /**
     * Construct Geospatial instance by parsing the given pattern.
     *
     * @param <T> concrete Geospatial subclass
     * @param reference concrete Geospatial subclass to build
     * @param pattern prodive information for building an instance of the provided reference class
     * @return concrete Geospatial instance according to the given pattern
     */
    @SuppressWarnings("unchecked")
    public static <T extends Geospatial> T build(final Class<T> reference, final String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("Cannot parse empty string");
        }

        String[] segments = pattern.split("|");

        // TODO: complete
        return (T) new Point(Geospatial.Dimension.valueOf(segments[0]));
    }
}
