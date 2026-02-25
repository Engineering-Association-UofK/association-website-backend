package edu.uofk.ea.association_website_backend.repository;

import edu.uofk.ea.association_website_backend.model.storage.StorageModel;
import edu.uofk.ea.association_website_backend.model.storage.StoreType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StorageRepo {

    private final EntityManager em;

    @Autowired
    public StorageRepo(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void store(StorageModel model) {
        em.persist(model);
    }

    /**
     * Methods used to fetch all items.
     * @return A list of all items in the database.
     */
    public List<StorageModel> getAll() {
        return em.createQuery("SELECT s FROM StorageModel s", StorageModel.class).getResultList();
    }

    public List<StorageModel> getByLatest() {
        return em.createQuery("SELECT s FROM StorageModel s ORDER BY s.createdAt DESC", StorageModel.class).getResultList();
    }

    public List<StorageModel> getByLatestUpdated() {
        return em.createQuery("SELECT s FROM StorageModel s ORDER BY s.updatedAt DESC", StorageModel.class).getResultList();
    }

    public List<StorageModel> getByReferenceNum(int referenceNum) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.referenceNum = :referenceNum", StorageModel.class)
                .setParameter("referenceNum", referenceNum)
                .getResultList();
    }

    public List<StorageModel> getByList(List<Integer> ids) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.id IN :ids", StorageModel.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    /**
     * Methods used to fetch items by type.
     * @param type The type of item to fetch. Can be either image, document, or video.
     * @return A list of all items of the specified type.
     */
    public List<StorageModel> getByType(StoreType type) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.type = :type", StorageModel.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<StorageModel> getByLatestAndType(StoreType type) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.type = :type ORDER BY s.createdAt DESC", StorageModel.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<StorageModel> getByLatestUpdatedAndType(StoreType type) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.type = :type ORDER BY s.updatedAt DESC", StorageModel.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<StorageModel> getByReferenceNumAndType(int referenceNum, StoreType type) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.referenceNum = :referenceNum AND s.type = :type", StorageModel.class)
                .setParameter("referenceNum", referenceNum)
                .setParameter("type", type)
                .getResultList();
    }

    /**
     * Methods used to fetch items by identifiers.
     * @return The item with the specified identifier.
     */
    public StorageModel findById(int id) {
        return em.find(StorageModel.class, id);
    }

    public StorageModel findByPublicId(String publicId) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.publicId = :publicId", StorageModel.class)
                .setParameter("publicId", publicId)
                .getResultList().stream().findFirst().orElse(null);
    }

    public StorageModel findByUrl(String url) {
        return em.createQuery("SELECT s FROM StorageModel s WHERE s.url = :url", StorageModel.class)
                .setParameter("url", url)
                .getResultList().stream().findFirst().orElse(null);
    }

    /**
     * Methods used to update items state.
     */
    @Transactional
    public void incrementReferenceNum(StorageModel model) {
        model.setReferenceNum(model.getReferenceNum() + 1);
        em.merge(model);
    }

    @Transactional
    public boolean decrementReferenceNum(StorageModel model) {
        if (model.getReferenceNum() <= 0) return false;
        model.setReferenceNum(model.getReferenceNum() - 1);
        em.merge(model);
        return true;
    }

    @Transactional
    public void update(StorageModel model) {
        em.merge(model);
    }

    @Transactional
    public void delete(StorageModel model) {
        em.remove(model);
    }
}
