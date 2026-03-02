package edu.uofk.ea.association_website_backend.repository.event;

import edu.uofk.ea.association_website_backend.model.event.EventModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EventRepo {

    private final EntityManager em;

    @Autowired
    public EventRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(EventModel event) {
        em.persist(event);
        em.flush();
        em.refresh(event);
        return event.getId();
    }

    public EventModel findById(int id) {
        return em.find(EventModel.class, id);
    }

    public List<EventModel> findAll() {
        return em.createQuery("SELECT e FROM EventModel e", EventModel.class).getResultList();
    }

    @Transactional
    public void update(EventModel event) {
        em.merge(event);
    }

    @Transactional
    public void delete(int id) {
        em.remove(findById(id));
    }
}
