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
    public void whenCreatingANewMessageThenDeferToTheStore() {
        Message messageToPersist = createMessage();
        when(messageStore.create(messageToPersist)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        Message actualMessage = messageController.createMessage(messageToPersist);
        assertThat(actualMessage).isEqualTo(messageToPersist);
    }

    @Test
    public void whenMessagesAreRequestedForADiscussionThenLookForItInTheStore() {
        List<Message> messages = new ArrayList();
        when(messageStore.getAll(1)).thenReturn(messages);

        MessageController messageController = new MessageController(messageStore);
        List<Message> actualMessages = messageController.getMessagesForADiscussion(1);
        assertThat(actualMessages).containsAll(messages);
    }

    @Test
    public void whenGettingAMessageByIdThenLookForItInTheStore() {
        when(messageStore.get(1)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        Message message = messageController.getMessage(1);
        assertThat(message).isEqualTo(createMessage());
    }

    @Test
    public void whenUpdatingAMessageThenNotifyTheStore() {
        Message updatedMessage = createMessage();
        when(messageStore.update(1, updatedMessage)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        Message message = messageController.updateMessage(1, updatedMessage);
        assertThat(message).isEqualTo(createMessage());
    }

    @Test
    public void whenDeletingAMessageThenNotifyTheStore() {
        /* You might think this is strange to have a test that asserts nothing, I agree! It has marginal value
         * however the test lived before the deleteMessage method so it served its purpose.
         */

        MessageController messageController = new MessageController(messageStore);
        messageController.deleteMessage(1);
    }

    public Message createMessage() {
        Message message = new Message();
        message.setDiscussionId(1);
        message.setId(1);
        message.setMessage("message");
        message.setSide(Side.FOR);
        return message;
    }
}
