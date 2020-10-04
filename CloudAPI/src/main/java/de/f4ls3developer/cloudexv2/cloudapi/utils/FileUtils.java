package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void writeEulaFile(File dir) {
        File eula = new File(dir.getPath() + "/eula.txt");

        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(eula))) {
            writer.write(StringUtils.getEula());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePropertiesFile(File dir, int port, int maxPlayers, boolean onlineMode, String motd) {
        File properties = new File(dir.getPath() + "/server.properties");

        try(BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(properties))) {
            writer.write(StringUtils.getProperties(port, maxPlayers, onlineMode, motd));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
