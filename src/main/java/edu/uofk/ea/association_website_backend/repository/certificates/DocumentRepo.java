package edu.uofk.ea.association_website_backend.repository.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentRepo {

    private final EntityManager em;

    @Autowired
    public DocumentRepo(EntityManager em) {
        this.em = em;
    }

    public DocumentModel FindById(int id){
        return em.find(DocumentModel.class, id);
    }

    public DocumentModel FindByHash(String hash){
        TypedQuery<DocumentModel> query= em.createQuery("SELECT c FROM DocumentModel c WHERE c.documentHash = :hash", DocumentModel.class);
        query.setParameter("hash", hash);
        List<DocumentModel> models = query.getResultList();
        if (models.isEmpty()) return null;
        return models.getFirst();
    }

    @Transactional
    public void Save(DocumentModel model){
        em.persist(model);
    }

    @Transactional
    public void Update(DocumentModel model){
        em.merge(model);
    }
    
}
