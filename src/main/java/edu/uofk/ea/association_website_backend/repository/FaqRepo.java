package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.faq.FaqModel;
import edu.uofk.ea.association_website_backend.model.faq.FaqSeeResponse;
import edu.uofk.ea.association_website_backend.model.faq.FaqTranslationModel;
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

    public FaqTranslationModel findTById(int id, Language lang){
        TypedQuery<FaqTranslationModel> query = em.createQuery("FROM FaqTranslationModel f Where f.lang = :lang AND f.faqId = :id", FaqTranslationModel.class);
        query.setParameter("lang", lang);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    // Get all FAQs in the selected language to show on the FAQ component
    public List<FaqTranslationModel> getAllTWithLang(Language lang){
        TypedQuery<FaqTranslationModel> query = em.createQuery("FROM FaqTranslationModel f Where f.lang = :lang", FaqTranslationModel.class);
        query.setParameter("lang", lang);

        return query.getResultList();
    }

    @Transactional
    public void updateTranslation(FaqTranslationModel faq){
        em.merge(faq);
    }

    public void deleteTranslations(int id) {
        Query query = em.createQuery("DELETE FROM FaqTranslationModel ft WHERE ft.faqId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<FaqTranslationModel> getAllT() {
        return em.createQuery("FROM FaqTranslationModel f", FaqTranslationModel.class).getResultList();
    }
    public List<FaqTranslationModel> getAllTWithId(int id) {
        TypedQuery<FaqTranslationModel> query = em.createQuery("""
                FROM FaqTranslationModel f
                WHERE f.faqId = :id
                AND f.lang != :lang
                """, FaqTranslationModel.class);
        query.setParameter("id", id);
        query.setParameter("lang", Language.en);
        return query.getResultList();
    }

    @Transactional
    public int save(FaqModel faq){
        em.persist(faq);
        em.flush();
        em.refresh(faq);
        return faq.getId();
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
            deleteTranslations(id);
            em.remove(faq);
        }
    }

    // Get only needed data from table in english to show on the Admin Dashboard
    public List<FaqSeeResponse> seeAll() {
        TypedQuery<FaqSeeResponse> query = em.createQuery("""
                        SELECT new FaqSeeResponse(ft.faqId, ft.title, ft.body)
                        FROM FaqTranslationModel ft
                        WHERE ft.lang = :lang
                        """, FaqSeeResponse.class);
        query.setParameter("lang", Language.en);
        return query.getResultList();
    }

    public FaqSeeResponse seeOne(int id){
        TypedQuery<FaqSeeResponse> query = em.createQuery("""
                SELECT new FaqSeeResponse(ft.faqId, ft.title, ft.body)
                FROM FaqTranslationModel ft
                WHERE ft.lang = :lang
                AND ft.faqId = :id
                """, FaqSeeResponse.class);
        query.setParameter("lang", Language.en);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
