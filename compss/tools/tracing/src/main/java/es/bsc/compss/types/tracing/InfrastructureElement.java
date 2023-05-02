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

package es.bsc.compss.types.tracing;

import java.util.LinkedList;
import java.util.List;


public class InfrastructureElement {

    private final List<InfrastructureElement> components;
    private String label;


    public InfrastructureElement(String label) {
        this.label = label;
        this.components = new LinkedList<>();
    }

    public void appendComponent(InfrastructureElement c) {
        this.components.add(c);
    }

    public List<InfrastructureElement> getSubComponents() {
        return this.components;
    }

    public int getNumberOfDirectSubcomponents() {
        return this.components.size();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * debug method.
     * 
     * @param pad pad
     * @return message
     */

    public String print(String pad) {
        StringBuilder s = new StringBuilder(pad + label + "\n");
        for (InfrastructureElement component : components) {
            s.append(component.print(pad + "\t"));
        }
        return s.toString();
    }
}
