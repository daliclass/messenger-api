package com.opendebate.messenger.discussion.message;

import com.opendebate.messenger.discussion.message.domain.Message;
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
public class MessageStore  implements PersistentStore<Message> {

    private final EntityManager entityManager;

    public Message create(Message message) {
        entityManager.getTransaction().begin();
        entityManager.persist(message);
        entityManager.getTransaction().commit();
        return message;
    }

    public Message get(Integer id) {
        Message message = entityManager.find(Message.class, id);
        return message;
    }

    public List<Message> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> messageQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> messageRoot = messageQuery.from(Message.class);

        CriteriaQuery<Message> queryWithWhere = messageQuery.select(messageRoot);
        TypedQuery<Message> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getResultList();

    }

    public List<Message> getAll(Integer discussionId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> messageQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> messageRoot = messageQuery.from(Message.class);

        CriteriaQuery<Message> queryWithWhere = messageQuery
                .select(messageRoot)
                .where(
                        criteriaBuilder.equal(messageRoot.get("discussionId"), discussionId)
                );
        TypedQuery<Message> allQuery = entityManager.createQuery(queryWithWhere);
        return allQuery.getResultList();

    }

    public Message update(Integer id, Message messageWithUpdates) {
        Message message = entityManager.find(Message.class, id);
        entityManager.getTransaction().begin();
        message.setSide(messageWithUpdates.getSide());
        message.setMessage(messageWithUpdates.getMessage());
        message.setDiscussionId(messageWithUpdates.getDiscussionId());
        entityManager.getTransaction().commit();
        return message;
    }

    public void delete(Integer id) {
        Message message = entityManager.find(Message.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(message);
        entityManager.getTransaction().commit();
    }
}
