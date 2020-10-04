package de.f4ls3developer.cloudexv2.wrapper.handler;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AuthenticationPacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.StatePacket;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.wrapper.utils.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectionClientHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if(packet instanceof StatePacket) {
            StatePacket statePacket = (StatePacket) packet;

            if(statePacket.getStateId() == 200) {
                Logger.log("[STATUS: OK/200] " + statePacket.getMessage());

            } else if(statePacket.getStateId() == 401) {
                Logger.warn("[STATUS: UNAUTHORIZED/400" + statePacket.getMessage());

            } else if(statePacket.getStateId() == 500) {
                Logger.error("[STATUS: INTERNAL_ERROR/500" + statePacket.getMessage());

            } else if(statePacket.getStateId() == 201) {
                Logger.log("[STATUS: CREATED/201] " + statePacket.getMessage());

            } else {
                Logger.warn("[STATUS: UNKNOWN] " + statePacket.getMessage());
            }
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
