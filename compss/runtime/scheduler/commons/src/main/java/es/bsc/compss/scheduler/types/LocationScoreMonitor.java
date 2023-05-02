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
package es.bsc.compss.scheduler.types;

import es.bsc.compss.types.data.LocationMonitor;
import es.bsc.compss.types.resources.Resource;
import java.util.List;


public class LocationScoreMonitor implements LocationMonitor {

    private final double score;
    private final SchedulingInformation info;


    /**
     * Constructs a new LocationScoreMonitor to update the locality score.
     * 
     * @param info scheduling info structure to notify the update
     * @param score amount of score increase
     */
    public LocationScoreMonitor(SchedulingInformation info, double score) {
        this.info = info;
        this.score = score;
    }

    @Override
    public void addedLocation(List<Resource> list) {
        info.increasePreregisteredScores(list, score);
    }

}
