package de.f4ls3developer.cloudexv2.cloudapi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class KeyUtils {

    public static String generateUUIDKey() {
        return UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }

    public static String generateMD5Key() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < 4; i++) {
                md.update(UUID.randomUUID().toString().getBytes());
                byte[] b = md.digest();

                for (byte b1 : b) {
                    builder.append(Integer.toHexString(b1 & 0xff));
                }
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
