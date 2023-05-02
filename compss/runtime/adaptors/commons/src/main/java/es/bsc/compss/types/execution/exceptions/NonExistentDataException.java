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
package es.bsc.compss.types.execution.exceptions;

import java.util.LinkedList;
import java.util.List;


public class NonExistentDataException extends Exception {

    /**
     * Exception Version UID are 2L in all Runtime.
     */
    private static final long serialVersionUID = 2L;

    protected String dataName;


    /**
     * Constructs a new Exception.
     * 
     * @param dataName name of the non-existing data
     */
    public NonExistentDataException(String dataName) {
        super();
        this.dataName = dataName;
    }

    @Override
    public String getMessage() {
        return "Data " + dataName + " does not exists.";
    }

    /**
     * Returns the list of all the missing data values.
     * 
     * @return name of the data missing.
     */
    public List<String> getMissingData() {
        List<String> missingData = new LinkedList<>();
        missingData.add(dataName);
        return missingData;
    }

}
