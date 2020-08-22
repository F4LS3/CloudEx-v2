package de.f4ls3developer.cloudexv2.wrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {

    public static void main(String[] args) {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        Wrapper.getInstance().launch();
    }
}
