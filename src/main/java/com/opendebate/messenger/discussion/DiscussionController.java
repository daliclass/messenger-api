package com.opendebate.messenger.discussion;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RestController
public class DiscussionController {


    EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("jimbo");


    @PostMapping(value = "discussions", produces="application/json", consumes="application/json")
    public Discussion createDiscussion(@RequestBody Discussion discussion) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(discussion);
        entityManager.getTransaction().commit();
        entityManager.close();
        return discussion;
    }

    @GetMapping(value = "discussions", produces = "application/json")
    public List<Discussion> getDiscussions() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        CriteriaQuery<Discussion> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Discussion.class);
        CriteriaQuery<Discussion> all = criteriaQuery.select(
                criteriaQuery.from(Discussion.class)
        );
        TypedQuery<Discussion> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @PutMapping(value = "discussions/{id}", consumes = "application/json")
    public Discussion updateDiscussion(@PathVariable(value = "id") Integer id, @RequestBody Discussion updateDiscussion) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        Discussion discussion = entityManager.find(Discussion.class, id);

        entityManager.getTransaction().begin();
        discussion.setTopic("Joe the Plumber");
        entityManager.getTransaction().commit();

        discussion.setTopic(updateDiscussion.getTopic());
        return discussion;
    }

    @GetMapping(value = "discussions/{id}", produces = "application/json")
    public Discussion getDiscussion(@PathVariable(value="id") Integer id) {
        EntityManager entityManager = sessionFactory.createEntityManager();
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
}
