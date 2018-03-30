package com.opendebate.messenger.discussion;

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
public class DiscussionStore implements PersistentStore<Discussion> {

    private final EntityManager entityManager;

    public Discussion create(Discussion discussion) {
        entityManager.getTransaction().begin();
        entityManager.persist(discussion);
        entityManager.getTransaction().commit();
        return discussion;
    }

    public Discussion get(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Discussion> discussionQuery = criteriaBuilder.createQuery(Discussion.class);
        Root<Discussion> discussion = discussionQuery.from(Discussion.class);

        CriteriaQuery<Discussion> queryWithWhere = discussionQuery
                .select(discussion)
                .where(
                        criteriaBuilder.equal(discussion.get("id"), id)
                );

        TypedQuery<Discussion> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getSingleResult();
    }

    public List<Discussion> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Discussion> criteriaQuery = criteriaBuilder.createQuery(Discussion.class);
        CriteriaQuery<Discussion> all = criteriaQuery.select(
                criteriaQuery.from(Discussion.class)
        );
        TypedQuery<Discussion> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public Discussion update(Integer id, Discussion updateDiscussion) {
        Discussion discussion = entityManager.find(Discussion.class, id);

        entityManager.getTransaction().begin();
        discussion.setTopic(updateDiscussion.getTopic());
        entityManager.getTransaction().commit();

        return discussion;
    }


    public void delete(Integer id) {
        Discussion discussion = entityManager.find(Discussion.class, id);

        entityManager.getTransaction().begin();
        entityManager.remove(discussion);
        entityManager.getTransaction().commit();
    }
}
