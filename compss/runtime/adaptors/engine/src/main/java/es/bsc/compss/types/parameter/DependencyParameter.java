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
package es.bsc.compss.types.parameter;

import es.bsc.compss.types.data.DataAccessId;
import es.bsc.compss.types.data.Transferable;


public interface DependencyParameter extends Parameter, Transferable {

    public static final String NO_NAME = "NO_NAME";


    @Override
    public boolean isPotentialDependency();

    /**
     * Returns the data access id.
     *
     * @return The data access id.
     */
    public DataAccessId getDataAccessId();

    /**
     * Sets a new data access id.
     *
     * @param daId New data access id.
     */
    public void setDataAccessId(DataAccessId daId);

    /**
     * Returns the parameter's original name.
     *
     * @return The parameter's original name.
     */
    public String getOriginalName();

    /**
     * Return the corresponding data target value for this type of dependency parameter.
     *
     * @param tgtName Proposed target name
     * @return data target name
     */
    public String generateDataTargetName(String tgtName);

}
