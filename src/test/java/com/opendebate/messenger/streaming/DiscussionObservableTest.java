package com.opendebate.messenger.streaming;

import com.opendebate.messenger.common.Discussion;
import com.opendebate.messenger.persistence.persistence.discussion.DiscussionStore;
import com.opendebate.messenger.persistence.persistence.discussion.MutableDiscussion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DiscussionObservableTest {

    @Mock
    DiscussionStore discussionStore;

    @Mock
    Consumer<Discussion> discussionConsumer;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void whenADiscussionChangesThenNotifyObservers() {
        MutableDiscussion mutableDiscussion = createMutableDiscussion(1, "topic one");

        MutableDiscussion mutableDiscussionWithChange = createMutableDiscussion(1, "topic two");
        Discussion discussionWithChange = mutableDiscussionWithChange.convertToDiscussion();

        when(discussionStore.get(1)).thenReturn(mutableDiscussion, mutableDiscussion, mutableDiscussionWithChange);

        DiscussionObservable discussionObservable = new DiscussionObservable(discussionStore);
        discussionObservable.subscribe(discussionConsumer);
        discussionObservable.watchDiscussion(1);
        discussionObservable.poll();
        discussionObservable.poll();
        discussionObservable.poll();
        ArgumentCaptor<Discussion> argument = ArgumentCaptor.forClass(Discussion.class);
        verify(discussionConsumer, times(1)).accept(discussionWithChange);
    }

    @Test
    public void whenARemovingAIdBeforeReceivingAUpdateThenDoNotReceiveChanges() {
        MutableDiscussion mutableDiscussion = createMutableDiscussion(1, "topic one");

        MutableDiscussion mutableDiscussionWithChange = createMutableDiscussion(1, "topic two");

        when(discussionStore.get(1)).thenReturn(mutableDiscussion, mutableDiscussionWithChange);

        DiscussionObservable discussionObservable = new DiscussionObservable(discussionStore);
        discussionObservable.subscribe(discussionConsumer);
        discussionObservable.watchDiscussion(1);
        discussionObservable.poll();
        discussionObservable.unwatchDiscussion(1);
        discussionObservable.poll();
        ArgumentCaptor<Discussion> argument = ArgumentCaptor.forClass(Discussion.class);
        verify(discussionConsumer, times(0)).accept(any());
    }

    @Test
    public void whenARemovingASubscriberBeforeReceivingAUpdateThenDoNotReceiveChanges() {
        MutableDiscussion mutableDiscussion = createMutableDiscussion(1, "topic one");

        MutableDiscussion mutableDiscussionWithChange = createMutableDiscussion(1, "topic two");

        when(discussionStore.get(1)).thenReturn(mutableDiscussion, mutableDiscussionWithChange);

        DiscussionObservable discussionObservable = new DiscussionObservable(discussionStore);
        discussionObservable.subscribe(discussionConsumer);
        discussionObservable.watchDiscussion(1);
        discussionObservable.poll();
        discussionObservable.unsubscribe(discussionConsumer);
        discussionObservable.poll();
        ArgumentCaptor<Discussion> argument = ArgumentCaptor.forClass(Discussion.class);
        verify(discussionConsumer, times(0)).accept(any());
    }

    private MutableDiscussion createMutableDiscussion(Integer id, String topic) {
        MutableDiscussion mutableDiscussion = new MutableDiscussion();
        mutableDiscussion.setId(id);
        mutableDiscussion.setTopic(topic);
        return mutableDiscussion;
    }
}
