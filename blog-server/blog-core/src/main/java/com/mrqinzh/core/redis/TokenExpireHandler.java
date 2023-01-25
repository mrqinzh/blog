package com.mrqinzh.core.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.message.GlobalMessageProducer;
import com.mrqinzh.core.message.WebSocketMessage;
import com.mrqinzh.core.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenExpireHandler implements RedisKeyExpiredHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GlobalMessageProducer producer;

    @Override
    public void handle(String value) throws Exception {
        // todo 提醒登录过期
        AuthenticatedToken token = objectMapper.readValue(value, AuthenticatedToken.class);
        producer.produce(new WebSocketMessage(AppStatus.TOKEN_EXPIRED.getMsg(), token.getPrincipal().getId(), 1));
    }

    @Override
    public boolean support(String value) {
        return value.startsWith(SecurityProperties.TOKEN_CACHE_PREFIX);
    }
}
