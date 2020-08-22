package de.f4ls3developer.cloudexv2.master.commands;

import de.f4ls3developer.cloudexv2.cloudapi.command.Command;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.Master;

import java.util.Arrays;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", new String[]{"?", "wtf"}, "Shows all commands and their description");
    }

    @Override
    public void execute(String[] args) {
        Master.getInstance().getCommandHandler().getCommandRegistry().getCommands().forEach((k, v) -> {
            if(!Arrays.asList(v.aliases).contains(k)) {
                Logger.log(k + " | " + v.description);
            }
        });
    }
}
