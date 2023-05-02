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
import es.bsc.compss.types.data.ResultFile;
import es.bsc.compss.types.tracing.TraceEvent;

import java.util.List;


public class UnblockResultFilesRequest extends APRequest {

    private final List<ResultFile> resultFiles;


    /**
     * Creates a new request to unlock the given result files.
     * 
     * @param resultFiles Result files to unlock.
     */
    public UnblockResultFilesRequest(List<ResultFile> resultFiles) {
        this.resultFiles = resultFiles;
    }

    /**
     * Returns the list of associated result files to unlock.
     * 
     * @return The list of associated result files to unlock.
     */
    public List<ResultFile> getResultFiles() {
        return this.resultFiles;
    }

    @Override
    public void process(AccessProcessor ap, TaskAnalyser ta, DataInfoProvider dip, TaskDispatcher td) {
        for (ResultFile resFile : this.resultFiles) {
            dip.unblockDataId(resFile.getFileInstanceId().getDataId());
        }
    }

    @Override
    public TraceEvent getEvent() {
        return TraceEvent.UNBLOCK_RESULT_FILES;
    }

}
