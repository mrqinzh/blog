package com.mrqinzh.mq.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalMessageProducer {

    public static final Logger logger = LoggerFactory.getLogger(GlobalMessageProducer.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic, Object payload) {
        logger.info("GlobalMessageProducer produce message: {} ", payload);
        rocketMQTemplate.convertAndSend(topic, payload);
    }

    public void sendAsync(String topic, Object payload) {
        rocketMQTemplate.asyncSend(topic, payload, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("async message send success, msgId: {}", sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        });
    }

    public void sendAsync(String topic, Object payload, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, payload, sendCallback);
    }

}
