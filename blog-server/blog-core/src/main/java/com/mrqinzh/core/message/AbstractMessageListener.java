package com.mrqinzh.core.message;

public abstract class AbstractMessageListener<T extends Message> implements MessageListener<T> {

    public void consume(T message) {
        this.onMessage(message);
    }

}
