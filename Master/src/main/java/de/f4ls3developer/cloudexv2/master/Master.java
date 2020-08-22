package de.f4ls3developer.cloudexv2.master;

import de.f4ls3developer.cloudexv2.cloudapi.CloudAPI;
import de.f4ls3developer.cloudexv2.cloudapi.command.CommandHandler;
import de.f4ls3developer.cloudexv2.cloudapi.module.ModuleLoader;
import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionServer;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.*;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.commands.HelpCommand;
import de.f4ls3developer.cloudexv2.master.commands.ModulesCommand;
import de.f4ls3developer.cloudexv2.master.handler.ConnectionServerHandler;
import de.f4ls3developer.cloudexv2.master.utils.FileUtils;

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
        this.server = new ConnectionServer(FileUtils.getConfig().getStorage().get("port").getAsInt());
        this.commandHandler = new CommandHandler();
        this.moduleLoader = new ModuleLoader();
        CloudAPI.getInstance().setMasterServer(this.server);

        /* REGISTER PACKETS */
        this.server.getPacketRegistry().registerPacket(new RejectionPacket());
        this.server.getPacketRegistry().registerPacket(new AcceptancePacket());
        this.server.getPacketRegistry().registerPacket(new AuthenticationPacket());
        this.server.getPacketRegistry().registerPacket(new CreateServerPacket());
        this.server.getPacketRegistry().registerPacket(new KeepalivePacket());

        /* ADD HANDLER TO PIPELINE */
        this.server.getPipelineRegistry().registerHandler(new ConnectionServerHandler());

        /* REGISTER COMMANDS */
        this.commandHandler.getCommandRegistry().registerCommand(new HelpCommand());
        this.commandHandler.getCommandRegistry().registerCommand(new ModulesCommand());

        this.server.start();
        this.commandHandler.start();

        this.moduleLoader.loadModules();
        this.moduleLoader.enableModules();
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
