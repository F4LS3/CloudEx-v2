package de.f4ls3developer.cloudexv2.cloudapi;

import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionClient;
import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionServer;
import de.f4ls3developer.cloudexv2.cloudapi.utils.ProcessFactory;

public class CloudAPI {

    private static final CloudAPI INSTANCE = new CloudAPI();

    private ConnectionServer masterServer;
    private ConnectionClient wrapperClient;

    public ConnectionServer getMasterServer() {
        return masterServer;
    }

    public ConnectionClient getWrapperClient() {
        return wrapperClient;
    }

    public void setMasterServer(ConnectionServer masterServer) {
        this.masterServer = masterServer;
    }

    public void setWrapperClient(ConnectionClient wrapperClient) {
        this.wrapperClient = wrapperClient;
    }

    public static CloudAPI getInstance() {
        return INSTANCE;
    }
}
