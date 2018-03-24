package com.opendebate.messenger.discussion;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class DiscussionStore {

    private final EntityManagerFactory entityManagerFactory;

    public DiscussionStore(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Discussion createDiscussion(Discussion discussion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(discussion);
        entityManager.getTransaction().commit();
        entityManager.close();
        return discussion;
    }

    public List<Discussion> getDiscussions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Discussion> criteriaQuery = criteriaBuilder.createQuery(Discussion.class);
        CriteriaQuery<Discussion> all = criteriaQuery.select(
                criteriaQuery.from(Discussion.class)
        );
        TypedQuery<Discussion> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public Discussion updateDiscussion(Integer id, Discussion updateDiscussion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Discussion discussion = entityManager.find(Discussion.class, id);

        entityManager.getTransaction().begin();
        discussion.setTopic("Joe the Plumber");
        entityManager.getTransaction().commit();

        discussion.setTopic(updateDiscussion.getTopic());
        return discussion;
    }

    public Discussion getDiscussion(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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

    public void removeDiscussion(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Discussion discussion = entityManager.find(Discussion.class, id);

        entityManager.getTransaction().begin();
        entityManager.remove(discussion);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
