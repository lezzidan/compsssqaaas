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
package es.bsc.compss.util;

import es.bsc.compss.COMPSsConstants;
import es.bsc.compss.components.impl.ResourceScheduler;
import es.bsc.compss.log.Loggers;
import es.bsc.compss.scheduler.types.Profile;
import es.bsc.compss.types.CloudProvider;
import es.bsc.compss.types.CoreElement;
import es.bsc.compss.types.implementations.Implementation;
import es.bsc.compss.types.resources.CloudMethodWorker;
import es.bsc.compss.types.resources.Worker;
import es.bsc.compss.types.resources.WorkerResourceDescription;
import es.bsc.compss.types.resources.description.CloudInstanceTypeDescription;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONException;
import org.json.JSONObject;


public class JSONStateManager {

    private static final Logger LOGGER = LogManager.getLogger(Loggers.TS_COMP);

    private final JSONObject jsonRepresentation;


    /**
     * Creates a new JSONStateManager instance.
     */
    public JSONStateManager() {
        String objectStr = "{}";
        String inputProfile = System.getProperty(COMPSsConstants.INPUT_PROFILE);
        if (inputProfile != null && !inputProfile.isEmpty()) {
            LOGGER.debug("Input profile detected. Reading from file " + inputProfile);
            objectStr = manageInputProfile(inputProfile);
        }

        this.jsonRepresentation = new JSONObject(objectStr);
        init();
    }

