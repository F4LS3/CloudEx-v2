package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Screen extends Thread {

    private List<String> screen;
    private Process process;

    private boolean attached;

    public Screen(Process process) {
        this.screen = new ArrayList<>();
        this.process = process;

        this.attached = false;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = this.process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                this.screen.add(line);

                if(this.attached) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void attachScreen() {
        this.screen.forEach(line -> {
            Logger.log(line);
        });

        this.attached = true;
    }

    public void detachScreen() {
        this.attached = false;
    }

    public Process getProcess() {
        return process;
    }

    public List<String> getScreen() {
        return screen;
    }
}
