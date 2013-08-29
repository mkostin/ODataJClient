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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 * Abstract representation of a JAXB element.
 */
public abstract class AbstractElement {

    public abstract List<Object> getValues();

    /**
     * Gets JAXB elements matching the given type.
     *
     * @param <T> element type to be retrieved.
     * @param reference reference class.
     * @return list of JABX elements.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getJAXBElements(Class<T> reference) {
        List<T> result = new ArrayList<T>();

        for (Object object : getValues()) {
            if (object instanceof JAXBElement
                    && ((JAXBElement) object).getDeclaredType().equals(reference)) {

                result.add((T) ((JAXBElement) object).getValue());
            }
        }
        return result;
    }

    /**
     * Gets JAXB elements matching the given type and the given name.
     *
     * @param <T> element type to be retrieved.
     * @param localName element name to be retrieved.
     * @param reference reference class.
     * @return list of JABX elements.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getJAXBElements(final String localName, final Class<T> reference) {
        List<T> result = new ArrayList<T>();

        for (Object object : getValues()) {
            if (object instanceof JAXBElement
                    && ((JAXBElement) object).getDeclaredType().equals(reference)
                    && ((JAXBElement) object).getName().getLocalPart().equals(localName)) {

                result.add((T) ((JAXBElement) object).getValue());
            }
        }
        return result;
    }

    /**
     * Removes JAXB elements matching the given type and the given name.
     *
     * @param <T> element type to be retrieved.
     * @param localName element name to be retrieved.
     * @param reference reference class.
     * @return 'TRUE' if the element has been removed; 'FALSE' otherwise.
     */
    @SuppressWarnings({"unchecked"})
    protected <T> boolean removeJAXBElements(final String localName, final Class<T> reference) {

        boolean res = true;

        final List<JAXBElement<T>> toBeRemoved = new ArrayList<JAXBElement<T>>();

        for (Object object : getValues()) {
            if (object instanceof JAXBElement
                    && ((JAXBElement<T>) object).getDeclaredType().equals(reference)
                    && ((JAXBElement<T>) object).getName().getLocalPart().equals(localName)) {

                toBeRemoved.add((JAXBElement<T>) object);
            }
        }

        for (JAXBElement<T> obj : toBeRemoved) {
            res = res && getValues().remove(obj);
        }

        return res;
    }

    /**
     * Gets elements matching the given type.
     *
     * @param <T> element type to be retrieved.
     * @param reference reference class.
     * @return list of elements.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getElements(final Class<T> reference) {
        final List<T> result = new ArrayList<T>();

        for (Object object : getValues()) {
            if (reference.isAssignableFrom(object.getClass())) {
                result.add((T) object);
            }
        }
        return result;
    }
}
