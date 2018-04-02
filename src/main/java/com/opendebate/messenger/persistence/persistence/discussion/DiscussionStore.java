package com.opendebate.messenger.persistence.persistence.discussion;

import com.opendebate.messenger.persistence.PersistentStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class DiscussionStore implements PersistentStore<MutableDiscussion> {

    private final EntityManager entityManager;

    public MutableDiscussion create(MutableDiscussion mutableDiscussion) {
        entityManager.getTransaction().begin();
        entityManager.persist(mutableDiscussion);
        entityManager.getTransaction().commit();
        return mutableDiscussion;
    }

    public MutableDiscussion get(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<MutableDiscussion> discussionQuery = criteriaBuilder.createQuery(MutableDiscussion.class);
        Root<MutableDiscussion> discussion = discussionQuery.from(MutableDiscussion.class);

        CriteriaQuery<MutableDiscussion> queryWithWhere = discussionQuery
                .select(discussion)
                .where(
                        criteriaBuilder.equal(discussion.get("id"), id)
                );

        TypedQuery<MutableDiscussion> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getSingleResult();
    }

    public List<MutableDiscussion> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MutableDiscussion> criteriaQuery = criteriaBuilder.createQuery(MutableDiscussion.class);
        CriteriaQuery<MutableDiscussion> all = criteriaQuery.select(
                criteriaQuery.from(MutableDiscussion.class)
        );
        TypedQuery<MutableDiscussion> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public MutableDiscussion update(Integer id, MutableDiscussion updateMutableDiscussion) {
        MutableDiscussion mutableDiscussion = entityManager.find(MutableDiscussion.class, id);

        entityManager.getTransaction().begin();
        mutableDiscussion.setTopic(updateMutableDiscussion.getTopic());
        entityManager.getTransaction().commit();

        return mutableDiscussion;
    }


    public void delete(Integer id) {
        MutableDiscussion mutableDiscussion = entityManager.find(MutableDiscussion.class, id);

        entityManager.getTransaction().begin();
        entityManager.remove(mutableDiscussion);
        entityManager.getTransaction().commit();
    }
}
