package com.mrqinzh.core.message;

public interface MessageListener<T extends Message> {

    void onMessage(T message);

}
