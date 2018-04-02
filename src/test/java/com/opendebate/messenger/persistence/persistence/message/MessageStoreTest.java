package com.opendebate.messenger.persistence.persistence.message;

import com.opendebate.messenger.Config;
import com.opendebate.messenger.MessengerApplication;
import com.opendebate.messenger.persistence.persistence.message.domain.MutableMessage;
import com.opendebate.messenger.common.Side;
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
        List<MutableMessage> mutableMessages = messageSet();

        MessageStore messageStore = new MessageStore(entityManager);
        List<MutableMessage> createdMutableMessages = mutableMessages
                .stream()
                .map(message -> messageStore.create(message))
                .collect(Collectors.toList());

        assertThat(createdMutableMessages).containsAll(mutableMessages);
    }

    @Test
    public void B_retrieveMessageFromTheDatabaseUsingId() {
        MutableMessage expectedMutableMessage = createMessage(1, 1);
        MessageStore messageStore = new MessageStore(entityManager);
        MutableMessage mutableMessage = messageStore.get(1);
        assertThat(mutableMessage).isEqualTo(expectedMutableMessage);
    }

    @Test
    public void C_getAllMessages() {
        List<MutableMessage> expectedMutableMessageSet = messageSet();
        MessageStore messageStore = new MessageStore(entityManager);
        List<MutableMessage> mutableMessages = messageStore.getAll();
        assertThat(mutableMessages).isEqualTo(expectedMutableMessageSet);
    }

    @Test
    public void D_getMessagesForADiscussion() {
        List<MutableMessage> expectedMutableMessages = messageSet()
                .stream()
                .filter(message -> message.getDiscussionId() == 1)
                .collect(Collectors.toList());

        MessageStore messageStore = new MessageStore(entityManager);
        List<MutableMessage> actualMutableMessages = messageStore.getAll(1);
        assertThat(actualMutableMessages).containsAll(expectedMutableMessages);
    }

    @Test
    public void E_deleteMessageById() {
        MutableMessage mutableMessageToDelete = createMessage(1,1);
        MessageStore messageStore = new MessageStore(entityManager);
        messageStore.delete(mutableMessageToDelete.getId());
        MutableMessage deletedMutableMessage = messageStore.get(1);
        assertThat(deletedMutableMessage).isEqualTo(null);
    }

    @Test
    public void F_updateMessage() {
        MutableMessage mutableMessageToUpdate = createMessage(2,3);
        MessageStore messageStore = new MessageStore(entityManager);
        MutableMessage updatedMutableMessage = messageStore.update(2, mutableMessageToUpdate);
        assertThat(updatedMutableMessage).isEqualTo(mutableMessageToUpdate);
    }

    public List<MutableMessage> messageSet() {
        List<MutableMessage> mutableMessages = new ArrayList();
        mutableMessages.add(createMessage(1,1));
        mutableMessages.add(createMessage(2,1));
        mutableMessages.add(createMessage(3,1));
        mutableMessages.add(createMessage(4,2));
        mutableMessages.add(createMessage(5,2));
        mutableMessages.add(createMessage(6,1));
        return mutableMessages;
    }

    public MutableMessage createMessage(Integer id, Integer discussionId) {
        MutableMessage mutableMessage = new MutableMessage();
        mutableMessage.setId(id);
        mutableMessage.setMessage("message_1");
        mutableMessage.setSide(Side.FOR);
        mutableMessage.setDiscussionId(discussionId);
        return mutableMessage;
    }
}
