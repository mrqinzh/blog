package com.mrqinzh.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrqinzh.core.message.NullMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class BeanConfig {

    @Bean
    public NullMessageListener nullMessageListener() {
        return new NullMessageListener();
    }

    /**
     * 开启WebSocket支持
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
