package de.f4ls3developer.cloudexv2.cloudapi.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {

    private File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public void writeAndFlush(String s) {
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(this.file));
            writer.write(s);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
