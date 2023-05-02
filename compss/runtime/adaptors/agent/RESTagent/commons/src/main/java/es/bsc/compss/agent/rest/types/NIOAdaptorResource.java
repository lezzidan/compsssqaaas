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
package es.bsc.compss.agent.rest.types;

import es.bsc.compss.agent.types.Resource;
import es.bsc.compss.types.project.jaxb.NIOAdaptorProperties;
import es.bsc.compss.types.resources.MethodResourceDescription;
import es.bsc.compss.types.resources.jaxb.ResourcesNIOAdaptorProperties;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name = "nioResource")
@XmlSeeAlso({ NIOAdaptorProperties.class,
    ResourcesNIOAdaptorProperties.class })
public class NIOAdaptorResource extends Resource<NIOAdaptorProperties, ResourcesNIOAdaptorProperties> {

    public NIOAdaptorResource() {
    }

    public NIOAdaptorResource(String name, MethodResourceDescription description, NIOAdaptorProperties projectConf,
        ResourcesNIOAdaptorProperties resourcesConf) {
        super(name, description, "es.bsc.compss.nio.master.NIOAdaptor", projectConf, resourcesConf);

    }

}
