package com.opendebate.messenger.discussion;


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
        Discussion inputDiscussion = new Discussion();
        Discussion outputDiscussion = new Discussion();
        when(discussionStore.createDiscussion(inputDiscussion)).thenReturn(outputDiscussion);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.createDiscussion(inputDiscussion)).isEqualTo(outputDiscussion);
    }

    @Test
    public void whenRequestingAListOfDisscussionsThenGetAListOfDiscussions() {
        List<Discussion> discussions = new ArrayList();
        discussions.add(new Discussion());
        discussions.add(new Discussion());

        when(discussionStore.getDiscussions()).thenReturn(discussions);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.getDiscussions()).isEqualTo(discussions);
    }

    @Test
    public void whenAmendingADiscusionThenUpdateADiscusion() {
        Discussion discussionUpdate = new Discussion();
        Discussion discussionUpdated = new Discussion();
        discussionUpdate.setTopic("foo");

        when(discussionStore.updateDiscussion(1, discussionUpdate)).thenReturn(discussionUpdated);

        DiscussionController discussionController = new DiscussionController(discussionStore);
        assertThat(discussionController.updateDiscussion(1, discussionUpdate)).isEqualTo(discussionUpdated);
    }
}
