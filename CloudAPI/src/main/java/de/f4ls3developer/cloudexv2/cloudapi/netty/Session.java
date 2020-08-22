package de.f4ls3developer.cloudexv2.cloudapi.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class Session {

    private String sessionId;
    private UUID sessionUUID;
    private ChannelHandlerContext sessionCtx;
    private boolean isAuthenticated;

    public Session(String sessionId, UUID sessionUUID, ChannelHandlerContext sessionCtx, boolean isAuthenticated) {
        this.sessionId = sessionId;
        this.sessionUUID = sessionUUID;
        this.sessionCtx = sessionCtx;
        this.isAuthenticated = isAuthenticated;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UUID getSessionUUID() {
        return sessionUUID;
    }

    public ChannelHandlerContext getSessionCtx() {
        return sessionCtx;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
