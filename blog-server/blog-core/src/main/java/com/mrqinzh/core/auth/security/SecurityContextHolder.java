package com.mrqinzh.core.auth.security;

public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal();

    public static SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = new SecurityContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    public static void clearContext() {
        contextHolder.remove();
    }

}
