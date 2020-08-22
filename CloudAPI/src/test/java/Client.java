import de.f4ls3developer.cloudexv2.cloudapi.netty.ConnectionClient;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        ConnectionClient client = new ConnectionClient(2000, "localhost");
        client.start();
    }
}
