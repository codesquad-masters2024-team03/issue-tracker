package com.codesquad.team3.issuetracker.oauth.util;

import java.security.SecureRandom;
import java.util.Random;

public class IdGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String getString(int length) {
        StringBuilder idBuilder = new StringBuilder(length);
        for (int index = 0; index < length; index++) {
            idBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return idBuilder.toString();
    }
}
