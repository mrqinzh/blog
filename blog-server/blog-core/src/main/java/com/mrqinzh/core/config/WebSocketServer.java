package com.mrqinzh.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/api/websocket/{userId}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 统计在线人数
     */
    private static int onlineCount = 0;

    /**
     * 存放所有在线的客户端
     */
    private static Map<Integer, WebSocketServer> clients = new ConcurrentHashMap<>();

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
        logger.info("用户连接: " + userId + " ,当前在线人数为: " + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            logger.error("用户: " + userId + " ,网络异常!!!!!!");
        }

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
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") Integer userId) throws IOException {
        logger.info("发送消息到:"+userId+"，报文:"+message);
        if (userId != null && clients.containsKey(userId)) {
            clients.get(userId).sendMessage(message);
        } else {
            logger.error("用户 " + userId + " ,不在线！");
        }
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
