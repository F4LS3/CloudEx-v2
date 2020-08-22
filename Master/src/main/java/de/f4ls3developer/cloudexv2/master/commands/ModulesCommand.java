package de.f4ls3developer.cloudexv2.master.commands;

import de.f4ls3developer.cloudexv2.cloudapi.command.Command;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.Master;

public class ModulesCommand extends Command {

    public ModulesCommand() {
        super("modules", new String[]{"mods", "plugins", "pl"}, "Shows all loaded modules");
    }

    @Override
    public void execute(String[] args) {
        int loadedModules = Master.getInstance().getModuleLoader().getModuleRegistry().getModules().size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Master.getInstance().getModuleLoader().getModuleRegistry().getModules().size(); i++) {
            if(Master.getInstance().getModuleLoader().getModuleRegistry().getModules().size() == i + 1) {
                builder.append(Master.getInstance().getModuleLoader().getModuleRegistry().getModules().get(i).getClass().getSimpleName());
            } else {
                builder.append(Master.getInstance().getModuleLoader().getModuleRegistry().getModules().get(i).getClass().getSimpleName() + ", ");
            }
        }
        Logger.log("Modules (" + loadedModules + "): " + builder.toString().replace("[", "").replace("]", ""));
    }
}
