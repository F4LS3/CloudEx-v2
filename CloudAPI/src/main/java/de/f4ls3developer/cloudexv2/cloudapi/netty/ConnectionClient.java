package de.f4ls3developer.cloudexv2.cloudapi.netty;

import de.f4ls3developer.cloudexv2.cloudapi.netty.client.ConnectionClientInitializer;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.cloudapi.utils.NetworkUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ConnectionClient extends Thread {

    private Bootstrap bootstrap;
    private Channel channel;

    private final int PORT;
    private final String HOST;

    private PacketRegistry packetRegistry;
    private PipelineRegistry pipelineRegistry;

    public ConnectionClient(final int port, final String host) {
        this.setName("ConnectionClient");
        this.packetRegistry = new PacketRegistry();
        this.pipelineRegistry = new PipelineRegistry();
        this.PORT = port;
        this.HOST = host;
    }

    @Override
    public void run() {
        EventLoopGroup group = NetworkUtils.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        try {
            this.bootstrap = new Bootstrap();

            this.bootstrap.group(group);
            this.bootstrap.channel(NetworkUtils.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class);
            this.bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            this.bootstrap.handler(new ConnectionClientInitializer<SocketChannel>(this.packetRegistry, this.pipelineRegistry));

            this.channel = this.bootstrap.connect(this.HOST, this.PORT).sync().channel().closeFuture().syncUninterruptibly().channel();
        } catch (Exception e) {
            Logger.error("Couldn't connect to Master");
            Logger.error("ERROR: " + e.getMessage());
            if(this.channel != null) {
                this.channel.close();
                this.channel = null;
            }

        } finally {
            group.shutdownGracefully();
        }
    }

    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public PipelineRegistry getPipelineRegistry() {
        return pipelineRegistry;
    }
}
