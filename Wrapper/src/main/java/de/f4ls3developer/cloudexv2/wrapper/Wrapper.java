package de.f4ls3developer.cloudexv2.wrapper;

import de.f4ls3developer.cloudexv2.cloudapi.CloudAPI;
import de.f4ls3developer.cloudexv2.cloudapi.command.CommandHandler;
import de.f4ls3developer.cloudexv2.cloudapi.module.ModuleLoader;
import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionClient;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.*;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.wrapper.handler.ConnectionClientHandler;
import de.f4ls3developer.cloudexv2.wrapper.utils.FileUtils;

public class Wrapper {

    private static Wrapper instance = new Wrapper();

    public ConnectionClient client;
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
                "Wrapper\n");

        FileUtils.checkFiles();
        this.client = new ConnectionClient(FileUtils.getConfig().getStorage().get("master-port").getAsInt(), FileUtils.getConfig().getStorage().get("master-host").getAsString());
        this.commandHandler = new CommandHandler();
        this.moduleLoader = new ModuleLoader();
        CloudAPI.getInstance().setWrapperClient(this.client);

        /* REGISTER PACKETS */
        this.client.getPacketRegistry().registerPacket(new RejectionPacket());
        this.client.getPacketRegistry().registerPacket(new AcceptancePacket());
        this.client.getPacketRegistry().registerPacket(new AuthenticationPacket());
        this.client.getPacketRegistry().registerPacket(new CreateServerPacket());
        this.client.getPacketRegistry().registerPacket(new KeepalivePacket());

        /* ADD HANDLER TO PIPELINE */
        this.client.getPipelineRegistry().registerHandler(new ConnectionClientHandler());

        /* REGISTER COMMANDS */


        this.client.start();
        this.commandHandler.start();

        this.moduleLoader.loadModules();
        this.moduleLoader.enableModules();
    }

    public ConnectionClient getClient() {
        return this.client;
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    public static Wrapper getInstance() {
        return instance;
    }
}
