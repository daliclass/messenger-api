package com.opendebate.messenger.discussion;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscussionController {

    private final DiscussionStore discussionStore;

    public DiscussionController(DiscussionStore discussionStore) {
        this.discussionStore = discussionStore;
    }

    @PostMapping(value = "discussions", produces="application/json", consumes="application/json")
    public Discussion createDiscussion(@RequestBody Discussion discussion) {
        return discussionStore.create(discussion);
    }

    @GetMapping(value = "discussions", produces = "application/json")
    public List<Discussion> getDiscussions() {
        return discussionStore.getAll();
    }

    @PutMapping(value = "discussions/{id}", consumes = "application/json")
    public Discussion updateDiscussion(@PathVariable(value = "id") Integer id, @RequestBody Discussion updateDiscussion) {
        return discussionStore.update(id, updateDiscussion);
    }

    @GetMapping(value = "discussions/{id}", produces = "application/json")
    public Discussion getDiscussion(@PathVariable(value="id") Integer id) {
        return discussionStore.get(id);
    }
}
