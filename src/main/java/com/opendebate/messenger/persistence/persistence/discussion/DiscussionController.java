package com.opendebate.messenger.persistence.persistence.discussion;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscussionController {

    private final DiscussionStore discussionStore;

    public DiscussionController(DiscussionStore discussionStore) {
        this.discussionStore = discussionStore;
    }

    @PostMapping(value = "discussions", produces="application/json", consumes="application/json")
    public MutableDiscussion createDiscussion(@RequestBody MutableDiscussion mutableDiscussion) {
        return discussionStore.create(mutableDiscussion);
    }

    @GetMapping(value = "discussions", produces = "application/json")
    public List<MutableDiscussion> getDiscussions() {
        return discussionStore.getAll();
    }

    @PutMapping(value = "discussions/{id}", consumes = "application/json")
    public MutableDiscussion updateDiscussion(@PathVariable(value = "id") Integer id, @RequestBody MutableDiscussion updateMutableDiscussion) {
        return discussionStore.update(id, updateMutableDiscussion);
    }

    @GetMapping(value = "discussions/{id}", produces = "application/json")
    public MutableDiscussion getDiscussion(@PathVariable(value="id") Integer id) {
        return discussionStore.get(id);
    }
}
