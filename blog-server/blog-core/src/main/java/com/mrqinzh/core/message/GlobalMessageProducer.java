package com.mrqinzh.core.message;

import org.springframework.stereotype.Component;

@Component
public class GlobalMessageProducer {

    public void produce(Message message) {
        synchronized (this) {
            MessageQueue.produce(message);
        }
    }

}
