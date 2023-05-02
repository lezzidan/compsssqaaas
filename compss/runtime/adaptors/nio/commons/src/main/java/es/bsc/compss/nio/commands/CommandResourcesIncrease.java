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
import es.bsc.compss.types.resources.MethodResourceDescription;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class CommandResourcesIncrease implements Command {

    private MethodResourceDescription description;


    /**
     * Creates a new CommandResourcesIncrease for externalization.
     */
    public CommandResourcesIncrease() {
        super();
    }

    /**
     * Creates a new CommandResourcesIncrease instance.
     *
     * @param description Increasing resource description.
     */
    public CommandResourcesIncrease(MethodResourceDescription description) {
        this.description = description;
    }

    @Override
    public void handle(NIOAgent agent, Connection c) {
        agent.increaseResources(this.description);
        c.sendCommand(new CommandResourcesReduced());
        c.finishConnection();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.description = (MethodResourceDescription) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.description);
    }

    @Override
    public String toString() {
        return "CommandIncreaseResources " + this.description;
    }

    @Override
    public void error(NIOAgent agent, Connection c) {
        // Nothing to do

    }

}
