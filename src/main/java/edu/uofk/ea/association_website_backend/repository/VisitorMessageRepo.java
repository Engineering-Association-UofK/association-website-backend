package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.VisitorMessageModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VisitorMessageRepo {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(VisitorMessageModel message) {
        em.persist(message);
    }

    public List<VisitorMessageModel> getAll() {
        return em.createQuery("SELECT m FROM VisitorMessageModel m ORDER BY m.createdAt DESC", VisitorMessageModel.class).getResultList();
    }
}