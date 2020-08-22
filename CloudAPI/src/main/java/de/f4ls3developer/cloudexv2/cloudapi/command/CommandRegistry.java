package de.f4ls3developer.cloudexv2.cloudapi.command;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.util.HashMap;

public class CommandRegistry {

    private HashMap<String, Command> commands;

    public CommandRegistry() {
        this.commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        if(!this.commands.containsKey(command.label)) {
            this.commands.put(command.label, command);
            for (String alias : command.aliases) {
                this.commands.put(alias, command);
            }

            Logger.log("Registered command \"" + command.label + "\" (aliases: " + command.aliases.length + ") successfully");
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
