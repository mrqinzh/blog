package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.WebSocketMessage;
import org.springframework.stereotype.Component;

@Component
public class DefaultWebSocketManager implements WebSocketManager {

    public void sendToClient(WebSocketMessage message) {
        WebSocketServer.sendToClient(message);
    }


}
