package com.mrqinzh.core.message;

import java.util.concurrent.ArrayBlockingQueue;

public class MessageQueue {

    private static final ArrayBlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(128);

    public static void produce(Message e) {
        messageQueue.add(e);
    }

    public static Message consume() throws InterruptedException {
        return messageQueue.poll();
    }

}
