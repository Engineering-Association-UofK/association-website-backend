package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.gallery.GalleryDeletedModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GalleryDeletedRepo {

    private final EntityManager em;

    @Autowired
    public GalleryDeletedRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(GalleryDeletedModel model) {
        em.persist(model);
    }

    public List<GalleryDeletedModel> getAll() {
        return em.createQuery("FROM GalleryDeletedModel", GalleryDeletedModel.class).getResultList();
    }

    public GalleryDeletedModel findById(int id) {
        return em.find(GalleryDeletedModel.class, id);
    }

    @Transactional
    public void delete(int id) {
        GalleryDeletedModel model = findById(id);
        if (model != null) {
            em.remove(model);
        }
    }
}
