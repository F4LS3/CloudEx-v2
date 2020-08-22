package de.f4ls3developer.cloudexv2.wrapper.handler;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AcceptancePacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AuthenticationPacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.KeepalivePacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.RejectionPacket;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.wrapper.utils.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectionClientHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if(packet instanceof KeepalivePacket) {
            ctx.channel().writeAndFlush(packet);
        }

        if(packet instanceof RejectionPacket) {
            Logger.warn("REJECTED: " + ((RejectionPacket) packet).getMessage());
        }

        if(packet instanceof AcceptancePacket) {
            Logger.log("Success: " + ((AcceptancePacket) packet).getMessage());
        }

        ctx.fireChannelRead(packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.log("Channel connected " + ctx.channel().remoteAddress().toString() + "?id=" + ctx.channel().id());
        Logger.log("Sending authentication...");
        ctx.channel().writeAndFlush(new AuthenticationPacket(FileUtils.getKey()));
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logger.log("Channel disconnected " + ctx.channel().remoteAddress().toString() + "?id=" + ctx.channel().id());
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.error("Wrapper ran into an error: \n" + cause.getMessage());
        ctx.fireExceptionCaught(cause);
    }
}
