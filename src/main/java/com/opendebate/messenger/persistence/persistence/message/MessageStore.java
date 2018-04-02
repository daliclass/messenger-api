package com.opendebate.messenger.persistence.persistence.message;

import com.opendebate.messenger.persistence.persistence.message.domain.MutableMessage;
import com.opendebate.messenger.persistence.PersistentStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@AllArgsConstructor
@Repository
public class MessageStore  implements PersistentStore<MutableMessage> {

    private final EntityManager entityManager;

    public MutableMessage create(MutableMessage mutableMessage) {
        entityManager.getTransaction().begin();
        entityManager.persist(mutableMessage);
        entityManager.getTransaction().commit();
        return mutableMessage;
    }

    public MutableMessage get(Integer id) {
        MutableMessage mutableMessage = entityManager.find(MutableMessage.class, id);
        return mutableMessage;
    }

    public List<MutableMessage> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MutableMessage> messageQuery = criteriaBuilder.createQuery(MutableMessage.class);
        Root<MutableMessage> messageRoot = messageQuery.from(MutableMessage.class);

        CriteriaQuery<MutableMessage> queryWithWhere = messageQuery.select(messageRoot);
        TypedQuery<MutableMessage> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getResultList();

    }

    public List<MutableMessage> getAll(Integer discussionId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MutableMessage> messageQuery = criteriaBuilder.createQuery(MutableMessage.class);
        Root<MutableMessage> messageRoot = messageQuery.from(MutableMessage.class);

        CriteriaQuery<MutableMessage> queryWithWhere = messageQuery
                .select(messageRoot)
                .where(
                        criteriaBuilder.equal(messageRoot.get("discussionId"), discussionId)
                );
        TypedQuery<MutableMessage> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getResultList();

    }

    public MutableMessage update(Integer id, MutableMessage mutableMessageWithUpdates) {
        MutableMessage mutableMessage = entityManager.find(MutableMessage.class, id);
        entityManager.getTransaction().begin();
        mutableMessage.setSide(mutableMessageWithUpdates.getSide());
        mutableMessage.setMessage(mutableMessageWithUpdates.getMessage());
        mutableMessage.setDiscussionId(mutableMessageWithUpdates.getDiscussionId());
        entityManager.getTransaction().commit();
        return mutableMessage;
    }

    public void delete(Integer id) {
        MutableMessage mutableMessage = entityManager.find(MutableMessage.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(mutableMessage);
        entityManager.getTransaction().commit();
    }
}
