package de.f4ls3developer.cloudexv2.cloudapi.netty;

import de.f4ls3developer.cloudexv2.cloudapi.netty.server.ConnectionServerInitializer;
import de.f4ls3developer.cloudexv2.cloudapi.utils.NetworkUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ConnectionServer extends Thread {

    private final int PORT;

    private PacketRegistry packetRegistry;
    private PipelineRegistry pipelineRegistry;

    public ConnectionServer(final int port) {
        this.setName("ConnectionServer");
        this.packetRegistry = new PacketRegistry();
        this.pipelineRegistry = new PipelineRegistry();
        this.PORT = port;
    }

    @Override
    public void run() {
        EventLoopGroup parent = NetworkUtils.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        EventLoopGroup child = NetworkUtils.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parent, child);
            b.channel(NetworkUtils.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
            b.childHandler(new ConnectionServerInitializer<SocketChannel>(this.packetRegistry, this.pipelineRegistry));


            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(this.PORT).sync();
            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }

    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public PipelineRegistry getPipelineRegistry() {
        return pipelineRegistry;
    }
}
