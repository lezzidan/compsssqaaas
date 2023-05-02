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

import es.bsc.compss.executor.external.commands.CreateChannelExternalCommand;
import es.bsc.compss.executor.external.piped.PipePair;


public class CreateChannelPipeCommand extends CreateChannelExternalCommand implements PipeCommand {

    private final PipePair pipe;


    public CreateChannelPipeCommand(PipePair pipe) {
        this.pipe = pipe;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder(super.getAsString());
        sb.append(TOKEN_SEP).append(pipe.getOutboundPipe());
        sb.append(TOKEN_SEP).append(pipe.getInboundPipe());
        return sb.toString();
    }

    @Override
    public int compareTo(PipeCommand t) {
        return Integer.compare(this.getType().ordinal(), t.getType().ordinal());
    }

    @Override
    public void join(PipeCommand receivedCommand) {
        // Do nothing
    }

}
