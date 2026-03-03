package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.certificates.CertificateModel;
import edu.uofk.ea.association_website_backend.repository.certificates.CertificatesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificatesService {

    private final CertificatesRepo repo;

    @Autowired
    public CertificatesService(CertificatesRepo repo) {
        this.repo = repo;
    }

    public CertificateModel findById(int id){
        return repo.FindById(id);
    }

    public CertificateModel findByHash(String hash){
        return repo.FindByHash(hash);
    }

    @Transactional
    public void save(CertificateModel model){
        repo.Save(model);
    }

    @Transactional
    public void update(CertificateModel model) {
        repo.Update(model);
    }

    @Transactional
    public void saveAll(List<CertificateModel> models) {
        repo.saveAll(models);
    }
}
