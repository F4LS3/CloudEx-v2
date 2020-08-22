package de.f4ls3developer.cloudexv2.cloudapi.netty.packets;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class RejectionPacket extends Packet {

    private String message;

    public RejectionPacket() {}

    public RejectionPacket(String message) {
        this.message = message;
    }

    @Override
    public void read(ByteBufInputStream bufferStream) throws IOException {
        this.message = bufferStream.readUTF();
    }

    @Override
    public void write(ByteBufOutputStream bufferStream) throws IOException {
        bufferStream.writeUTF(this.message);
    }

    public String getMessage() {
        return message;
    }
}
