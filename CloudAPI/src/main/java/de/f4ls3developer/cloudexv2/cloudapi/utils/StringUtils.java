package de.f4ls3developer.cloudexv2.cloudapi.utils;

public class StringUtils {

    public static final String getProperties(int port, int maxPlayers, boolean onlineMode, String motd) {
        return  "generator-settings=\n" +
                "op-permission-level=0\n" +
                "resource-pack-hash=\n" +
                "allow-nether=true\n" +
                "level-name=world\n" +
                "allow-flight=false\n" +
                "announce-player-achievements=true\n" +
                "server-port=" + port + "\n" +
                "max-world-size=29999984\n" +
                "level-type=DEFAULT\n" +
                "force-gamemode=false\n" +
                "level-seed=\n" +
                "server-ip=\n" +
                "network-compression-threshold=256\n" +
                "max-build-height=256\n" +
                "spawn-npcs=true\n" +
                "white-list=false\n" +
                "spawn-animals=true\n" +
                "snooper-enabled=true\n" +
                "hardcore=false\n" +
                "online-mode=" + onlineMode + "\n" +
                "resource-pack=\n" +
                "pvp=true\n" +
                "difficulty=1\n" +
                "enable-command-block=false\n" +
                "player-idle-timeout=0\n" +
                "gamemode=0\n" +
                "max-players=" + maxPlayers + "\n" +
                "spawn-monsters=true\n" +
                "view-distance=10\n" +
                "generate-structures=true\n" +
                "motd=" + motd + "\n";
    }

    public static final String getEula() {
        return "eula=true";
    }
}
