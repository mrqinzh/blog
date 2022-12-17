package com.mrqinzh.core.cache;

import com.mrqinzh.core.auth.token.Token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionCache {

    private static final Map<String, Token> sessions = new ConcurrentHashMap<>(64);

    public static void set(String key, Token value) {
        sessions.put(key, value);
    }

    public static Token get(String key) {
        return sessions.get(key);
    }

}
