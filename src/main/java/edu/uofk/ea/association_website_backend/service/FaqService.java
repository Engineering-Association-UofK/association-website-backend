package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.model.faq.FaqDashboardResponse;
import edu.uofk.ea.association_website_backend.model.faq.FaqGetModel;
import edu.uofk.ea.association_website_backend.model.faq.FaqModel;
import edu.uofk.ea.association_website_backend.model.faq.FaqTranslationModel;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.repository.FaqRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    public List<FaqTranslationModel> getAllWithLang(Language lang) {
        return repo.getAllTranslations(lang);
    }

    public List<FaqTranslationModel> getAll() {
        return repo.getAllTranslationsWithout();
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

    public List<FaqDashboardResponse> dashboard(Language lang){
        List<FaqTranslationModel> allTranslations = getAll();

        Map<Integer, List<FaqTranslationModel>> grouped = allTranslations.stream()
                .collect(Collectors.groupingBy(FaqTranslationModel::getFaqId));

        List<FaqDashboardResponse> response = new ArrayList<>();

        for (Map.Entry<Integer, List<FaqTranslationModel>> entry : grouped.entrySet()) {
            int faqId = entry.getKey();
            List<FaqTranslationModel> translations = entry.getValue();

            String title = translations.stream()
                    .filter(t -> t.getLang() == lang)
                    .map(FaqTranslationModel::getTitle)
                    .findFirst()
                    .orElse("---");

            List<Language> availableLangs = translations.stream().map(FaqTranslationModel::getLang).toList();

            response.add(new FaqDashboardResponse(faqId, title, availableLangs));
        }
        return response;
    }
}
