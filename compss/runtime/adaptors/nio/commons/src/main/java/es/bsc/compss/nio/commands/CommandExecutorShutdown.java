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
package es.bsc.compss.nio.commands;

import es.bsc.comm.Connection;
import es.bsc.compss.nio.NIOAgent;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class CommandExecutorShutdown implements Command {

    /**
     * Creates a new CommandExecutorShutdown for externalization.
     */
    public CommandExecutorShutdown() {
        super();
    }

    @Override
    public void handle(NIOAgent agent, Connection c) {
        agent.shutdownExecutionManager(c);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // Nothing to do
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Nothing to do
    }

    @Override
    public String toString() {
        return "ExecutorShutdown";
    }

    @Override
    public void error(NIOAgent agent, Connection c) {
        agent.handleExecutorShutdownCommandError(c, this);

    }

}
