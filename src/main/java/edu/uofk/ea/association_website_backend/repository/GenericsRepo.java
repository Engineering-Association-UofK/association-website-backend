package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.model.generics.GenericModel;
import edu.uofk.ea.association_website_backend.model.generics.GenericTranslationModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenericsRepo {

    private final EntityManager em;

    @Autowired
    public GenericsRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(GenericModel gen){
        em.persist(gen);
    }

    @Transactional
    public void saveTranslation(GenericTranslationModel gen){
        em.persist(gen);
    }

    public GenericTranslationModel findTById(int id, Language lang){
        TypedQuery<GenericTranslationModel> query = em.createQuery("FROM GenericTranslationModel g Where g.lang = :lang AND g.genId = :id", GenericTranslationModel.class);
        query.setParameter("lang", lang);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void updateTranslation(GenericTranslationModel gen){
        em.merge(gen);
    }

    public void deleteTranslations(int id) {
        Query query = em.createQuery("DELETE FROM GenericTranslationModel gt WHERE gt.genId = :id");
        GenericTranslationModel model = new GenericTranslationModel();
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public GenericModel findById(int id){
        return em.find(GenericModel.class, id);
    }

    public GenericModel findByKeyword(String keyword) {
        TypedQuery<GenericModel> query = em.createQuery("FROM GenericModel g Where g.keyword = :keyword", GenericModel.class);
        query.setParameter("keyword", keyword);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void delete(int id){
        GenericModel gen = findById(id);
        if (gen != null) {
            deleteTranslations(id);
            em.remove(gen);
        }
    }

    public List<GenericModel> findByKeywords(List<String> keywords) {
        TypedQuery<GenericModel> query = em.createQuery("FROM GenericModel g Where g.keyword IN :keywords", GenericModel.class);
        query.setParameter("keywords", keywords);
        return query.getResultList();
    }
}
