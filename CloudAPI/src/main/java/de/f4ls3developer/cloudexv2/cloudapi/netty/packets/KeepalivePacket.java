package de.f4ls3developer.cloudexv2.cloudapi.netty.packets;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class KeepalivePacket extends Packet {

    @Override
    public void read(ByteBufInputStream bufferStream) throws IOException {}

    @Override
    public void write(ByteBufOutputStream bufferStream) throws IOException {}
}
