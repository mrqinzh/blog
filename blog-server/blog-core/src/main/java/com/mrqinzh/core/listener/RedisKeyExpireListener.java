package com.mrqinzh.core.listener;

import com.mrqinzh.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * redis Key过期事件监听器
 */
@Component
public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisUtil redisUtil;

    public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("message ==> key " + message);
        String s = new String(pattern);
        System.out.println("partten " + s);
    }

}
