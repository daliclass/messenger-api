package com.opendebate.messenger.discussion.message;

import com.opendebate.messenger.Config;
import com.opendebate.messenger.MessengerApplication;
import com.opendebate.messenger.discussion.message.domain.Message;
import com.opendebate.messenger.discussion.message.domain.Side;
import org.flywaydb.core.Flyway;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MessengerApplication.class, Config.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageStoreTest {

    @Autowired
    Flyway flyway;

    @Autowired
    EntityManager entityManager;

    @Test
    public void A_createMessages() {
        List<Message> messages = messageSet();

        MessageStore messageStore = new MessageStore(entityManager);
        List<Message> createdMessages = messages
                .stream()
                .map(message -> messageStore.create(message))
                .collect(Collectors.toList());

        assertThat(createdMessages).containsAll(messages);
    }

    @Test
    public void B_retrieveMessageFromTheDatabaseUsingId() {
        Message expectedMessage = createMessage(1, 1);
        MessageStore messageStore = new MessageStore(entityManager);
        Message message = messageStore.get(1);
        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    public void C_getAllMessages() {
        List<Message> expectedMessageSet = messageSet();
        MessageStore messageStore = new MessageStore(entityManager);
        List<Message> messages = messageStore.getAll();
        assertThat(messages).isEqualTo(expectedMessageSet);
    }

    @Test
    public void D_getMessagesForADiscussion() {
        List<Message> expectedMessages = messageSet()
                .stream()
                .filter(message -> message.getDiscussionId() == 1)
                .collect(Collectors.toList());

        MessageStore messageStore = new MessageStore(entityManager);
        List<Message> actualMessages = messageStore.getAll(1);
        assertThat(actualMessages).containsAll(expectedMessages);
    }

    @Test
    public void E_deleteMessageById() {
        Message messageToDelete = createMessage(1,1);
        MessageStore messageStore = new MessageStore(entityManager);
        messageStore.delete(messageToDelete.getId());
        Message deletedMessage = messageStore.get(1);
        assertThat(deletedMessage).isEqualTo(null);
    }

    @Test
    public void F_updateMessage() {
        Message messageToUpdate = createMessage(2,3);
        MessageStore messageStore = new MessageStore(entityManager);
        Message updatedMessage = messageStore.update(2, messageToUpdate);
        assertThat(updatedMessage).isEqualTo(messageToUpdate);
    }

    public List<Message> messageSet() {
        List<Message> messages = new ArrayList();
        messages.add(createMessage(1,1));
        messages.add(createMessage(2,1));
        messages.add(createMessage(3,1));
        messages.add(createMessage(4,2));
        messages.add(createMessage(5,2));
        messages.add(createMessage(6,1));
        return messages;
    }

    public Message createMessage(Integer id, Integer discussionId) {
        Message message = new Message();
        message.setId(id);
        message.setMessage("message_1");
        message.setSide(Side.FOR);
        message.setDiscussionId(discussionId);
        return message;
    }
}
