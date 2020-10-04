package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerProcess {

    private Process process;
    private Screen screen;
    private String name;
    private int port;
    private int id;

    public ServerProcess(Process process, Screen screen, String name, int port, int id) {
        this.process = process;
        this.screen = screen;
        this.name = name;
        this.port = port;
        this.id = id;

        this.screen.start();
    }

    public void emitCommand(String command) {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()))) {
            writer.write(command + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Process getProcess() {
        return process;
    }

    public Screen getScreen() {
        return screen;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }
}
