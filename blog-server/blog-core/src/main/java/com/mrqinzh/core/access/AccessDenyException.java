package com.mrqinzh.core.access;

public class AccessDenyException extends RuntimeException {

    public AccessDenyException(String msg) {
        super(msg);
    }

}
