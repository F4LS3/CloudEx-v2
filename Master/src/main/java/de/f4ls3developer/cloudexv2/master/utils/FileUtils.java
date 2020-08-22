package de.f4ls3developer.cloudexv2.master.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import de.f4ls3developer.cloudexv2.cloudapi.io.FileReader;
import de.f4ls3developer.cloudexv2.cloudapi.io.FileWriter;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Document;
import de.f4ls3developer.cloudexv2.cloudapi.utils.KeyUtils;

import java.io.File;
import java.io.IOException;

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
                config.put("port", 2000);
                config.put("maintenance", false);
                config.put("authentication-type", "uuid");

                JsonObject maintenanceMOTD = new JsonObject();
                maintenanceMOTD.addProperty("first-line", "§8» §cCloudΞx §8| §cCloudSystem §8developed by §cf4ls3");
                maintenanceMOTD.addProperty("second-line", "§8» §cCurrently the Network is in Maintenance");

                JsonObject motd = new JsonObject();
                motd.addProperty("first-line", "§8» §cCloudΞx §8| §cCloudSystem §8developed by §cf4ls3");
                motd.addProperty("second-line", "§8» §cYour free and easy Cloud-Service");

                JsonObject tablistLayout = new JsonObject();
                tablistLayout.addProperty("top-first-line", "§8» §cCloudEx §8| §cCloudSystem §8«");
                tablistLayout.addProperty("top-second-line", "§cYour free and easy Cloud-Service");
                tablistLayout.addProperty("bottom-first-line", "§8Your currently playing on §c%server%");
                tablistLayout.addProperty("bottom-second-line", "§8We hope you enjoy using and playing!");

                config.put("maintenance-motd", maintenanceMOTD);
                config.put("motd", motd);
                config.put("tablist-layout", tablistLayout);

                FileWriter writer = new FileWriter(configFile);
                writer.writeAndFlush(gson.toJson(config.getStorage()));

            } else {
                JsonReader reader = new JsonReader(new java.io.FileReader(configFile));
                reader.setLenient(true);
                config.setStorage(gson.fromJson(reader, JsonObject.class));
            }

            if(!keyFile.exists()) {
                keyFile.createNewFile();
                FileWriter writer = new FileWriter(keyFile);

                if(config.get("authentication-type").getAsString().equalsIgnoreCase("uuid")) {
                    key = KeyUtils.generateUUIDKey();

                } else if(config.get("authentication-type").getAsString().equalsIgnoreCase("hash")) {
                    key = KeyUtils.generateMD5Key();
                }

                if(key == null) key = KeyUtils.generateUUIDKey();

                writer.writeAndFlush(key);

            } else {
                FileReader reader = new FileReader(keyFile);
                key = reader.readFile();

                if(key == null) key = KeyUtils.generateUUIDKey();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isKeyFileExistent() {
        return new File(BASE_DIR + "AUTH-KEY.key").exists();
    }

    public static String getCurrentKey() {
        if(key != null)
            return key;

        return "";
    }

    public static Document getConfig() {
        return config;
    }
}
