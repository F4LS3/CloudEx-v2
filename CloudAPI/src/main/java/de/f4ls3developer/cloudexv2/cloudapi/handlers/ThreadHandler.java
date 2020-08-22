package de.f4ls3developer.cloudexv2.cloudapi.handlers;

import java.util.*;

public class ThreadHandler {

    private Map<UUID, Thread> threadList;

    public ThreadHandler() {
        this.threadList = new HashMap<>();
    }

    public void addThread(UUID uuid, Thread thread) {
        if(!this.threadList.containsKey(uuid)) {
            this.threadList.put(uuid, thread);
        }
    }

    public Thread getThreadByUUID(UUID uuid) {
        return this.threadList.get(uuid);
    }
}
