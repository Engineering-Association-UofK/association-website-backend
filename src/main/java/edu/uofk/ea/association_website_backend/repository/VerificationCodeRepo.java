package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.VerificationCodeModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class VerificationCodeRepo {

    private EntityManager em;

    @Autowired
    public VerificationCodeRepo(EntityManager em) { this.em = em; }

    public VerificationCodeModel findByAdminId(int adminId){
        return em.createQuery("FROM VerificationCodeModel WHERE adminId = :adminId", VerificationCodeModel.class).setParameter("adminId", adminId).getSingleResult();
    }

    public VerificationCodeModel findById(int id) {
        return em.find(VerificationCodeModel.class, id);
    }

    public void save(VerificationCodeModel code){
        em.persist(code);
    }

    public void delete(int id){
        em.remove(findById(id));
    }

    public void deleteExpiredCodes(Instant cutoff) {
        em.createQuery("DELETE FROM VerificationCodeModel v WHERE v.createdAt < :cutoff")
                .setParameter("cutoff", cutoff)
                .executeUpdate();
    }
}
