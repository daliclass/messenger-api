package com.opendebate.messenger.persistence.persistence.discussion;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DiscussionControllerTest {

    @Mock
    DiscussionStore discussionStore;

    @Before
    public void beforeEach() {
        initMocks(this);
    }

    @Test
    public void whenCreatingANewDiscussionThenAddADiscussionToTheStore() {
        MutableDiscussion inputMutableDiscussion = new MutableDiscussion();
        MutableDiscussion outputMutableDiscussion = new MutableDiscussion();
        when(discussionStore.create(inputMutableDiscussion)).thenReturn(outputMutableDiscussion);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.createDiscussion(inputMutableDiscussion)).isEqualTo(outputMutableDiscussion);
    }

    @Test
    public void whenRequestingAListOfDisscussionsThenGetAListOfDiscussions() {
        List<MutableDiscussion> mutableDiscussions = new ArrayList();
        mutableDiscussions.add(new MutableDiscussion());
        mutableDiscussions.add(new MutableDiscussion());

        when(discussionStore.getAll()).thenReturn(mutableDiscussions);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.getDiscussions()).isEqualTo(mutableDiscussions);
    }

    @Test
    public void whenAmendingADiscusionThenUpdateADiscusion() {
        MutableDiscussion mutableDiscussionUpdate = new MutableDiscussion();
        MutableDiscussion mutableDiscussionUpdated = new MutableDiscussion();
        mutableDiscussionUpdate.setTopic("foo");

        when(discussionStore.update(1, mutableDiscussionUpdate)).thenReturn(mutableDiscussionUpdated);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.updateDiscussion(1, mutableDiscussionUpdate)).isEqualTo(mutableDiscussionUpdated);
    }
}
