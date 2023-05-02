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
package es.bsc.compss.invokers.test.utils;

import es.bsc.compss.COMPSsConstants.Lang;
import es.bsc.compss.COMPSsConstants.TaskExecution;
import es.bsc.compss.api.COMPSsRuntime;
import es.bsc.compss.loader.LoaderAPI;
import es.bsc.compss.types.annotations.parameter.DataType;
import es.bsc.compss.types.execution.Invocation;
import es.bsc.compss.types.execution.InvocationContext;
import es.bsc.compss.types.execution.InvocationParam;
import es.bsc.compss.types.execution.LanguageParams;
import es.bsc.compss.types.execution.exceptions.UnloadableValueException;
import es.bsc.compss.types.resources.ResourceDescription;
import es.bsc.distrostreamlib.server.types.StreamBackend;

import java.io.PrintStream;
import storage.StorageException;
import storage.StubItf;


public class FakeInvocationContext implements InvocationContext {

    private final String hostName;
    private final String appDir;
    private final String installDir;
    private final String workingDir;
    private final String logDir;
    private final PrintStream out;
    private final PrintStream err;
    private final InvocationContextListener listener;


    private FakeInvocationContext() {
        hostName = "localhost";
        appDir = "";
        installDir = "";
        workingDir = "";
        logDir = "";
        out = System.out;
        err = System.err;
        listener = null;
    }

    private FakeInvocationContext(String hostName, String appDir, String installDir, String wDir, String lDir,
        PrintStream out, PrintStream err, InvocationContextListener listener) {

        this.hostName = hostName;
        this.appDir = appDir;
        this.installDir = installDir;
        this.workingDir = wDir;
        this.logDir = lDir;
        this.out = out;
        this.err = err;
        this.listener = listener;
    }

    @Override
    public String getHostName() {
        return this.hostName;
    }

    @Override
    public String getAppDir() {
        return this.appDir;
    }

    @Override
    public String getInstallDir() {
        return this.installDir;
    }

    @Override
    public String getWorkingDir() {
        return this.workingDir;
    }

    @Override
    public String getLogDir() {
        return this.logDir;
    }

    @Override
    public TaskExecution getExecutionType() {
        return TaskExecution.COMPSS;
    }

    @Override
    public boolean isPersistentCEnabled() {
        return false;
    }

    @Override
    public LanguageParams getLanguageParams(Lang lang) {
        return new LanguageParams() {
        };
    }

    @Override
    public void registerOutputs(String outputsBasename) {
        // Do nothing
    }

    @Override
    public void unregisterOutputs() {
        // Do nothing
    }

    @Override
    public String getStandardStreamsPath(Invocation invocation) {
        return null;
    }

    @Override
    public PrintStream getThreadOutStream() {
        return this.out;
    }

    @Override
    public PrintStream getThreadErrStream() {
        return this.err;
    }

    @Override
    public String getStorageConf() {
        return null;
    }

    @Override
    public StreamBackend getStreamingBackend() {
        return StreamBackend.NONE;
    }

    @Override
    public String getStreamingMasterName() {
        return null;
    }

    @Override
    public int getStreamingMasterPort() {
        return -1;
    }

    @Override
    public void loadParam(InvocationParam param) throws UnloadableValueException {
        Object o;
        switch (param.getType()) {
            case OBJECT_T:
            case STREAM_T:
                o = getObject(param.getDataMgmtId());
                if (o != null) {
                    param.setValue(o);
                } else {
                    // Checking if initial object is now a persistent object
                    try {
                        o = getPersistentObject(param.getDataMgmtId());
                    } catch (StorageException se) {
                        throw new UnloadableValueException(se);
                    }
                    if (o != null) {
                        param.setType(DataType.PSCO_T);
                        param.setValue(o);
                    }
                }
                break;
            case PSCO_T:
                try {
                    o = getPersistentObject(param.getDataMgmtId());
                } catch (StorageException se) {
                    throw new UnloadableValueException(se);
                }
                if (o != null) {
                    param.setType(DataType.PSCO_T);
                    param.setValue(o);
                }
                break;
            default:
        }

    }

    private Object getObject(String rename) {
        if (this.listener != null) {
            return listener.getObject(rename);
        }
        return null;
    }

    private Object getPersistentObject(String id) throws StorageException {
        if (this.listener != null) {
            return listener.getPersistentObject(id);
        }
        return null;
    }

    @Override
    public void storeParam(InvocationParam param, boolean createifNonExistent) {
        switch (param.getType()) {
            case OBJECT_T:
            case STREAM_T:
                storeObject(param.getDataMgmtId(), param.getValue());
                break;
            case PSCO_T:
                String dataId = ((StubItf) param.getValue()).getID();
                storePersistentObject(dataId, param.getValue());
                break;
            default:
                // do nothing
        }
    }

    /**
     * Store Object.
     * 
     * @param renaming renaming
     * @param value value
     */
    public void storeObject(String renaming, Object value) {
        if (this.listener != null) {
            listener.storeObject(renaming, value);
        }
    }

    /**
     * Store persistenst object.
     * 
     * @param id PSCO Id
     * @param obj Object
     */
    public void storePersistentObject(String id, Object obj) {
        if (this.listener != null) {
            listener.storePersistentObject(id, obj);
        }
    }

    @Override
    public long getTracingHostID() {
        return 0;
    }

    @Override
    public COMPSsRuntime getRuntimeAPI() {
        return null;
    }

    @Override
    public LoaderAPI getLoaderAPI() {
        return null;
    }

    @Override
    public void idleReservedResourcesDetected(ResourceDescription resources) {
    }

    @Override
    public void reactivatedReservedResourcesDetected(ResourceDescription resources) {
    }

    @Override
    public String getEnvironmentScript() {
        return null;
    }


    public static class Builder {

        final FakeInvocationContext context;


        public Builder() {
            context = new FakeInvocationContext();
        }

        public Builder(FakeInvocationContext context) {
            this.context = context;
        }

        public Builder setListener(InvocationContextListener listener) {
            return new Builder(new FakeInvocationContext(context.hostName, context.appDir, context.installDir,
                context.workingDir, context.logDir, context.out, context.err, listener));
        }

        public FakeInvocationContext build() {
            return context;
        }
    }

    public static interface InvocationContextListener {

        public Object getObject(String rename);

        public Object getPersistentObject(String id);

        public void storePersistentObject(String id, Object obj);

        public void storeObject(String renaming, Object value);

    }

}
