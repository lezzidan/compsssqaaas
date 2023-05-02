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
package es.bsc.compss.scheduler.lookahead.fifo;

import es.bsc.compss.scheduler.lookahead.LookaheadTS;
import es.bsc.compss.scheduler.types.AllocatableAction;
import es.bsc.compss.scheduler.types.Score;
import es.bsc.compss.types.resources.Worker;
import es.bsc.compss.types.resources.WorkerResourceDescription;

import org.json.JSONObject;


/**
 * Representation of a Scheduler that considers only ready tasks and sorts them in FIFO mode.
 */
public class FifoTS extends LookaheadTS {

    /**
     * Constructs a new FIFOScheduler instance.
     */
    public FifoTS() {
        super();
    }

    /*
     * *********************************************************************************************************
     * *********************************************************************************************************
     * ***************************** UPDATE STRUCTURES OPERATIONS **********************************************
     * *********************************************************************************************************
     * *********************************************************************************************************
     */
    @Override
    public <T extends WorkerResourceDescription> FifoRS<T> generateSchedulerForResource(Worker<T> w, JSONObject resJSON,
        JSONObject implJSON) {
        return new FifoRS<>(w, resJSON, implJSON);
    }

    @Override
    public Score generateActionScore(AllocatableAction action) {
        return new Score(action.getPriority(), action.getGroupPriority(), -action.getId(), 0, 0);
    }

}
