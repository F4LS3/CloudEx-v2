package de.f4ls3developer.cloudexv2.cloudapi.netty;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.UUID;

public class SessionRegistry {

    private HashMap<UUID, Session> sessions;

    public SessionRegistry() {
        this.sessions = new HashMap<>();
    }

    public void registerSession(Session session) {
        this.sessions.put(session.getSessionUUID(), session);
        Logger.log("Registered session[id=" + session.getSessionId() + ", uuid=" + session.getSessionUUID().toString() + "] successfully");
    }

    public void unregisterSession(Session session) {
        this.sessions.remove(session);
        Logger.log("Unregistered session[id=" + session.getSessionId() + ", uuid=" + session.getSessionUUID().toString() + "] successfully");
    }

    public Session getSessionByCtx(ChannelHandlerContext ctx) {
        for (Session value : this.sessions.values()) {
            if(value.getSessionCtx().equals(ctx)) return value;
        }
        return null;
    }

    public Session getSessionById(String sessionId) {
        for (Session value : this.sessions.values()) {
            if(value.getSessionId().equals(sessionId)) return value;
        }
        return null;
    }

    public Session getSessionByUUID(UUID uuid) {
        return this.sessions.get(uuid);
    }

    public UUID getUUIDBySession(Session session) {
        for (UUID uuid : this.sessions.keySet()) {
            if(this.sessions.get(uuid).equals(session)) return uuid;
        }
        return null;
    }
}
