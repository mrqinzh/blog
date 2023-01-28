package com.mrqinzh.common.model.bean;

import lombok.Data;

@Data
public class WebSocketBean {

    private boolean isJsonMsg; // 是否json消息
    private String msgContent; // 消息内容

    public WebSocketBean() {
    }

    public WebSocketBean(boolean isJsonMsg, String msgContent) {
        this.isJsonMsg = isJsonMsg;
        this.msgContent = msgContent;
    }

    @Data
    public static class MsgBody {
        private WebSocketMessageType messageType;
    }

}