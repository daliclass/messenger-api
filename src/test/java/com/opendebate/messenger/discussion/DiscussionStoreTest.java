package com.opendebate.messenger.discussion;

import com.opendebate.messenger.Config;
import com.opendebate.messenger.MessengerApplication;
import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MessengerApplication.class, Config.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscussionStoreTest {

    @Autowired
    Flyway flyway;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Discussion createDiscussion(Integer id) {
        Discussion discussion = new Discussion();
        discussion.setTopic("the best topic");
        discussion.setId(id);
        return discussion;
    }

    @Test
    public void A_whenProvidedADiscussionThenAddItToDatabase() {
        DiscussionStore discussionStore = new DiscussionStore(entityManagerFactory);
        discussionStore.createDiscussion(createDiscussion(1));
        discussionStore.createDiscussion(createDiscussion(2));
    }

    @Test
    public void B_whenSelectingADiscussionThenGetTheDiscussion() {
        DiscussionStore discussionStore = new DiscussionStore(entityManagerFactory);
        Discussion discussion = createDiscussion(2);
        assertThat(discussionStore.getDiscussion(discussion.getId())).isEqualTo(discussion);
    }

    @Test
    public void C_whenSelectingAllDiscussionThenGetTwoDiscussion() {
        DiscussionStore discussionStore = new DiscussionStore(entityManagerFactory);
        assertThat(discussionStore.getDiscussions())
                .contains(createDiscussion(1), createDiscussion(2))
                .hasSize(2);
    }

    @Test
    public void D_whenRemovingDiscussionsThenRemoveBothDiscussions() {
        DiscussionStore discussionStore = new DiscussionStore(entityManagerFactory);
        discussionStore.removeDiscussion(1);
        assertThat(discussionStore.getDiscussions()).hasSize(1);
        discussionStore.removeDiscussion(2);
        assertThat(discussionStore.getDiscussions()).hasSize(0);
    }
}
