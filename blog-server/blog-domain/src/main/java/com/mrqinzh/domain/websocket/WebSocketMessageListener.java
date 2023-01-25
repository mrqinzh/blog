package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.MessageListener;
import com.mrqinzh.core.message.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageListener implements MessageListener<WebSocketMessage> {

    @Autowired
    private WebSocketManager webSocketManager;

    @Override
    public void onMessage(WebSocketMessage message) {
        webSocketManager.sendToClient(message);
    }

}
