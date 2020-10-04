package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.io.File;
import java.io.IOException;

public class ProcessFactory {

    public static Process constructProcess(File dir, String... command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command)
                .directory(dir)
                .redirectErrorStream(true);

        Process process = processBuilder.start();
        return process;
    }
}
