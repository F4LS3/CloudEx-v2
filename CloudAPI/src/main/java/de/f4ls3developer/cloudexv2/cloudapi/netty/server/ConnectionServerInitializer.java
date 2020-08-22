package de.f4ls3developer.cloudexv2.cloudapi.netty.server;

import de.f4ls3developer.cloudexv2.cloudapi.netty.PacketRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.netty.PipelineRegistry;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ConnectionServerInitializer<T extends Channel> extends ChannelInitializer<T> {

    private PacketRegistry packetRegistry;
    private PipelineRegistry pipelineRegistry;

    public ConnectionServerInitializer(PacketRegistry packetRegistry, PipelineRegistry pipelineRegistry) {
        this.packetRegistry = packetRegistry;
        this.pipelineRegistry = pipelineRegistry;
    }

    @Override
    protected void initChannel(T t) throws Exception {
        t.pipeline().addLast(new ServerByteToMessageDecoder(this.packetRegistry));
        t.pipeline().addLast(new ServerMessageToByteEncoder(this.packetRegistry));
        this.pipelineRegistry.getHandler().forEach(h -> t.pipeline().addLast(h));
    }
}
