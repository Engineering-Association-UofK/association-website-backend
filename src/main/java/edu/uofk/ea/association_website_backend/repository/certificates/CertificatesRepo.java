package edu.uofk.ea.association_website_backend.repository.certificates;

import edu.uofk.ea.association_website_backend.model.Language;
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

    public void Save(CertificateModel model){
        em.persist(model);
    }

    public void saveAll(List<CertificateModel> models) {
        for (CertificateModel model : models) {
            em.persist(model);
        }
    }

    public void Update(CertificateModel model){
        em.merge(model);
    }

    public CertificateModel FindByStudentIdAndEventId(int studentId, int eventId) {
        TypedQuery<CertificateModel> query = em.createQuery("SELECT c FROM CertificateModel c WHERE c.studentId = :studentId AND c.eventId = :eventId", CertificateModel.class);
        query.setParameter("studentId", studentId);
        query.setParameter("eventId", eventId);
        List<CertificateModel> list = query.getResultList();
        if (list.isEmpty()) return null;
        return list.getFirst();
    }

    public CertificateModel FindByStudentIdAndEventIdAndLanguage(int studentId, int eventId, Language lang) {
        TypedQuery<CertificateModel> query = em.createQuery("SELECT c FROM CertificateModel c WHERE c.studentId = :studentId AND c.eventId = :eventId AND c.lang = :lang", CertificateModel.class);
        query.setParameter("studentId", studentId);
        query.setParameter("eventId", eventId);
        query.setParameter("lang", lang);
        List<CertificateModel> list = query.getResultList();
        if (list.isEmpty()) return null;
        return list.getFirst();
    }
}
