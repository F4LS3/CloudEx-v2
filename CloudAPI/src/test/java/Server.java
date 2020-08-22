import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionServer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        ConnectionServer server = new ConnectionServer(2000);
        server.start();
    }
}
