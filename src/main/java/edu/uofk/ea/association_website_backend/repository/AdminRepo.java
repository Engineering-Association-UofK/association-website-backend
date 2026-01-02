package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.AdminModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class AdminRepo {

    @PersistenceContext
    private EntityManager em;

//    @Autowired
//    public AdminRepo(EntityManager em) {
//        this.em = em;
//    }

    public AdminModel findByUsername(String username){
        try {
            TypedQuery<AdminModel> query = em.createQuery("SELECT a FROM AdminModel a WHERE a.name = :username", AdminModel.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
