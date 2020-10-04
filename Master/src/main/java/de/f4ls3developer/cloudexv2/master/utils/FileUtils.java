package de.f4ls3developer.cloudexv2.master.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import de.f4ls3developer.cloudexv2.cloudapi.io.FileReader;
import de.f4ls3developer.cloudexv2.cloudapi.io.FileWriter;
import de.f4ls3developer.cloudexv2.cloudapi.utils.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FileUtils {

    public static final String BASE_DIR = "./";
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Document config;
    private static String key;

    public static void checkFiles() {
        try {
            new File(BASE_DIR + "modules").mkdirs();
            new File(BASE_DIR + "templates").mkdirs();
            new File(BASE_DIR + "temporary").mkdirs();
            new File(BASE_DIR + "library").mkdirs();

            File configFile = new File(BASE_DIR + "config.json");
            File keyFile = new File(BASE_DIR + "AUTH-KEY.key");
            config = new Document();

            if(!configFile.exists()) {
                configFile.createNewFile();
                config.put("port", 2000);
                config.put("port-range", "30000-40000");
                config.put("maintenance", false);
                config.put("authentication-type", "uuid");
                config.put("spigot-startup", "spigot.jar");
                config.put("bungee-startup", "bungee.jar");

                config.put("maintenance-motd", Arrays.asList(
                        "§8» §cCloudΞx §8| §cCloudSystem §8developed by §cf4ls3",
                        "§8» §cCurrently the Network is in Maintenance"
                ));

                config.put("motd", Arrays
                        .asList("§8» §cCloudΞx §8| §cCloudSystem §8developed by §cf4ls3",
                                "§8» §cYour free and easy Cloud-Service"));

                config.put("tablist", Arrays.asList(
                        Arrays.asList(
                                "§8» §cCloudEx §8| §cCloudSystem §8«",
                                "§cYour free and easy Cloud-Service"),
                        Arrays.asList(
                                "§8Your currently playing on §c%server%",
                                "§8We hope you enjoy the experience!"
                        )));

                FileWriter writer = new FileWriter(configFile);
                writer.writeAndFlush(gson.toJson(config.getStorage()));

            } else {
                JsonReader reader = new JsonReader(new java.io.FileReader(configFile));
                reader.setLenient(true);
                config.setStorage(gson.fromJson(reader, HashMap.class));
            }

            if(!keyFile.exists()) {
                keyFile.createNewFile();
                FileWriter writer = new FileWriter(keyFile);

                if(config.get("authentication-type").toString().equalsIgnoreCase("uuid")) {
                    key = KeyUtils.generateUUIDKey();

                } else if(config.get("authentication-type").toString().equalsIgnoreCase("hash")) {
                    key = KeyUtils.generateMD5Key();
                }

                if(key == null) key = KeyUtils.generateUUIDKey();

                writer.writeAndFlush(key);

            } else {
                FileReader reader = new FileReader(keyFile);
                key = reader.readFile();

                if(key == null) key = KeyUtils.generateUUIDKey();
            }

            File spigotFile = new File(BASE_DIR + "library/" + config.get("spigot-startup").toString());

            if(!spigotFile.exists()) {
                Logger.log("Downloading spigot file...");
                URLDownloader.download(spigotFile, "https://cdn.getbukkit.org/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar");
                Logger.log("Spigot file has been downloaded");
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
