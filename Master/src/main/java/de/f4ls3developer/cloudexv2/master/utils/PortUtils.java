package de.f4ls3developer.cloudexv2.master.utils;

import java.util.ArrayList;
import java.util.List;

public class PortUtils {

    private static int minPort;
    private static int maxPort;

    private static List<Integer> ports = new ArrayList<>();

    public static void setMinPort(int minPort) {
        PortUtils.minPort = minPort;
    }

    public static void setMaxPort(int maxPort) {
        PortUtils.maxPort = maxPort;
    }

    public static void initPorts() {
        PortUtils.ports.clear();
        for (int i = getMinPort(); i <= getMaxPort(); i++) {
            PortUtils.ports.add(i);
        }
    }

    public static int useNextFreePort() {
        int port = PortUtils.ports.get(0);
        PortUtils.ports.remove(0);

        return port;
    }

    public static void clearPort(int port) {
        PortUtils.ports.add(port);
    }

    public static boolean isPortUsed(int port) {
        return !PortUtils.ports.contains(port);
    }

    public static int getMinPort() {
        return minPort;
    }

    public static int getMaxPort() {
        return maxPort;
    }

    public static List<Integer> getPorts() {
        return ports;
    }
}
