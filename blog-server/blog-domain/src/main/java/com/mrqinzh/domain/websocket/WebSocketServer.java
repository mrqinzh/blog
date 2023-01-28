package com.mrqinzh.domain.websocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.bean.WebSocketBean;
import com.mrqinzh.core.entity.User;
import com.mrqinzh.core.message.WebSocketMessage;
import com.mrqinzh.domain.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/api/websocket/{userId}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * Q: 无法 autowired ?
     * A: WebSocket是多对象的，使用的spring却是单例模式。这两者刚好冲突。
     * Autowired 注解注入对象是在启动的时候就把对象注入，而不是在使用A对象时才把A需要的B对象注入到A中。
     * 而WebSocket在刚刚有说到，有连接时才实例化对象，而且有多个连接就有多个对象。
     * 由此得知，UserService根本就没有注入到WebSocket当中。
     */
    private static UserService userService;
    private static ObjectMapper objectMapper;
    @Autowired
    public void setUserService(UserService userService) {
        WebSocketServer.userService = userService;
    }
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        WebSocketServer.objectMapper = objectMapper;
    }

    /**
     * 统计在线人数
     */
    private static int onlineCount = 0;
    /**
     * 存放所有在线的客户端
     */
    private static final Map<Integer, WebSocketServer> clients = new ConcurrentHashMap<>();
    private Session session;
    private Integer userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        this.userId = userId;
        if (clients.containsKey(userId)) {
            clients.remove(userId);
            clients.put(userId, this);
            //加入set中
        } else {
            clients.put(userId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        sendConnectMessage();
    }

    private void sendConnectMessage() {
        User user = userService.getById(userId);
        String msg = "欢迎" + user.getName() + "来到管理后台。";
        sendToClient(new WebSocketMessage(new WebSocketBean(false, msg), userId));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (clients.containsKey(userId)) {
            clients.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        logger.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息:" + userId + ", 报文: " + message);
        // 可以群发消息
        // 消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                // 解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                // 追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                Integer toUserId = jsonObject.getInteger("toUserId");
                // 传送给对应toUserId用户的websocket
                if (toUserId != null && clients.containsKey(toUserId)) {
                    clients.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    logger.error("请求的userId: " + toUserId + " 不在该服务器上");
                    // 否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) {
        logger.info("发送消息至{}，内容：{}", userId, message);
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("send message {} error !", message);
        }
    }

    /**
     * 发送自定义消息,批量发
     */
    public static void sendToClient(WebSocketMessage webSocketMessage) {
        List<Integer> receiveIds = webSocketMessage.getReceiveIds();
        if (CollectionUtil.isEmpty(receiveIds)) {
            logger.warn("发送webSocket消息失败。无接收人。");
        }
        for (Integer receiveId : receiveIds) {
            if (!clients.containsKey(receiveId)) {
                logger.info("用户 " + receiveId + " ,不在线！");
                return;
            }
            WebSocketServer socketServer = clients.get(receiveId);
            // todo 改逻辑
            try {
                String message = objectMapper.writeValueAsString(webSocketMessage.getWebSocketBean());
                socketServer.sendMessage(message);
                logger.info("发送消息到:"+ receiveId +"，内容为:"+message);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void sendToClient(String content, Integer receiveId) {
        if (!clients.containsKey(receiveId)) {
            logger.info("用户 " + receiveId + " ,不在线！");
            return;
        }
        WebSocketServer socketServer = clients.get(receiveId);
        socketServer.sendMessage(content);
        logger.info("发送消息到:"+ receiveId +"，内容:"+content);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
