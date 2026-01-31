package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertificateModel;
import edu.uofk.ea.association_website_backend.repository.certificates.CertificatesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificatesService {

    private final CertificatesRepo repo;

    @Autowired
    public CertificatesService(CertificatesRepo repo) {
        this.repo = repo;
    }

    public CertificateModel GetById(int id){
        return repo.FindById(id);
    }

    public CertificateModel GetByHash(String hash){
        return repo.FindByHash(hash);
    }

    @Transactional
    public void Save(CertificateModel model){
        if (repo.FindByHash(model.getCertHash()) != null) repo.Update(model);
        else repo.Save(model);
    }
}
