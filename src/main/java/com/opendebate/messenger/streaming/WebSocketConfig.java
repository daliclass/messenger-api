package com.opendebate.messenger.streaming;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messagingWebSocketHandler(webSocketSessions()), "/di");
    }

    @Bean
    public MessagingWebSocketHandler messagingWebSocketHandler(ConcurrentHashMap<String, WebSocketSession> webSocketSessions) {
        return new MessagingWebSocketHandler(webSocketSessions);
    }

    @Bean
    public ConcurrentHashMap<String, WebSocketSession> webSocketSessions() {
        return new ConcurrentHashMap();
    }
}