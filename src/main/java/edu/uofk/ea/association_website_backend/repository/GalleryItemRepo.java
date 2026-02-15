package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemModel;
import edu.uofk.ea.association_website_backend.model.gallery.GalleryItemType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GalleryItemRepo {
    
    private EntityManager em;

    @Autowired
    public GalleryItemRepo(EntityManager em) { this.em = em; }

    @Transactional
    public void save(GalleryItemModel item){
        em.persist(item);
    }

    public GalleryItemModel findById(int id){
        return em.find(GalleryItemModel.class, id);
    }

    public List<GalleryItemModel> getAll(){
        return em.createQuery("FROM GalleryItemModel", GalleryItemModel.class).getResultList();
    }

    public List<GalleryItemModel> findByType(GalleryItemType type){
        return em.createQuery("FROM GalleryItemModel WHERE type = :type", GalleryItemModel.class)
                .setParameter("type", type).getResultList();
    }

    public GalleryItemModel findByKeyword(String keyword){
        return em.createQuery("FROM GalleryItemModel WHERE keyword = :keyword", GalleryItemModel.class)
                .setParameter("keyword", keyword).getResultList().stream().findFirst().orElse(null);
    }

    public void Update(GalleryItemModel item){
        em.merge(item);
    }

    @Transactional
    public void delete(int id){
        em.remove(findById(id));
    }
}
