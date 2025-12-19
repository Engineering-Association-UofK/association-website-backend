package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.FaqModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FaqRepo {

    private final EntityManager em;

    @Autowired
    public FaqRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(FaqModel faq){
        em.persist(faq);
    }

    public FaqModel findById(int id){
        return em.find(FaqModel.class, id);
    }

    public List<FaqModel> getAll(){
        return em.createQuery("FROM FaqModel", FaqModel.class).getResultList();
    }

    @Transactional
    public void update(FaqModel faq){
        em.merge(faq);
    }

    @Transactional
    public void delete(int id){
        FaqModel faq = findById(id);
        if (faq != null) {
            em.remove(faq);
        }
    }
}
