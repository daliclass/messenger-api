package com.opendebate.messenger.streaming;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessagingWebSocketHandlerTest {

    @Mock
    WebSocketSession webSocketSessionOne;

    @Mock
    WebSocketSession webSocketSessionTwo;

    @Mock
    WebSocketSession webSocketSessionThree;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenProvidedAWebSocketSessionThenCacheIt() throws Exception {
        when(webSocketSessionOne.getId()).thenReturn("1");
        ConcurrentHashMap<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap();
        MessagingWebSocketHandler messagingWebSocketHandler = new MessagingWebSocketHandler(webSocketSessions);
        messagingWebSocketHandler.afterConnectionEstablished(webSocketSessionOne);
        assertThat(webSocketSessions).containsValues(webSocketSessionOne);
    }

    @Test
    public void whenRemovingAWebSocketSessionThenRemoveTheCorrectWebSocketSession() throws Exception {
        when(webSocketSessionOne.getId()).thenReturn("1");
        when(webSocketSessionTwo.getId()).thenReturn("2");
        when(webSocketSessionThree.getId()).thenReturn("3");

        ConcurrentHashMap<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap();
        MessagingWebSocketHandler messagingWebSocketHandler = new MessagingWebSocketHandler(webSocketSessions);

        messagingWebSocketHandler.afterConnectionEstablished(webSocketSessionOne);
        messagingWebSocketHandler.afterConnectionEstablished(webSocketSessionTwo);
        messagingWebSocketHandler.afterConnectionEstablished(webSocketSessionThree);

        assertThat(webSocketSessions).containsExactly(
                new ConcurrentHashMap.SimpleEntry("1", webSocketSessionOne),
                new ConcurrentHashMap.SimpleEntry("2", webSocketSessionTwo),
                new ConcurrentHashMap.SimpleEntry("3", webSocketSessionThree)
        );

        messagingWebSocketHandler.afterConnectionClosed(webSocketSessionTwo, CloseStatus.NO_STATUS_CODE);

        assertThat(webSocketSessions).containsExactly(
                new ConcurrentHashMap.SimpleEntry("1", webSocketSessionOne),
                new ConcurrentHashMap.SimpleEntry("3", webSocketSessionThree)
        );
    }

    @Test
    public void whenRemovingAWebSocketSessionWithAIdThatDoesNotExistThenDoNothing() throws Exception {
        when(webSocketSessionOne.getId()).thenReturn("1");
        when(webSocketSessionTwo.getId()).thenReturn("2");

        ConcurrentHashMap<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap();
        MessagingWebSocketHandler messagingWebSocketHandler = new MessagingWebSocketHandler(webSocketSessions);

        messagingWebSocketHandler.afterConnectionEstablished(webSocketSessionOne);

        assertThat(webSocketSessions).containsExactly(
                new ConcurrentHashMap.SimpleEntry("1", webSocketSessionOne)
        );

        messagingWebSocketHandler.afterConnectionClosed(webSocketSessionTwo, CloseStatus.NO_STATUS_CODE);

        assertThat(webSocketSessions).containsExactly(
                new ConcurrentHashMap.SimpleEntry("1", webSocketSessionOne)
        );
    }
}
