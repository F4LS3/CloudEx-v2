package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static SimpleDateFormat sdf = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");

    public static void log(Object out) {
        System.out.println(sdf.format(new Date()) + " [INFO] " + out);
    }

    public static void warn(Object out) {
        System.out.println(sdf.format(new Date()) + " [WARN] " + out);
    }

    public static void error(Object out) {
        System.out.println(sdf.format(new Date()) + " [ERROR] " + out);
    }
}
