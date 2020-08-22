package de.f4ls3developer.cloudexv2.cloudapi.command;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandHandler extends Thread {

    private CommandRegistry commandRegistry;

    public CommandHandler() {
        this.setName("CommandHandler");
        this.commandRegistry = new CommandRegistry();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                if((line = line.toLowerCase()).length() == 0) continue;
                String[] args = line.split(" ");
                if(this.commandRegistry.getCommands().containsKey(args[0])) {
                    this.commandRegistry.getCommands().get(args[0]).execute(Arrays.copyOfRange(args, 1, this.commandRegistry.getCommands().size()));

                } else {
                    Logger.warn("Command not found or not registered");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}
