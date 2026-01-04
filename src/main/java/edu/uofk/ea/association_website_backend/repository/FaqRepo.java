package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.FaqModel;
import edu.uofk.ea.association_website_backend.model.FaqTranslationModel;
import edu.uofk.ea.association_website_backend.model.Language;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
    public void saveTranslation(FaqTranslationModel faq){
        em.persist(faq);
    }

    public FaqTranslationModel findTranslationById(int id, Language lang){
        TypedQuery<FaqTranslationModel> query = em.createQuery("FROM FaqTranslationModel f Where f.lang = :lang AND f.faqId = :id", FaqTranslationModel.class);
        query.setParameter("lang", lang);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<FaqTranslationModel> getAllTranslations(Language lang){
        TypedQuery<FaqTranslationModel> query = em.createQuery("FROM FaqTranslationModel f Where f.lang = :lang", FaqTranslationModel.class);
        query.setParameter("lang", lang);

        return query.getResultList();
    }

    @Transactional
    public void updateTranslation(FaqTranslationModel faq){
        Query query = em.createQuery("UPDATE FaqTranslationModel ft SET ft.title = :title, ft.body = :body WHERE ft.faqId = :id AND ft.lang = :lang");
        query.setParameter("title", faq.getTitle());
        query.setParameter("body", faq.getBody());
        query.setParameter("id", faq.getFaqId());
        query.setParameter("lang", faq.getLang());
        query.executeUpdate();
    }

    public void deleteTranslations(int id) {
        Query query = em.createQuery("DELETE FROM FaqTranslationModel ft WHERE ft.faqId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
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
