package de.f4ls3developer.cloudexv2.wrapper.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.f4ls3developer.cloudexv2.cloudapi.io.FileWriter;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Document;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileUtils {

    public static final String BASE_DIR = "./";
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Document config;
    private static String key;

    public static void checkFiles() {
        try {
            new File(BASE_DIR + "modules").mkdirs();

            File configFile = new File(BASE_DIR + "config.json");
            File keyFile = new File(BASE_DIR + "AUTH-KEY.key");

            config = new Document();

            if(!configFile.exists()) {
                configFile.createNewFile();
                config.put("master-host", "127.0.0.1");
                config.put("master-port", 2000);

                FileWriter writer = new FileWriter(configFile);
                writer.writeAndFlush(gson.toJson(config.getStorage()));

            } else {
                JsonReader reader = new JsonReader(new FileReader(configFile));
                reader.setLenient(true);
                config.setStorage(gson.fromJson(reader, HashMap.class));
            }

            if(!keyFile.exists()) {
                Logger.error("AUTH-KEY.key wasn't found, please copy it from the Master to the Wrapper (stopping in 3s)");
                Thread.sleep(3000);
                System.exit(-1);

            } else {
                de.f4ls3developer.cloudexv2.cloudapi.io.FileReader reader = new de.f4ls3developer.cloudexv2.cloudapi.io.FileReader(keyFile);
                key = reader.readFile();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getKey() {
        return key;
    }

    public static Document getConfig() {
        return config;
    }
}
