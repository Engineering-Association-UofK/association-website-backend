package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.admin.OldAdminModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OldAdminRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public OldAdminRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(OldAdminModel admin) {
        em.persist(admin);
    }

}
