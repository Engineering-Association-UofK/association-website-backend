package edu.uofk.ea.association_website_backend.repository.event;

import edu.uofk.ea.association_website_backend.model.event.EventParticipationModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventParticipationRepo {

    private final EntityManager em;

    @Autowired
    public EventParticipationRepo(EntityManager em) {
        this.em = em;
    }

    public int save(EventParticipationModel event) {
        em.persist(event);
        em.flush();
        em.refresh(event);
        return event.getId();
    }

    public void saveAll(List<EventParticipationModel> events) {
        for (EventParticipationModel event : events) {
            em.persist(event);
        }
    }

    public EventParticipationModel findById(int id) {
        return em.find(EventParticipationModel.class, id);
    }

    public List<EventParticipationModel> getByEventId(int eventId) {
        return em.createQuery("SELECT ep FROM EventParticipationModel ep WHERE ep.eventId = :eventId", EventParticipationModel.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    public EventParticipationModel findByEventIdAndStudentIds(int eventId, int studentId) {
        return em.createQuery("SELECT ep FROM EventParticipationModel ep WHERE ep.eventId = :eventId AND ep.studentId = :studentId", EventParticipationModel.class)
                .setParameter("eventId", eventId)
                .setParameter("studentId", studentId)
                .getResultList().stream().findFirst().orElse(null);
    }

    public List<EventParticipationModel> findByEventIdAndStudentIds(int eventId, List<Integer> studentId) {
        return em.createQuery("SELECT ep FROM EventParticipationModel ep WHERE ep.eventId = :eventId AND ep.studentId IN :studentIds", EventParticipationModel.class)
                .setParameter("eventId", eventId)
                .setParameter("studentIds", studentId)
                .getResultList();
    }

    public void update(EventParticipationModel event) {
        em.merge(event);
    }

    public void delete(int id) {
        em.remove(findById(id));
    }
}
