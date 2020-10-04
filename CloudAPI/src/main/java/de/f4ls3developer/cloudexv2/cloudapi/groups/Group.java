package de.f4ls3developer.cloudexv2.cloudapi.groups;

import de.f4ls3developer.cloudexv2.cloudapi.utils.GroupType;
import de.f4ls3developer.cloudexv2.cloudapi.utils.TemplateType;

import java.io.File;
import java.util.UUID;

public abstract class Group {

    protected String groupName;
    protected int groupId;
    protected UUID groupUUID;
    protected int groupMaxPlayers;
    protected TemplateType groupTemplateType;
    protected GroupType groupType;

    public Group(String groupName, int groupId, UUID groupUUID, int groupMaxPlayers, TemplateType groupTemplateType, GroupType groupType) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupUUID = groupUUID;
        this.groupMaxPlayers = groupMaxPlayers;
        this.groupTemplateType = groupTemplateType;
        this.groupType = groupType;
    }

    public abstract void startInstance(int port, File dir, File libDir, String fileName);
    public abstract void stopInstance();

    public String getGroupName() {
        return groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public UUID getGroupUUID() {
        return groupUUID;
    }

    public int getGroupMaxPlayers() {
        return groupMaxPlayers;
    }

    public TemplateType getGroupTemplateType() {
        return groupTemplateType;
    }
}
