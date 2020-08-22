package de.f4ls3developer.cloudexv2.cloudapi.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class Document {

    private JsonObject storage;

    public Document() {
        this.storage = new JsonObject();
    }

    public void put(String key, Object value) {
        if (value == null) {
            this.storage.add(key, JsonNull.INSTANCE);
            return;
        }
        this.storage.add(key, new Gson().toJsonTree(value));
    }

    public JsonElement get(String key) {
        return this.storage.get(key);
    }

    public JsonObject getStorage() {
        return this.storage;
    }

    public void setStorage(JsonObject storage) {
        this.storage = storage;
    }
}
