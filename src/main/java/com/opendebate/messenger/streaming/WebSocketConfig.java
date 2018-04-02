package com.opendebate.messenger.streaming;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MessagingWebSocketHandler(), "/di");
    }

    @Bean
    @Qualifier("webSocketSessions")
    public Set<WebSocketSession> webSocketSessions() {
        return new ConcurrentSkipListSet();
    }
}