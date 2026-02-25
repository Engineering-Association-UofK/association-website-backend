package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.gallery.NewsModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NewsRepo {

    private final EntityManager em;

    @Autowired
    public NewsRepo(EntityManager em) {
        this.em = em;
    }

    public List<NewsModel> getAll() {
        return em.createQuery("FROM NewsModel", NewsModel.class).getResultList();
    }

    @Transactional
    public void save(NewsModel news){
        em.persist(news);
    }

    public NewsModel findByStorageId(int storageId) {
        return em.createQuery("FROM NewsModel WHERE storageId = :storageId", NewsModel.class)
                .setParameter("storageId", storageId)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void delete(int id){
        em.remove(findByStorageId(id));
    }
}
