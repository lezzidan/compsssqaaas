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
package es.bsc.compss.types;

/**
 * Handler invoked by the ErrorManager whenever a fatal error raises and needs to stop the whole system.
 */
public interface ErrorHandler {

    /**
     * Notifies an error to be handled.
     * 
     * @return {@literal true} if the error handling shuts down COMPSs.
     */
    public boolean handleError();

    /**
     * Notifies a fatal error to be handled.
     * 
     * @return {@literal true} if the error handling shuts down COMPSs.
     */
    public boolean handleFatalError();

}
