package de.f4ls3developer.cloudexv2.cloudapi.io;

import java.io.*;

public class FileReader {

    private File file;

    public FileReader(File file) {
        this.file = file;
    }

    public String readFile() {
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(this.file));
            StringBuilder builder = new StringBuilder();
            while(reader.readLine() != null) { builder.append(reader.readLine()); }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
