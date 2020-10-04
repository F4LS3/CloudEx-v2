package de.f4ls3developer.cloudexv2.master.commands;

import de.f4ls3developer.cloudexv2.cloudapi.command.Command;
import de.f4ls3developer.cloudexv2.cloudapi.utils.GroupType;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.cloudapi.utils.TemplateType;

public class CreateCommand extends Command {

    public CreateCommand() {
        super("create", new String[]{}, "Create a new group");
    }

    @Override
    public void execute(String[] args) {
        // create <groupType> <name> <maxPlayers> <templateType> <maxRam>
        if (args.length != 6) {
            Logger.error("Insufficient arguments supplied");
        } else {
            GroupType groupType;
            try {
                groupType = GroupType.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                Logger.error(args[1], "is not an available group type");
                return;
            }

            String name = args[2];
            int maxPlayers = Integer.valueOf(args[3]);

            TemplateType templateType;
            try {
                templateType = TemplateType.valueOf(args[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                Logger.error(args[4], "is not an available template type");
                return;
            }

            String maxRam = args[5];
            Logger.log(groupType.name(), name, maxPlayers, templateType.name(), maxRam);
        }
    }
}
