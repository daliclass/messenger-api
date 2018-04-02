package com.opendebate.messenger.streaming;

import com.opendebate.messenger.common.Discussion;
import com.opendebate.messenger.persistence.persistence.discussion.DiscussionStore;
import com.opendebate.messenger.persistence.persistence.discussion.MutableDiscussion;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class DiscussionObservable {

    private final DiscussionStore discussionStore;
    private final List<Consumer<Discussion>> discussionConsumers;
    private final ArrayList<Integer> discussionIds;
    private final ConcurrentHashMap<Integer, MutableDiscussion> cachedDiscussions;

    public DiscussionObservable(DiscussionStore discussionStore) {
        this.discussionStore = discussionStore;
        this.discussionConsumers = new ArrayList();
        this.discussionIds = new ArrayList();
        this.cachedDiscussions = new ConcurrentHashMap();
    }

    @Scheduled(fixedDelay = 5000)
    public void poll() {
        for(Integer id: discussionIds) {
            MutableDiscussion mutableDiscussion = this.discussionStore.get(id);
            if (shouldSendDiscussion(id, mutableDiscussion)) {
                Discussion discussion = mutableDiscussion.convertToDiscussion();
                discussionConsumers.forEach(discussionConsumer -> discussionConsumer.accept(discussion));
            }

            if (shouldPopulateCache(id, mutableDiscussion)) {
                this.cachedDiscussions.put(id, mutableDiscussion);
            }
        }
    }

    private boolean shouldPopulateCache(Integer id, MutableDiscussion mutableDiscussion) {
        if(!cachedDiscussions.containsKey(id) || !cachedDiscussions.get(id).equals(mutableDiscussion)) {
            return true;
        }
        return false;
    }

    private boolean shouldSendDiscussion(Integer id, MutableDiscussion discussion) {
        if(!cachedDiscussions.containsKey(id) || cachedDiscussions.get(id).equals(discussion)) {
            return false;
        }
        return true;
    }

    public void subscribe(Consumer<Discussion> discussionConsumer) {
        discussionConsumers.add(discussionConsumer);
    }

    public void watchDiscussion(Integer discussionId) {
        discussionIds.add(discussionId);
    }

    public void unwatchDiscussion(Integer discussionId) {
        discussionIds.remove(discussionId);
    }

    public void unsubscribe(Consumer<Discussion> discussionConsumer) {
        discussionConsumers.remove(discussionConsumer);
    }
}
