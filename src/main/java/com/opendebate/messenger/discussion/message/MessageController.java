package com.opendebate.messenger.discussion.message;

import com.opendebate.messenger.discussion.message.domain.Message;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageStore messageStore;

    @GetMapping("/discussion/{discussionId}/messages")
    public List<Message> getAllMessages(@PathVariable("discussionId") Integer discussionId) {
        return messageStore.getAll(discussionId);
    }
}
