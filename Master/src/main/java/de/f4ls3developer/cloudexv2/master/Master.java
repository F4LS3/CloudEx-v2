package de.f4ls3developer.cloudexv2.master;

import de.f4ls3developer.cloudexv2.cloudapi.CloudAPI;
import de.f4ls3developer.cloudexv2.cloudapi.command.CommandHandler;
import de.f4ls3developer.cloudexv2.cloudapi.module.ModuleLoader;
import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionServer;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AuthenticationPacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.CreateGroupPacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.StatePacket;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.commands.CreateCommand;
import de.f4ls3developer.cloudexv2.master.commands.HelpCommand;
import de.f4ls3developer.cloudexv2.master.commands.ModulesCommand;
import de.f4ls3developer.cloudexv2.master.commands.StopCommand;
import de.f4ls3developer.cloudexv2.master.handler.ConnectionServerHandler;
import de.f4ls3developer.cloudexv2.master.utils.FileUtils;
import de.f4ls3developer.cloudexv2.master.utils.PortUtils;

public class Master {

    private static Master instance = new Master();

    public ConnectionServer server;
    public CommandHandler commandHandler;
    public ModuleLoader moduleLoader;

    public void launch() {
        Logger.log("\n" +
                "   _____ _                 _ ______             ___  \n" +
                "  / ____| |               | |  ____|           |__ \\ \n" +
                " | |    | | ___  _   _  __| | |__  __  __ __   __ ) |\n" +
                " | |    | |/ _ \\| | | |/ _` |  __| \\ \\/ / \\ \\ / // / \n" +
                " | |____| | (_) | |_| | (_| | |____ >  <   \\ V // /_ \n" +
                "  \\_____|_|\\___/ \\__,_|\\__,_|______/_/\\_\\   \\_/|____|\n" +
                "Master\n");

        FileUtils.checkFiles();

        this.server = new ConnectionServer(Integer.valueOf(FileUtils.getConfig().getStorage().get("port").toString().replace(".0", "")));
        CloudAPI.getInstance().setMasterServer(this.server);

        this.commandHandler = new CommandHandler();
        this.moduleLoader = new ModuleLoader();

        /* REGISTER PACKETS */
        this.server.getPacketRegistry().registerPacket(new AuthenticationPacket());
        this.server.getPacketRegistry().registerPacket(new CreateGroupPacket());
        this.server.getPacketRegistry().registerPacket(new StatePacket());

        /* ADD HANDLER TO PIPELINE */
        this.server.getPipelineRegistry().registerHandler(new ConnectionServerHandler());

        /* REGISTER COMMANDS */
        this.commandHandler.getCommandRegistry().registerCommand(new HelpCommand());
        this.commandHandler.getCommandRegistry().registerCommand(new ModulesCommand());
        this.commandHandler.getCommandRegistry().registerCommand(new StopCommand());
        this.commandHandler.getCommandRegistry().registerCommand(new CreateCommand());

        this.server.start();
        this.commandHandler.start();

        this.moduleLoader.loadModules();
        this.moduleLoader.enableModules();

        String[] portRange = FileUtils.getConfig().get("port-range").toString().split("-");
        PortUtils.setMinPort(Integer.valueOf(portRange[0]));
        PortUtils.setMaxPort(Integer.valueOf(portRange[1]));
        PortUtils.initPorts();
    }

    public ConnectionServer getServer() {
        return this.server;
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    public static Master getInstance() {
        return instance;
    }
}
