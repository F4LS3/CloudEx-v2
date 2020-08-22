package de.f4ls3developer.cloudexv2.cloudapi.netty;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class PipelineRegistry {

    private List<SimpleChannelInboundHandler> handler;

    public PipelineRegistry() {
        this.handler = new ArrayList<>();
    }

    public void registerHandler(SimpleChannelInboundHandler handler) {
        if(!this.handler.contains(handler)) {
            this.handler.add(handler);
            Logger.log("Added \"" + handler.getClass().getSimpleName() + "\" to pipeline successfully");
        }
    }

    public List<SimpleChannelInboundHandler> getHandler() {
        return handler;
    }
}
