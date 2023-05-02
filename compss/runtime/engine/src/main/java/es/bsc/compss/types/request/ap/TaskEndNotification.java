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
package es.bsc.compss.types.request.ap;

import es.bsc.compss.components.impl.AccessProcessor;
import es.bsc.compss.components.impl.DataInfoProvider;
import es.bsc.compss.components.impl.TaskAnalyser;
import es.bsc.compss.components.impl.TaskDispatcher;
import es.bsc.compss.types.AbstractTask;
import es.bsc.compss.types.tracing.TraceEvent;


public class TaskEndNotification extends APRequest {

    private AbstractTask task;


    /**
     * Creates a new request to end a task execution.
     * 
     * @param task Ended task.
     */
    public TaskEndNotification(AbstractTask task) {
        this.task = task;
    }

    /**
     * Returns the associated ended task.
     * 
     * @return The associated ended task.
     */
    public AbstractTask getTask() {
        return this.task;
    }

    public void setTask(AbstractTask task) {
        this.task = task;
    }

    @Override
    public void process(AccessProcessor ap, TaskAnalyser ta, DataInfoProvider dip, TaskDispatcher td) {
        ta.endTask(this.task, false);
    }

    @Override
    public TraceEvent getEvent() {
        return TraceEvent.UPDATE_GRAPH;
    }

}
