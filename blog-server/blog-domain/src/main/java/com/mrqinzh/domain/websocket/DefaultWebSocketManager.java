package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultWebSocketManager implements WebSocketManager {

    @Autowired
    private WebSocketServer webSocketServer;

    public void sendToClient(WebSocketMessage message) {
        webSocketServer.sendToClient(message);
    }


}
