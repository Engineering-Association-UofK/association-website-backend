package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.GalleryItemModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class GalleryItemRepo {
    
    private EntityManager em;

    @Autowired
    public GalleryItemRepo(EntityManager em) { this.em = em; }

    @Transactional
    public void save(GalleryItemModel item){
        item.setCreatedAt(Instant.now());
        em.persist(item);
    }

    public GalleryItemModel findById(int id){
        return em.find(GalleryItemModel.class, id);
    }

    public List<GalleryItemModel> getAll(){
        return em.createQuery("FROM GalleryItemModel", GalleryItemModel.class).getResultList();
    }

    @Transactional
    public void delete(int id){
        em.remove(findById(id));
    }
}
