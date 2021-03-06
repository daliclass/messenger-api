package com.opendebate.messenger.persistence.persistence.discussion;

import com.opendebate.messenger.Config;
import com.opendebate.messenger.MessengerApplication;
import org.flywaydb.core.Flyway;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MessengerApplication.class, Config.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscussionStoreTest {

    @Autowired
    Flyway flyway;

    @Autowired
    EntityManager entityManager;

    public MutableDiscussion createDiscussion(Integer id) {
        MutableDiscussion mutableDiscussion = new MutableDiscussion();
        mutableDiscussion.setTopic("the best topic");
        mutableDiscussion.setId(id);
        return mutableDiscussion;
    }

    @Test
    public void A_whenProvidedADiscussionThenAddItToDatabase() {
        DiscussionStore discussionStore = new DiscussionStore(entityManager);
        discussionStore.create(createDiscussion(1));
        discussionStore.create(createDiscussion(2));
    }

    @Test
    public void B_whenSelectingADiscussionThenGetTheDiscussion() {
        DiscussionStore discussionStore = new DiscussionStore(entityManager);
        MutableDiscussion mutableDiscussion = createDiscussion(2);
        assertThat(discussionStore.get(mutableDiscussion.getId())).isEqualTo(mutableDiscussion);
    }

    @Test
    public void C_whenSelectingAllDiscussionThenGetTwoDiscussion() {
        DiscussionStore discussionStore = new DiscussionStore(entityManager);
        assertThat(discussionStore.getAll())
                .contains(createDiscussion(1), createDiscussion(2))
                .hasSize(2);
    }

    @Test
    public void D_whenRemovingDiscussionsThenRemoveBothDiscussions() {
        DiscussionStore discussionStore = new DiscussionStore(entityManager);
        discussionStore.delete(1);
        assertThat(discussionStore.getAll()).hasSize(1);
        discussionStore.delete(2);
        assertThat(discussionStore.getAll()).hasSize(0);
    }
}
