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
package es.bsc.compss.worker;

import es.bsc.compss.log.Loggers;
import es.bsc.compss.worker.COMPSsWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CanceledTask {

    private static final Logger LOGGER = LogManager.getLogger(Loggers.WORKER_INVOKER);

    private final int taskId;


    public CanceledTask(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Sets the task to cancel.
     */
    public void cancel() {
        LOGGER.info("Task " + this.taskId + " has been canceled.");
        COMPSsWorker.setCancelled(this.taskId, CancelReason.COMPSS_EXCEPTION);
    }
}
