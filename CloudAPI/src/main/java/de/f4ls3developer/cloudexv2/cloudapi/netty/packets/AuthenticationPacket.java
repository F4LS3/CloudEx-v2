package de.f4ls3developer.cloudexv2.cloudapi.netty.packets;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class AuthenticationPacket extends Packet {

    private String key;

    public AuthenticationPacket() {}

    public AuthenticationPacket(String key) {
        this.key = key;
    }

    @Override
    public void read(ByteBufInputStream bufferStream) throws IOException {
        this.key = bufferStream.readUTF();
    }

    @Override
    public void write(ByteBufOutputStream bufferStream) throws IOException {
        bufferStream.writeUTF(this.key);
    }

    public String getKey() {
        return key;
    }
}
