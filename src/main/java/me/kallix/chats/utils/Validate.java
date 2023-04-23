package me.kallix.chats.utils;

import java.util.regex.Pattern;

public final class Validate {

    private static final Pattern VALID_USER_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");

    public static boolean isValidUser(String content) {
        return VALID_USER_PATTERN.matcher(content).matches();
    }
}
