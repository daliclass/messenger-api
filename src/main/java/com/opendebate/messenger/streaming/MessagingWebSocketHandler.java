package com.opendebate.messenger.streaming;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
* Responsible for handling the addition and removal of WebSocketSession's based on client interaction
* */
@Component
public class MessagingWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> webSocketSessions;

    public MessagingWebSocketHandler(ConcurrentHashMap<String, WebSocketSession> webSocketSessions) {
        this.webSocketSessions = webSocketSessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession newSession) throws Exception {
        webSocketSessions.put(newSession.getId(), newSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession closedSession, CloseStatus status) throws Exception {
        if (!webSocketSessions.containsKey(closedSession.getId())) {
            return;
        }

        webSocketSessions.remove(closedSession.getId());
    }
}
