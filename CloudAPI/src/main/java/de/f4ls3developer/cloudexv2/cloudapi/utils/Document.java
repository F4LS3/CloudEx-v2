package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.util.HashMap;

public class Document {

    private HashMap<String, Object> storage;

    public Document() {
        this.storage = new HashMap<>();
    }

    public void put(String key, Object value) {
        this.storage.put(key, value);
    }

    public void putAll(HashMap<String, Object> storage) {
        this.storage.putAll(storage);
    }

    public Object get(String key) {
        return this.storage.get(key);
    }

    public HashMap<String, Object> getStorage() {
        return this.storage;
    }

    public void setStorage(HashMap<String, Object> storage) {
        this.storage = storage;
    }
}
