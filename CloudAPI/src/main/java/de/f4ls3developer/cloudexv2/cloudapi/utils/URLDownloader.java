package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class URLDownloader {

    public static void download(File outputFile, String fileURL) {
        try {
            InputStream inputStream = new URL(fileURL).openStream();
            Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
