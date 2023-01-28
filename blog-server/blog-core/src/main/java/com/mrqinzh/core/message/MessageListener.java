package com.mrqinzh.core.message;

public interface MessageListener<T extends Message> {

    void consume(T message);

    void onMessage(T message);

}
