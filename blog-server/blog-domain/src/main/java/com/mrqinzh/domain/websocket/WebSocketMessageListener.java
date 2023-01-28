package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.AbstractMessageListener;
import com.mrqinzh.core.message.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageListener extends AbstractMessageListener<WebSocketMessage> {

    @Autowired
    private WebSocketManager webSocketManager;

    @Override
    public void onMessage(WebSocketMessage message) {
        webSocketManager.sendToClient(message);
    }

}
