package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.WebSocketMessage;
import com.mrqinzh.mq.config.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MQConfig.CONSUMER_GROUP, topic = WebSocketMessage.TOPIC)
public class WebSocketMessageListener implements RocketMQListener<WebSocketMessage> {

    @Autowired
    private WebSocketManager webSocketManager;

    @Override
    public void onMessage(WebSocketMessage message) {
        log.info("WebSocketMessageListener consumer message: {}", message);
        webSocketManager.sendToClient(message);
    }

}
