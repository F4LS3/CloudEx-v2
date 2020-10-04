package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static SimpleDateFormat sdf = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");

    public static void log(Object... out) {
        System.out.println(sdf.format(new Date()) + " [INFO] " + formatToString(out));
    }

    public static void warn(Object... out) {
        System.out.println(sdf.format(new Date()) + " [WARN] " + formatToString(out));
    }

    public static void error(Object... out) {
        System.out.println(sdf.format(new Date()) + " [ERROR] " + formatToString(out));
    }

    private static String formatToString(Object... obj) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < obj.length; i++) {
            if(i == (obj.length - 1)) {
                builder.append(obj[i]);
            } else {
                builder.append(obj[i] + " ");
            }
        }

        return builder.toString();
    }
}
