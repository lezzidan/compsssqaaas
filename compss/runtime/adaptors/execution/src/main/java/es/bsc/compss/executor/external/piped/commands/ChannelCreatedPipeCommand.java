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
package es.bsc.compss.executor.external.piped.commands;

import es.bsc.compss.executor.external.commands.ChannelCreatedExternalCommand;
import es.bsc.compss.executor.external.piped.PipePair;


public class ChannelCreatedPipeCommand extends ChannelCreatedExternalCommand implements PipeCommand {

    private final String pipeIn;
    private final String pipeOut;


    public ChannelCreatedPipeCommand(PipePair pp) {
        this.pipeIn = pp.getInboundPipe();
        this.pipeOut = pp.getOutboundPipe();
    }

    public ChannelCreatedPipeCommand(String[] command) {
        this.pipeIn = command[2];
        this.pipeOut = command[1];
    }

    @Override
    public int compareTo(PipeCommand t) {
        int value = Integer.compare(this.getType().ordinal(), t.getType().ordinal());
        if (value == 0) {
            value = pipeIn.compareTo(((ChannelCreatedPipeCommand) t).pipeIn);
        }
        if (value == 0) {
            value = pipeOut.compareTo(((ChannelCreatedPipeCommand) t).pipeOut);
        }
        return value;
    }

    @Override
    public void join(PipeCommand receivedCommand) {

    }

    @Override
    public String getAsString() {
        return super.getAsString() + " " + pipeIn;
    }

}
