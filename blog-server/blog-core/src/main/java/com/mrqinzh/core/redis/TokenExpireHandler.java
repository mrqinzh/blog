package com.mrqinzh.core.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.common.model.bean.WebSocketBean;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.core.auth.token.AuthenticatedToken;
import com.mrqinzh.core.message.GlobalMessageProducer;
import com.mrqinzh.core.message.WebSocketMessage;
import com.mrqinzh.core.security.SecurityProperties;
import com.mrqinzh.core.security.SecurityService;
import com.mrqinzh.core.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenExpireHandler implements RedisKeyExpiredHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GlobalMessageProducer producer;
    @Autowired
    private SecurityService securityService;

    @Override
    public void handle(String value) throws Exception {
        // todo 后续将token转为jwt方式保存
        // 告诉前端过期
        String username = value.substring(SecurityProperties.TOKEN_CACHE_PREFIX.length() +
                UUID.randomUUID().toString().replaceAll("-", "").length());
        SecurityUser user = securityService.loadSecurityUserFromDb(username);
        if (user == null) return;
        WebSocketBean webSocketBean = buildMsgToClient();
        producer.produce(new WebSocketMessage(webSocketBean, user.getId()));

        // 。。。。。。
    }

    @Override
    public boolean support(String value) {
        return value.startsWith(SecurityProperties.TOKEN_CACHE_PREFIX);
    }

    private WebSocketBean buildMsgToClient() throws JsonProcessingException {
        return new WebSocketBean(true, objectMapper.writeValueAsString(new Resp(AppStatus.TOKEN_EXPIRED)));
    }
}
