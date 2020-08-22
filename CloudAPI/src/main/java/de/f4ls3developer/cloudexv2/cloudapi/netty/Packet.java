package de.f4ls3developer.cloudexv2.cloudapi.netty;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public abstract class Packet {

    public abstract void read(ByteBufInputStream bufferStream) throws IOException;
    public abstract void write(ByteBufOutputStream bufferStream) throws IOException;

}
