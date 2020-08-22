package de.f4ls3developer.cloudexv2.cloudapi.netty.client;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.PacketRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public class ClientMessageToByteEncoder extends MessageToByteEncoder<Packet> {

    private PacketRegistry registry;

    public ClientMessageToByteEncoder(PacketRegistry packetRegistry) {
        this.registry = packetRegistry;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf buffer) throws Exception {
        if (buffer instanceof EmptyByteBuf) return;
        int id = this.registry.getPacketId(packet);
        if(id < 0) throw new IOException("Cannot encode packet: Invalid packet id");

        ByteBufOutputStream bufferStream = new ByteBufOutputStream(buffer);
        bufferStream.writeInt(id);
        packet.write(bufferStream);
    }
}
