package edu.uofk.ea.association_website_backend.repository.event;

import edu.uofk.ea.association_website_backend.model.event.EventComponentModel;
import edu.uofk.ea.association_website_backend.model.event.EventModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EventComponentRepo {

    private final EntityManager em;

    @Autowired
    public EventComponentRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(EventComponentModel event) {
        em.persist(event);
        em.flush();
        em.refresh(event);
        return event.getId();
    }

    public EventComponentModel findById(int id) {
        return em.find(EventComponentModel.class, id);
    }

    public List<EventComponentModel> getByEventId(int eventId){
        return em.createQuery("SELECT ec FROM EventComponentModel ec WHERE ec.eventId = :eventId", EventComponentModel.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    @Transactional
    public void update(EventComponentModel event) {
        em.merge(event);
    }

    @Transactional
    public void delete(int id) {
        em.remove(findById(id));
    }
}
