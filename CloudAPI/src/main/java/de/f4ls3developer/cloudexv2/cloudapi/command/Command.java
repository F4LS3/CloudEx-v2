package de.f4ls3developer.cloudexv2.cloudapi.command;

public abstract class Command {

    public String label;
    public String[] aliases;
    public String description;

    public Command(String label, String[] aliases, String description) {
        this.label = label;
        this.aliases = aliases;
        this.description = description;
    }

    public abstract void execute(String[] args);

}
