package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.EntityType;
import edu.uofk.ea.association_website_backend.model.storage.StorageReferenceModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StorageReferenceRepo {

    private final EntityManager em;

    @Autowired
    public StorageReferenceRepo(EntityManager em) {
        this.em = em;
    }

    public StorageReferenceModel findByEntity(EntityType entityType, int entityId) {
        try {
            return em.createQuery("SELECT r FROM StorageReferenceModel r WHERE r.entityType = :entityType AND r.entityId = :entityId", StorageReferenceModel.class)
                    .setParameter("entityType", entityType)
                    .setParameter("entityId", entityId)
                    .getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void save(StorageReferenceModel model) {
        em.persist(model);
    }

    @Transactional
    public void update(StorageReferenceModel model) {
        em.merge(model);
    }

    @Transactional
    public void delete(StorageReferenceModel model) {
        em.remove(model);
    }
}