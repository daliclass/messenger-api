package com.opendebate.messenger.discussion.message;

import com.opendebate.messenger.discussion.message.domain.Message;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageStore messageStore;

    @PostMapping("/message/{messageId}")
    public Message createMessage(Message messageToPersist) {
        return messageStore.create(messageToPersist);
    }

    @GetMapping("/discussion/{discussionId}/messages")
    public List<Message> getMessagesForADiscussion(@PathVariable("discussionId") Integer discussionId) {
        return messageStore.getAll(discussionId);
    }

    @GetMapping("/message/{messageId}")
    public Message getMessage(@PathVariable("messageId") Integer messageId) {
        return messageStore.get(messageId);
    }

    @PutMapping("/message/{messageId}")
    public Message updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        return messageStore.update(messageId, message);
    }

    @DeleteMapping("/message/{messageId}")
    public void deleteMessage(Integer messageId) {
        messageStore.delete(messageId);
    }
}
