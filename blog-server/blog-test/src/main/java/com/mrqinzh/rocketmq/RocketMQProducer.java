package com.mrqinzh.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class RocketMQProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        Message message = new Message();
        message.setTopic("hello-topic");
        message.setTags("demo");
        message.setBody("hello mq".getBytes(StandardCharsets.UTF_8));

        SendResult result = producer.send(message);

        System.out.println(result);

//        producer.shutdown();
    }

}
