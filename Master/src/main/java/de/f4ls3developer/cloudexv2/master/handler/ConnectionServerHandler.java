package de.f4ls3developer.cloudexv2.master.handler;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.Session;
import de.f4ls3developer.cloudexv2.cloudapi.netty.SessionRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.*;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import de.f4ls3developer.cloudexv2.master.utils.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class ConnectionServerHandler extends SimpleChannelInboundHandler<Packet> {

    private static SessionRegistry sessionRegistry;

    public ConnectionServerHandler() {
        this.sessionRegistry = new SessionRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Session session = this.sessionRegistry.getSessionById(ctx.channel().id().toString());

        if(packet instanceof AuthenticationPacket && !session.isAuthenticated()) {
            if(((AuthenticationPacket) packet).getKey().equals(FileUtils.getCurrentKey())) {
                session.getSessionCtx().channel().writeAndFlush(new StatePacket(200, "Authenticated successfully"));
                session.setAuthenticated(true);
                Logger.log("Authenticated session[id=" + session.getSessionId() + "] successfully");
            }
        }

        if(session.isAuthenticated()) {
            if(packet instanceof StatePacket) {
                StatePacket statePacket = (StatePacket) packet;

                if(statePacket.getStateId() == 200) {
                    Logger.log("[STATUS: OK/200] " + statePacket.getMessage());

                } else if(statePacket.getStateId() == 401) {
                    Logger.warn("[STATUS: UNAUTHORIZED/401" + statePacket.getMessage());

                } else if(statePacket.getStateId() == 500) {
                    Logger.error("[STATUS: INTERNAL_ERROR/500" + statePacket.getMessage());

                } else if(statePacket.getStateId() == 201) {
                    Logger.log("[STATUS: CREATED/201] " + statePacket.getMessage());

                } else {
                    Logger.warn("[STATUS: UNKNOWN] " + statePacket.getMessage());
                }
            }

        } else {
            Logger.warn("Unauthorized session tried to send packets. Closing channel...");
            session.getSessionCtx().channel().writeAndFlush(new StatePacket(401, "authorization failed due to incorrect key")).channel().closeFuture();
            this.sessionRegistry.unregisterSession(session);
        }

        ctx.fireChannelRead(packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.log("Channel connected " + ctx.channel().remoteAddress());
        Session session = new Session(ctx.channel().id().toString(), UUID.randomUUID(), ctx, false);
        this.sessionRegistry.registerSession(session);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = this.sessionRegistry.getSessionById(ctx.channel().id().toString());
        this.sessionRegistry.unregisterSession(session);
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Session session = this.sessionRegistry.getSessionById(ctx.channel().id().toString());
        if(cause.getMessage().contains("Remotehost")) return;
        Logger.error("Session (id=" + session.getSessionId() + ") ran into an error: " + cause.toString());
        ctx.fireExceptionCaught(cause);
    }

    public static SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }
}