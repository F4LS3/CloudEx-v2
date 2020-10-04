package de.f4ls3developer.cloudexv2.cloudapi.netty.packets;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class StatePacket extends Packet {

    private int stateId;
    private String message;

    public StatePacket(int stateId, String message) {
        this.stateId = stateId;
        this.message = message;
    }

    public StatePacket() {}

    @Override
    public void read(ByteBufInputStream bufferStream) throws IOException {
        this.stateId = bufferStream.readInt();
        this.message = bufferStream.readUTF();
    }

    @Override
    public void write(ByteBufOutputStream bufferStream) throws IOException {
        bufferStream.writeInt(this.stateId);
        bufferStream.writeUTF(this.message);
    }

    public int getStateId() {
        return stateId;
    }

    public String getMessage() {
        return message;
    }
}
