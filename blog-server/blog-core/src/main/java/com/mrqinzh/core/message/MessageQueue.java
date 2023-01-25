package com.mrqinzh.core.message;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageQueue {

    private static final Queue<Message> messageQueue = new ArrayBlockingQueue<>(128);

    public static void produce(Message e) {
        messageQueue.add(e);
    }

    public static Message consume() {
        return messageQueue.poll();
    }

}
