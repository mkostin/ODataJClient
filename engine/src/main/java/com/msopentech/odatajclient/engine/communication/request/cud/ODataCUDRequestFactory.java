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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.communication.request.UpdateType;
import com.msopentech.odatajclient.engine.data.ODataEntity;
import com.msopentech.odatajclient.engine.data.ODataLink;
import com.msopentech.odatajclient.engine.data.ODataPrimitiveValue;
import com.msopentech.odatajclient.engine.data.ODataProperty;
import java.io.InputStream;
import java.net.URI;

/**
 * OData request factory class.
 */
public class ODataCUDRequestFactory {

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new entity.
     *
     * @param targetURI entity set URI.
     * @return new ODataCreateEntityRequest instance.
     */
    public static ODataEntityCreateRequest getEntityCreateRequest(final URI targetURI, final ODataEntity entity) {
        return new ODataEntityCreateRequest(targetURI, entity);
    }

    /**
     * Gets a media entity create request object instance.
     * <p>
     * Use this kind of request to create a new media entity.
     *
     * @param targetURI entity set URI.
     * @param entity entity blob to be created.
     * @return new ODataMediaEntityCreateRequest instance.
     */
    public static ODataMediaEntityCreateRequest getMediaEntityCreateRequest(final URI targetURI, final InputStream media) {
        return new ODataMediaEntityCreateRequest(targetURI, media);
    }

    /**
     * Gets a stream update request object instance.
     * <p>
     * Use this kind of request to update a named stream property.
     *
     * @param targetURI target URI.
     * @param stream stream to be updated.
     * @return new ODataUpdateStreamRequest instance.
     */
    public static ODataStreamUpdateRequest getStreamUpdateRequest(final URI targetURI, final InputStream stream) {
        return new ODataStreamUpdateRequest(targetURI, stream);
    }

    /**
     * Gets a media entity update request object instance.
     * <p>
     * Use this kind of request to update a media entity.
     *
     * @param editURI media entity edit link URI.
     * @param entity entity blob to be updated.
     * @return new ODataMediaEntityUpdateRequest instance.
     */
    public static ODataMediaEntityUpdateRequest getMediaEntityUpdateRequest(final URI editURI, final InputStream media) {
        return new ODataMediaEntityUpdateRequest(editURI, media);
    }

    /**
     * Gets a create request object instance.
     * <p>
     * Use this kind of request to create a new value (e.g. http://Northwind.svc/Customer(1)/Picture/$value).
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param value value to be created.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataValueUpdateRequest getValueUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataPrimitiveValue value) {
        return new ODataValueUpdateRequest(targetURI, type, value);
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a complex property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getComplexUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataProperty property) {
        return new ODataPropertyUpdateRequest(targetURI, type, property);
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a primitive property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getPrimitiveUpdateRequest(
            final URI targetURI, final ODataProperty property) {
        return new ODataPropertyUpdateRequest(targetURI, UpdateType.REPLACE, property);
    }

    /**
     * Gets an update request object instance.
     * <p>
     * Use this kind of request to update a collection property value
     *
     * @param targetURI entity set or entity or entity property URI.
     * @param type type of update to be performed.
     * @param property value to be update.
     * @return new ODataCreatePrimitiveRequest instance.
     */
    public static ODataPropertyUpdateRequest getCollectionUpdateRequest(
            final URI targetURI, final ODataProperty property) {
        return new ODataPropertyUpdateRequest(targetURI, UpdateType.REPLACE, property);
    }

    /**
     * Gets an add link request object instance.
     * <p>
     * Use this kind of request to create a navigation link between existing entities.
     *
     * @param targetURI navigation property's link collection.
     * @param entityToBeAdded navigation link to be added.
     * @return new ODataInsertLinkRequest instance.
     */
    public static ODataInsertLinkRequest getInsertLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        return new ODataInsertLinkRequest(targetURI, entityToBeAdded);
    }

    /**
     * Gets a remove link request object instance.
     * <p>
     * Use this kind of request to remove a navigation link between existing entities.
     *
     * @param linkToBeRemoved navigation link to be removed.
     * @return new ODataRemovedLinkRequest instance.
     */
    public static ODataRemoveLinkRequest getRemoveLinkRequest(final URI linkToBeRemoved) {
        return new ODataRemoveLinkRequest(linkToBeRemoved);
    }

    /**
     * Gets a link update request object instance.
     * <p>
     * Use this kind of request to update a navigation link between existing entities.
     * <p>
     * In case of the old navigation link doesn't exist the new one will be added as well.
     *
     * @param targetURI navigation property's link collection.
     * @param linkToBeRemoved navigation link to be removed.
     * @param entityToBeAdded URL that identifies the entity to be linked.
     * @return new ODataUpdateLinkRequest instance.
     */
    public static ODataUpdateLinkRequest getUpdateLinkRequest(final URI targetURI, final ODataLink entityToBeAdded) {
        return new ODataUpdateLinkRequest(targetURI, entityToBeAdded);
    }

    /**
     * Gets a delete request object instance.
     * <p>
     * Use this kind of request to delete an entity and media entity as well.
     *
     * @param targetURI edit link of the object to be removed.
     * @return new ODataDeleteRequest instance.
     */
    public static ODataDeleteRequest getDeleteRequest(final URI targetURI) {
        return new ODataDeleteRequest(targetURI);
    }

    /**
     * Gets an update request object instance.
     *
     * @param targetURI edit link of the object to be updated.
     * @param entity changes to be applied.
     * @param type type of update to be performed.
     * @return new ODataUpdateEntityRequest instance.
     */
    public static ODataEntityUpdateRequest getEntityUpdateRequest(
            final URI targetURI, final UpdateType type, final ODataEntity changes) {
        return new ODataEntityUpdateRequest(targetURI, type, changes);
    }
}
