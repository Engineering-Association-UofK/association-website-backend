package edu.uofk.ea.association_website_backend.repository.activity;

import edu.uofk.ea.association_website_backend.model.activity.ActivityEventModel;
import edu.uofk.ea.association_website_backend.model.activity.ActivityType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ActivityEventRepo {

    private final EntityManager em;

    @Autowired
    public ActivityEventRepo(EntityManager em) {
        this.em = em;
    }

    public List<ActivityEventModel> getAll() {
        return em.createQuery("SELECT e FROM ActivityEventModel e", ActivityEventModel.class).getResultList();
    }

    public ActivityEventModel findById(int id) {
        return em.find(ActivityEventModel.class, id);
    }

    public List<ActivityEventModel> findByUser(int adminId) {
        return em.createQuery("SELECT e FROM ActivityEventModel e WHERE e.adminId = :adminId", ActivityEventModel.class)
                .setParameter("adminId", adminId)
                .getResultList();
    }

    public List<ActivityEventModel> findByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return em.createQuery("SELECT e FROM ActivityEventModel e " +
                        "WHERE e.createdAt >= :start " +
                        "AND e.createdAt < :end", ActivityEventModel.class)
                .setParameter("start", startTime)
                .setParameter("end", endTime)
                .getResultList();
    }

    public List<ActivityEventModel> findByType(ActivityType type) {
        return em.createQuery("SELECT e FROM ActivityEventModel e WHERE e.eventType = :type", ActivityEventModel.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<ActivityEventModel> findByUserAndDate(int UserId, LocalDateTime startTime, LocalDateTime endTime) {
        return em.createQuery("SELECT e FROM ActivityEventModel e " +
                        "WHERE e.adminId = :adminId " +
                        "AND e.createdAt >= :start " +
                        "AND e.createdAt < :end", ActivityEventModel.class)
                .setParameter("adminId", UserId)
                .setParameter("start", startTime)
                .setParameter("end", endTime)
                .getResultList();
    }

    public List<ActivityEventModel> findByUserAndType(int UserId, ActivityType type) {
        return em.createQuery("SELECT e FROM ActivityEventModel e WHERE e.adminId = :adminId AND e.eventType = :type", ActivityEventModel.class)
                .setParameter("adminId", UserId)
                .setParameter("type", type)
                .getResultList();
    }

    public List<ActivityEventModel> findByTypeAndDate(ActivityType type, LocalDateTime startTime, LocalDateTime endTime) {
        return em.createQuery("SELECT e FROM ActivityEventModel e WHERE e.eventType = :type AND e.createdAt >= :start AND e.createdAt < :end", ActivityEventModel.class)
                .setParameter("type", type)
                .setParameter("start", startTime)
                .setParameter("end", endTime)
                .getResultList();
    }

    public List<ActivityEventModel> findByUserAndDateAndType(int UserId, ActivityType type, LocalDateTime startTime, LocalDateTime endTime) {
        return em.createQuery("SELECT e FROM ActivityEventModel e " +
                        "WHERE e.adminId = :adminId " +
                        "AND e.eventType = :type " +
                        "AND e.createdAt >= :start " +
                        "AND e.createdAt < :end", ActivityEventModel.class)
                .setParameter("adminId", UserId)
                .setParameter("type", type)
                .setParameter("start", startTime)
                .setParameter("end", endTime)
                .getResultList();
    }

    public void save(ActivityEventModel event) {
        em.persist(event);
    }

}
