package de.f4ls3developer.cloudexv2.master.commands;

import de.f4ls3developer.cloudexv2.cloudapi.command.Command;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.Master;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop", new String[]{ "exit", "kill", "die" }, "Stops the Cloud");
    }

    @Override
    public void execute(String[] args) {
        Master.getInstance().getServer().stop();

        Logger.log("\n" +
                "  ____             _ \n" +
                " |  _ \\           | |\n" +
                " | |_) |_   _  ___| |\n" +
                " |  _ <| | | |/ _ \\ |\n" +
                " | |_) | |_| |  __/_|\n" +
                " |____/ \\__, |\\___(_)\n" +
                "         __/ |       \n" +
                "        |___/        \n");

        System.exit(0);
    }
}
