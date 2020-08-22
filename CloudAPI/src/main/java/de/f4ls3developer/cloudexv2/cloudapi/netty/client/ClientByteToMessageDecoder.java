package de.f4ls3developer.cloudexv2.cloudapi.netty.client;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.PacketRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class ClientByteToMessageDecoder extends ByteToMessageDecoder {

    private PacketRegistry registry;

    public ClientByteToMessageDecoder(PacketRegistry packetRegistry) {
        this.registry = packetRegistry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
        if (buffer instanceof EmptyByteBuf) return;
        int id = buffer.readInt();
        if(id == -1) throw new IOException("Cannot decode packet: Invalid packet id");

        Packet packet = this.registry.getPacketById(id).newInstance();
        ByteBufInputStream bufferStream = new ByteBufInputStream(buffer);
        packet.read(bufferStream);
        list.add(packet);
    }
}
