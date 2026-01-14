package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.admin.AdminStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
public class AdminRepo {

    @PersistenceContext
    private EntityManager em;

    public AdminModel findByUsername(String username){
        try {
            TypedQuery<AdminModel> query = em.createQuery("SELECT a FROM AdminModel a WHERE a.name = :username", AdminModel.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void save(AdminModel admin){
        em.persist(admin);
    }

    @Transactional
    public void update(AdminModel admin){
        em.merge(admin);
    }

    @Transactional
    public void delete(int id){
        AdminModel admin = findById(id);
        admin.setStatus(AdminStatus.deactivated);
        em.merge(admin);
    }

    public AdminModel findById(int id){
        return em.find(AdminModel.class, id);
    }
}
