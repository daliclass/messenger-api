package com.opendebate.messenger.discussion.message;

import com.opendebate.messenger.discussion.message.domain.Message;
import com.opendebate.messenger.discussion.message.domain.Side;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessageControllerTest {

    @Mock
    MessageStore messageStore;

    @Before
    public void beforeEach() {
        initMocks(this);
    }

    @Test
    public void whenMessagesAreRequestedForADiscussionThenReturnTheMessages() {
        Message message = new Message();
        message.setDiscussionId(1);
        message.setId(1);
        message.setMessage("message");
        message.setSide(Side.FOR);
        List<Message> messages = new ArrayList();
        when(messageStore.getAll(1)).thenReturn(messages);

        MessageController messageController = new MessageController(messageStore);
        List<Message> actualMessages = messageController.getAllMessages(1);
        assertThat(actualMessages).containsAll(messages);
    }
}
