/*
 *  Copyright 2002-2022 Barcelona Supercomputing Center (www.bsc.es)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package es.bsc.compss.types.data.accessparams;

import es.bsc.compss.comm.Comm;
import es.bsc.compss.components.impl.DataInfoProvider;
import es.bsc.compss.types.Application;
import es.bsc.compss.types.annotations.parameter.Direction;
import es.bsc.compss.types.data.DataInfo;
import es.bsc.compss.types.data.DataInstanceId;
import es.bsc.compss.types.data.DataParams.CollectionData;
import es.bsc.compss.types.data.DataVersion;
import es.bsc.compss.types.request.exceptions.ValueUnawareRuntimeException;


public class CollectionAccessParams extends AccessParams<CollectionData> {

    /**
     * Serializable objects Version UID are 1L in all Runtime.
     */
    private static final long serialVersionUID = 1L;


    /**
     * Creates a new ObjectAccessParams instance for the given object.
     *
     * @param app Id of the application accessing the object.
     * @param dir operation performed.
     * @param collectionId Id of the collection
     * @return new CollectionAccessParams instance
     */
    public static final CollectionAccessParams constructCAP(Application app, Direction dir, String collectionId) {
        return new CollectionAccessParams(app, dir, collectionId);
    }

    private CollectionAccessParams(Application app, Direction dir, String collectionId) {
        super(new CollectionData(app, collectionId), dir);
    }

    @Override
    public void checkAccessValidity(DataInfoProvider dip) throws ValueUnawareRuntimeException {
        // Accesses to collections are always valids.
    }

    @Override
    public void registeredAsFirstVersionForData(DataInfo dInfo) {
        DataVersion dv = dInfo.getCurrentDataVersion();
        if (mode != AccessMode.W) {
            DataInstanceId lastDID = dv.getDataInstanceId();
            String renaming = lastDID.getRenaming();
            // Null until the two-step transfer method is implemented
            Comm.registerCollection(renaming, null);
        } else {
            dv.invalidate();
        }
    }

    @Override
    public boolean resultRemainOnMain() {
        return false;
    }

    @Override
    public void externalRegister() {
        // Do nothing. No need to register the access anywhere.
    }
}
