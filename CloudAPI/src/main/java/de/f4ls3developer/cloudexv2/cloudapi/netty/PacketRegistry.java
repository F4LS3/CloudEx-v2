package de.f4ls3developer.cloudexv2.cloudapi.netty;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class PacketRegistry {

    private List<Class<? extends Packet>> packets;

    public PacketRegistry() {
        this.packets = new ArrayList<Class<? extends Packet>>();
    }

    public void registerPacket(Packet packet) {
        if(!this.packets.contains(packet.getClass())) {
            this.packets.add(packet.getClass());
            Logger.log("Registered packet \"" + packet.getClass().getSimpleName() + "\" successfully");
        }
    }

    public Class<? extends Packet> getPacketById(int id) {
        return this.packets.get(id);
    }

    public int getPacketId(Packet packet) {
        return this.packets.indexOf(packet.getClass());
    }

    public List<Class<? extends Packet>> getPackets() {
        return packets;
    }
}
