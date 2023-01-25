package com.mrqinzh.domain.websocket;

import com.mrqinzh.core.message.WebSocketMessage;

public interface WebSocketManager {

    void sendToClient(WebSocketMessage message);

}
