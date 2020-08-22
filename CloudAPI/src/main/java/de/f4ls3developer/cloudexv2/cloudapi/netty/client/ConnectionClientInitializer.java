package de.f4ls3developer.cloudexv2.cloudapi.netty.client;

import de.f4ls3developer.cloudexv2.cloudapi.netty.PacketRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.netty.PipelineRegistry;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ConnectionClientInitializer<T extends Channel> extends ChannelInitializer<T> {

    private PacketRegistry packetRegistry;
    private PipelineRegistry pipelineRegistry;

    public ConnectionClientInitializer(PacketRegistry packetRegistry, PipelineRegistry pipelineRegistry) {
        this.packetRegistry = packetRegistry;
        this.pipelineRegistry = pipelineRegistry;
    }
    @Override
    protected void initChannel(T t) throws Exception {
        t.pipeline().addLast(new ClientByteToMessageDecoder(this.packetRegistry));
        t.pipeline().addLast(new ClientMessageToByteEncoder(this.packetRegistry));
        this.pipelineRegistry.getHandler().forEach(h -> t.pipeline().addLast(h));
        t.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
    }
}
