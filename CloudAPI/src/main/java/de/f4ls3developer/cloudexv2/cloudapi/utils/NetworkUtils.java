package de.f4ls3developer.cloudexv2.cloudapi.utils;

import io.netty.channel.epoll.Epoll;

public class NetworkUtils {

    public static final boolean EPOLL = Epoll.isAvailable();

}
