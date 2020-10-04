package de.f4ls3developer.cloudexv2.cloudapi.netty.packets;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.utils.GroupType;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;
import java.util.UUID;

public class CreateGroupPacket extends Packet {

    private String groupName;
    private int groupId;
    private UUID groupUUID;
    private int maxMemory;
    private int minServersOnline;
    private int threshold;
    private GroupType groupType;

    public CreateGroupPacket(String groupName, int groupId, UUID groupUUID, int maxMemory, int minServersOnline, int threshold, GroupType groupType) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupUUID = groupUUID;
        this.maxMemory = maxMemory;
        this.minServersOnline = minServersOnline;
        this.threshold = threshold;
        this.groupType = groupType;
    }

    public CreateGroupPacket() {}

    @Override
    public void read(ByteBufInputStream bufferStream) throws IOException {
        this.groupName = bufferStream.readUTF();
        this.groupId = bufferStream.readInt();
        this.groupUUID = new UUID(bufferStream.readLong(), bufferStream.readLong());
        this.maxMemory = bufferStream.readInt();
        this.minServersOnline = bufferStream.readInt();
        this.threshold = bufferStream.readInt();
        this.groupType = GroupType.valueOf(bufferStream.readUTF());
    }

    @Override
    public void write(ByteBufOutputStream bufferStream) throws IOException {
        bufferStream.writeUTF(this.groupName);
        bufferStream.writeInt(this.groupId);
        bufferStream.writeLong(this.groupUUID.getMostSignificantBits());
        bufferStream.writeLong(this.groupUUID.getLeastSignificantBits());
        bufferStream.writeInt(this.maxMemory);
        bufferStream.writeInt(this.minServersOnline);
        bufferStream.writeInt(this.threshold);
        bufferStream.writeUTF(this.groupType.name());
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public UUID getGroupUUID() {
        return groupUUID;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public int getMinServersOnline() {
        return minServersOnline;
    }

    public int getThreshold() {
        return threshold;
    }
}
