package edu.uofk.ea.association_website_backend.service.certificates;

import edu.uofk.ea.association_website_backend.model.certificates.documents.DocumentModel;
import edu.uofk.ea.association_website_backend.repository.certificates.DocumentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentsService {

    private final DocumentRepo repo;

    @Autowired
    public DocumentsService(DocumentRepo repo) {
        this.repo = repo;
    }

    public DocumentModel GetById(int id){
        return repo.FindById(id);
    }

    public DocumentModel GetByHash(String hash){
        return repo.FindByHash(hash);
    }

    @Transactional
    public void Save(DocumentModel model){
        if (repo.FindByHash(model.getDocumentHash()) != null) repo.Update(model);
        else repo.Save(model);
    }
}
