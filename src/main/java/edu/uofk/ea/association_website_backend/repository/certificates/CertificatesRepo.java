package edu.uofk.ea.association_website_backend.repository.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertificateModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificatesRepo {

    private final EntityManager em;

    @Autowired
    public CertificatesRepo(EntityManager em) {
        this.em = em;
    }

    public CertificateModel FindById(int id){
        return em.find(CertificateModel.class, id);
    }

    public CertificateModel FindByHash(String hash){
        TypedQuery<CertificateModel> query = em.createQuery("SELECT c FROM CertificateModel c WHERE c.certHash = :hash", CertificateModel.class);
        query.setParameter("hash", hash);
        List<CertificateModel> list = query.getResultList();
        if (list.isEmpty()) return null;
        return list.getFirst();
    }

    @Transactional
    public void Save(CertificateModel model){
        em.persist(model);
    }

    @Transactional
    public void Update(CertificateModel model){
        em.merge(model);
    }

}
