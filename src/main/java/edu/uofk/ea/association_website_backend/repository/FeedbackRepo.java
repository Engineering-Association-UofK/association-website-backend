package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.feedback.FeedbackModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepo {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(FeedbackModel feedback) {
        em.persist(feedback);
    }
}