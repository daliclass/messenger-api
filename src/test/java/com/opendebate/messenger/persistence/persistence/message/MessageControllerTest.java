package com.opendebate.messenger.persistence.persistence.message;

import com.opendebate.messenger.persistence.persistence.message.domain.MutableMessage;
import com.opendebate.messenger.common.Side;
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
        MutableMessage mutableMessageToPersist = createMessage();
        when(messageStore.create(mutableMessageToPersist)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        MutableMessage actualMutableMessage = messageController.createMessage(mutableMessageToPersist);
        assertThat(actualMutableMessage).isEqualTo(mutableMessageToPersist);
    }

    @Test
    public void whenMessagesAreRequestedForADiscussionThenLookForItInTheStore() {
        List<MutableMessage> mutableMessages = new ArrayList();
        when(messageStore.getAll(1)).thenReturn(mutableMessages);

        MessageController messageController = new MessageController(messageStore);
        List<MutableMessage> actualMutableMessages = messageController.getMessagesForADiscussion(1);
        assertThat(actualMutableMessages).containsAll(mutableMessages);
    }

    @Test
    public void whenGettingAMessageByIdThenLookForItInTheStore() {
        when(messageStore.get(1)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        MutableMessage mutableMessage = messageController.getMessage(1);
        assertThat(mutableMessage).isEqualTo(createMessage());
    }

    @Test
    public void whenUpdatingAMessageThenNotifyTheStore() {
        MutableMessage updatedMutableMessage = createMessage();
        when(messageStore.update(1, updatedMutableMessage)).thenReturn(createMessage());
        MessageController messageController = new MessageController(messageStore);
        MutableMessage mutableMessage = messageController.updateMessage(1, updatedMutableMessage);
        assertThat(mutableMessage).isEqualTo(createMessage());
    }

    @Test
    public void whenDeletingAMessageThenNotifyTheStore() {
        /* You might think this is strange to have a test that asserts nothing, I agree! It has marginal value
         * however the test lived before the deleteMessage method so it served its purpose.
         */

        MessageController messageController = new MessageController(messageStore);
        messageController.deleteMessage(1);
    }

    public MutableMessage createMessage() {
        MutableMessage mutableMessage = new MutableMessage();
        mutableMessage.setDiscussionId(1);
        mutableMessage.setId(1);
        mutableMessage.setMessage("mutableMessage");
        mutableMessage.setSide(Side.FOR);
        return mutableMessage;
    }
}
