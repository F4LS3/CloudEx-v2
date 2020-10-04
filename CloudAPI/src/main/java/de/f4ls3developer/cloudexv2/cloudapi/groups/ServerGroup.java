package de.f4ls3developer.cloudexv2.cloudapi.groups;

import de.f4ls3developer.cloudexv2.cloudapi.handlers.ServerHandler;
import de.f4ls3developer.cloudexv2.cloudapi.handlers.TemplateHandler;
import de.f4ls3developer.cloudexv2.cloudapi.utils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerGroup extends Group {

    public ServerHandler serverHandler;
    public TemplateHandler templateHandler;
    private String maxRam;

    public ServerGroup(String groupName, int groupId, UUID groupUUID, int groupMaxPlayers, TemplateType groupTemplateType, GroupType groupType, String maxRam) {
        super(groupName, groupId, groupUUID, groupMaxPlayers, groupTemplateType, groupType);

        this.serverHandler = new ServerHandler();
        this.templateHandler = new TemplateHandler(new File("./templates/"));
        this.maxRam = maxRam;
    }

    @Override
    public void startInstance(int port, File dir, File libDir, String fileName) {
        try {
            this.templateHandler.deleteTemporaryAndCopyTemplate(this, dir, dir);

            dir.mkdirs();

            Files.copy(new File(libDir.getPath() + "/" + fileName).toPath(),
                    new File(dir.getPath() + "/" + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

            FileUtils.writeEulaFile(dir);
            FileUtils.writePropertiesFile(dir, port, this.groupMaxPlayers, true, this.groupName);

            /*if(this.templateHandler.templateExists(this)) {
                this.templateHandler.copyTemplate(this, dir);
            }*/

            int id = getNextInstanceId();

            Process p = ProcessFactory
                    .constructProcess(dir,
                            "java", "-jar", fileName);

            Screen serverScreen = new Screen(p);
            ServerProcess serverProcess = new ServerProcess(p, serverScreen, this.groupName + "-" + id, port, id);

            this.serverHandler.addProcess(this.groupName + "-" + id, serverProcess);

            Logger.log("Server Instance " + serverProcess.getName() + " has been started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopInstance() {
        String instanceName = this.serverHandler.getProcessMap().keySet().stream().findFirst().get();

        this.serverHandler.getProcessMap().get(instanceName).emitCommand("stop");
        this.serverHandler.getProcessMap().get(instanceName).getProcess().destroy();
        this.serverHandler.getProcessMap().remove(instanceName);

        this.templateHandler.deleteTemporary(new File("./temporary/" + this.getGroupName() + "/" + instanceName));

        Logger.warn("Server Instance " + instanceName + " has been stopped");
    }

    public int getNextInstanceId() {
        return this.serverHandler.getNextId();
    }
}
