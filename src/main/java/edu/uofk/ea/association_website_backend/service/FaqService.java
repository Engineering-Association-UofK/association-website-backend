package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.FaqModel;
import edu.uofk.ea.association_website_backend.model.FaqTranslationModel;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.repository.FaqRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {

    private FaqRepo repo;

    @Autowired
    public FaqService(FaqRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(FaqTranslationModel faqT) {
        if (faqT.getFaqId() == 0) {
            repo.save(new FaqModel());
            faqT.setFaqId(repo.getAll().getLast().getId());
        }
        repo.saveTranslation(faqT);
    }

    public FaqTranslationModel findById(int id, Language lang) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + id);
        }
        return repo.findTranslationById(id, lang);
    }

    public List<FaqTranslationModel> getAll(Language lang) {
        return repo.getAllTranslations(lang);
    }

    @Transactional
    public void update(FaqTranslationModel faqT) {
        if (repo.findById(faqT.getFaqId()) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + faqT.getId());
        }
        repo.updateTranslation(faqT);
    }

    @Transactional
    public void delete(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("FAQ not found with ID:" + id);
        }
        repo.deleteTranslations(id);
        repo.delete(id);
    }
}
