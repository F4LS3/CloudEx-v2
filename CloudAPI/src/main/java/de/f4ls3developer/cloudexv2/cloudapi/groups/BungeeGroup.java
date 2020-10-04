package de.f4ls3developer.cloudexv2.cloudapi.groups;

import de.f4ls3developer.cloudexv2.cloudapi.handlers.BungeeHandler;
import de.f4ls3developer.cloudexv2.cloudapi.utils.GroupType;
import de.f4ls3developer.cloudexv2.cloudapi.utils.TemplateType;

import java.io.File;
import java.util.UUID;

public class BungeeGroup extends Group {

    public BungeeHandler bungeeHandler;

    public BungeeGroup(String groupName, int groupId, UUID groupUUID, int groupMaxPlayers, TemplateType groupTemplateType, GroupType groupType) {
        super(groupName, groupId, groupUUID, groupMaxPlayers, groupTemplateType, groupType);

        this.bungeeHandler = new BungeeHandler();
    }

    @Override
    public void startInstance(int port, File dir, File libDir, String fileName) {

    }

    @Override
    public void stopInstance() {

    }
}
