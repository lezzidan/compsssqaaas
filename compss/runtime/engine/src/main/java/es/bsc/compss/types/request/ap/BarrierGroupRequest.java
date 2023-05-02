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

import es.bsc.compss.components.impl.TaskAnalyser;
import es.bsc.compss.types.Application;
import es.bsc.compss.types.tracing.TraceEvent;


public class BarrierGroupRequest extends BarrierRequest {

    private final String groupName;


    /**
     * Creates a new group barrier request.
     *
     * @param app Application Id.
     * @param groupName Name of the group.
     */
    public BarrierGroupRequest(Application app, String groupName) {
        super(app, "Group " + groupName + "'s barrier");
        this.groupName = groupName;
    }

    @Override
    public TraceEvent getEvent() {
        return TraceEvent.WAIT_FOR_ALL_TASKS;
    }

    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public void handleBarrier(TaskAnalyser ta) {
        ta.barrierGroup(this);
    }

}
