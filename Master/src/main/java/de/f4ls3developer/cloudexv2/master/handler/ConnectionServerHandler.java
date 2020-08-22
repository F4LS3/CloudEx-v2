package de.f4ls3developer.cloudexv2.master.handler;

import de.f4ls3developer.cloudexv2.cloudapi.netty.Packet;
import de.f4ls3developer.cloudexv2.cloudapi.netty.Session;
import de.f4ls3developer.cloudexv2.cloudapi.netty.SessionRegistry;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AcceptancePacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.AuthenticationPacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.KeepalivePacket;
import de.f4ls3developer.cloudexv2.cloudapi.netty.packets.RejectionPacket;
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
                session.getSessionCtx().channel().writeAndFlush(new AcceptancePacket("Authenticated successfully"));
                session.setAuthenticated(true);
                Logger.log("Authenticated session[id=" + session.getSessionId() + "] successfully");
                session.getSessionCtx().channel().writeAndFlush(new KeepalivePacket());
            }
        }

        if(session.isAuthenticated()) {
            if(packet instanceof KeepalivePacket) {
                wait(2000);
                System.out.println("Keepalive");
                session.getSessionCtx().channel().writeAndFlush(packet);
            }

            if(packet instanceof RejectionPacket) {
                Logger.warn("REJECTED: " + ((RejectionPacket) packet).getMessage());
            }

            if(packet instanceof AcceptancePacket) {
                Logger.log("Success: " + ((AcceptancePacket) packet).getMessage());
            }
            ctx.fireChannelRead(packet);
            return;
        }

        ctx.fireChannelRead(packet);

        Logger.warn("Unauthorized session tried to send packets. Closing channel...");
        session.getSessionCtx().channel().writeAndFlush(new RejectionPacket("authorization failed")).channel().closeFuture();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.log("Connected " + ctx.channel().remoteAddress());
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
        Logger.error("Session (id=" + session.getSessionId() + ") ran into an error:\n" + cause.toString());
        ctx.fireExceptionCaught(cause);
    }

    public static SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }
}