    private String manageInputProfile(String inputProfile) {
        File f = new File(inputProfile);
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes, "UTF-8");
        } catch (FileNotFoundException e) {
            ErrorManager
                .warn("Error loading profile. File " + f.getAbsolutePath() + " not found. Using default values");
            return "{}";
        } catch (IOException e) {
            ErrorManager
                .warn("Error loading profile. Exception reading " + f.getAbsolutePath() + ". Using default values", e);
            return "{}";
        }

    }

    private void init() {
        JSONObject resources = getJSONForResources();
        if (resources == null) {
            addResources();
        }
        JSONObject implementations = getJSONForImplementations();
        if (implementations == null) {
            addImplementations();
        }
        JSONObject cloud = getJSONForCloud();
        if (cloud == null) {
            addCloud();
        }
    }

    private JSONObject addResources() {
        JSONObject resources = new JSONObject();
        this.jsonRepresentation.put("resources", resources);
        return resources;
    }

    private JSONObject addImplementations() {
        JSONObject implementations = new JSONObject();
        this.jsonRepresentation.put("implementations", implementations);
        return implementations;
    }

    private JSONObject addCloud() {
        JSONObject cloud = new JSONObject();
        this.jsonRepresentation.put("cloud", cloud);
        return cloud;
    }

    /**
     * Returns a JSON Object representation for the implementations.
     * 
     * @return A JSON Object representation for the implementations.
     */
    public JSONObject getJSONForImplementations() {
        try {
            return this.jsonRepresentation.getJSONObject("implementations");
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the implementation with the given implementation.
     * 
     * @param impl Implementation
     * @return A JSON Object representation for the implementation with the given implementation
     */
    public JSONObject getJSONForImplementation(Implementation impl) {
        try {
            JSONObject implsJSON = this.jsonRepresentation.getJSONObject("implementations");
            String signature = impl.getSignature();
            return implsJSON.getJSONObject(signature);
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the given cloud implementation.
     * 
     * @param cp Cloud Provider.
     * @param citd Cloud Instance Type.
     * @param impl Implementation .
     * @return A JSON Object representation for the given cloud implementation.
     */
    public JSONObject getJSONForImplementation(CloudProvider cp, CloudInstanceTypeDescription citd,
        Implementation impl) {
        try {
            JSONObject citdJSON = getJSONForCloudInstanceTypeDescription(cp, citd);
            if (citdJSON != null) {
                String signature = impl.getSignature();
                return citdJSON.getJSONObject(signature);
            }
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the resources.
     * 
     * @return A JSON Object representation for the resources.
     */
    public <T extends WorkerResourceDescription> JSONObject getJSONForResources() {
        try {
            return this.jsonRepresentation.getJSONObject("resources");
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the given resource {@code resource}.
     * 
     * @param resource Worker resource.
     * @return A JSON Object representation for the given resource {@code resource}.
     */
    public <T extends WorkerResourceDescription> JSONObject getJSONForResource(Worker<T> resource) {
        JSONObject resourcesJSON = getJSONForResources();
        if (resourcesJSON != null) {
            try {
                return resourcesJSON.getJSONObject(resource.getName());
            } catch (JSONException je) {
                if (resource instanceof CloudMethodWorker) {
                    CloudMethodWorker cmw = (CloudMethodWorker) resource;
                    if (!cmw.getDescription().getTypeComposition().isEmpty()) {
                        return getJSONForCloudInstanceTypeDescription(cmw.getProvider(),
                            cmw.getDescription().getTypeComposition().keySet().iterator().next());
                    }
                }
            }
        }

        return null;
    }

    /**
     * Returns a JSON Object representation for the cloud.
     * 
     * @return A JSON Object representation for the cloud.
     */
    public JSONObject getJSONForCloud() {
        try {
            return this.jsonRepresentation.getJSONObject("cloud");
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the given cloud provider.
     * 
     * @param cp Cloud provider.
     * @return A JSON Object representation for the cloud.
     */
    public JSONObject getJSONForCloudProvider(CloudProvider cp) {
        try {
            JSONObject cloudJSON = getJSONForCloud();
            if (cloudJSON != null) {
                return cloudJSON.getJSONObject(cp.getName());
            }
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Returns a JSON Object representation for the given cloud instance type.
     * 
     * @param cp Cloud Provider.
     * @param citd Cloud Instance Type.
     * @return A JSON Object representation for the given cloud instance type.
     */
    public JSONObject getJSONForCloudInstanceTypeDescription(CloudProvider cp, CloudInstanceTypeDescription citd) {
        try {
            JSONObject cpJSON = getJSONForCloudProvider(cp);
            if (cpJSON != null) {
                return cpJSON.getJSONObject(citd.getName());
            }
        } catch (JSONException je) {
            // Do nothing it will return null
        }
        return null;
    }

    /**
     * Adds the given resource to the JSON Object.
     * 
     * @param rs ResourceScheduler to add.
     */
    public void addResourceJSON(ResourceScheduler<? extends WorkerResourceDescription> rs) {
        // Increasing Implementation stats
        for (CoreElement ce : CoreManager.getAllCores()) {
            for (Implementation impl : ce.getImplementations()) {
                JSONObject implJSON = getJSONForImplementation(impl);
                Profile p = rs.getProfile(impl);
                if (implJSON == null) {
                    addImplementationJSON(impl, p);
                } else {
                    accumulateImplementationJSON(impl, p);
                }
            }
        }
        // Attaching new resource to the resources
        JSONObject resources = getJSONForResources();
        resources.put(rs.getName(), rs.toJSONObject());
    }

    /**
     * Updates the given resource information on the JSON Object.
     * 
     * @param rs ResourceSchedulr to update.
     * @return Updated JSON Object.
     */
    public JSONObject updateResourceJSON(ResourceScheduler<? extends WorkerResourceDescription> rs) {
        JSONObject oldResource = getJSONForResource(rs.getResource());
        return rs.updateJSON(oldResource);
    }

    /**
     * Adds a new implementation to the JSON Object.
     * 
     * @param impl Implementation.
     * @param profile Execution profile.
     */
    public void addImplementationJSON(Implementation impl, Profile profile) {
        String signature = impl.getSignature();
        JSONObject implsJSON = this.getJSONForImplementations();
        implsJSON.put(signature, profile.toJSONObject());
    }

    /**
     * Accumulates the given profile of the given implementation into the JSON Object.
     * 
     * @param impl Implementation.
     * @param profile Execution profile to accumulate.
     */
    public void accumulateImplementationJSON(Implementation impl, Profile profile) {
        // int coreId = impl.getCoreId();
        // int implId = impl.getImplementationId();
        JSONObject oldImplJSON = this.getJSONForImplementation(impl);
        profile.accumulateJSON(oldImplJSON);
    }

    /**
     * Returns a String representation of the internal JSON Object.
     * 
     * @return A String representation of the internal JSON Object.
     */
    public String getString() {
        return this.jsonRepresentation.toString();
    }

    /**
     * Dumps the internal JSON Object into file.
     */
    public void write() {
        String outputProfile = System.getProperty(COMPSsConstants.OUTPUT_PROFILE);
        if (outputProfile != null && !outputProfile.isEmpty()) {
            LOGGER.debug("Output profile detected. Writting to file " + outputProfile);
            writeOutputProfile(outputProfile);
        }
    }

    /**
     * Dumps the internal JSON Object into the given file path {@code filename}.
     * 
     * @param filename File path.
     */
    public void writeOutputProfile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename));) {
            writer.write(this.jsonRepresentation.toString());
        } catch (IOException e) {
            ErrorManager.warn("Error loading profile. Exception reading " + filename + ".", e);
        }
    }
}
