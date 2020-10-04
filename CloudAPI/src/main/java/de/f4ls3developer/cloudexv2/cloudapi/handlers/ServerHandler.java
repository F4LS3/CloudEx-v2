package de.f4ls3developer.cloudexv2.cloudapi.handlers;

import de.f4ls3developer.cloudexv2.cloudapi.utils.ServerProcess;

import java.util.HashMap;

public class ServerHandler {

    private HashMap<String, ServerProcess> processMap;
    private int nextIdCounter = 1;

    public ServerHandler() {
        this.processMap = new HashMap<>();
    }

    public ServerHandler(HashMap<String, ServerProcess> processMap) {
        this.processMap = processMap;
    }

    public ServerProcess addProcess(String name, ServerProcess serverProcess) {
        this.nextIdCounter++;
        return this.processMap.put(name, serverProcess);
    }

    public HashMap<String, ServerProcess> getProcessMap() {
        return processMap;
    }

    public int getNextId() {
        return this.nextIdCounter;
    }
}
