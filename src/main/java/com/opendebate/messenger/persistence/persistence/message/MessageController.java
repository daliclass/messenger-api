package com.opendebate.messenger.persistence.persistence.message;

import com.opendebate.messenger.persistence.persistence.message.domain.MutableMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageStore messageStore;

    @PostMapping("/message/{messageId}")
    public MutableMessage createMessage(MutableMessage mutableMessageToPersist) {
        return messageStore.create(mutableMessageToPersist);
    }

    @GetMapping("/discussion/{discussionId}/messages")
    public List<MutableMessage> getMessagesForADiscussion(@PathVariable("discussionId") Integer discussionId) {
        return messageStore.getAll(discussionId);
    }

    @GetMapping("/message/{messageId}")
    public MutableMessage getMessage(@PathVariable("messageId") Integer messageId) {
        return messageStore.get(messageId);
    }

    @PutMapping("/mutableMessage/{messageId}")
    public MutableMessage updateMessage(@PathVariable Integer messageId, @RequestBody MutableMessage mutableMessage) {
        return messageStore.update(messageId, mutableMessage);
    }

    @DeleteMapping("/message/{messageId}")
    public void deleteMessage(Integer messageId) {
        messageStore.delete(messageId);
    }
}
