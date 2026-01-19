package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericAlreadyExistsException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.GenericNotFoundException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnexpectedErrorException;
import edu.uofk.ea.association_website_backend.model.faq.*;
import edu.uofk.ea.association_website_backend.model.Language;
import edu.uofk.ea.association_website_backend.repository.FaqRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaqService {

    private FaqRepo repo;

    @Autowired
    public FaqService(FaqRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void save(FaqRequest request) {
        if (request.getId() != null && repo.findById(request.getId()) != null) {
            throw new GenericAlreadyExistsException("FAQ already exists with ID");
        }
        // Get the FAQ ID back from the DB to add it into the translations list
        int id = repo.save(new FaqModel());
        for (FaqTranslationModel t : request.getTranslations()) {
            repo.saveTranslation(new FaqTranslationModel(id, t.getTitle(), t.getBody(), t.getLang()));
        }
    }

    public List<FaqTranslationModel> getAllWithLang(Language lang) {
        return repo.getAllTWithLang(lang);
    }

    @Transactional
    public void update(FaqRequest request) {
        if (request.getId() == null || repo.findById(request.getId()) == null) {
            throw new GenericNotFoundException("FAQ not found with ID, Make sure you added a valid field.");
        }
        int id = request.getId();

        repo.deleteTranslations(id);
        for (FaqTranslationModel t : request.getTranslations()) {
            repo.saveTranslation(new FaqTranslationModel(id, t.getTitle(), t.getBody(), t.getLang()));
        }
    }

    @Transactional
    public void delete(int id) {
        if (repo.findById(id) == null) {
            throw new GenericNotFoundException("FAQ not found with ID, Make sure you added a valid field.");
        }
        repo.delete(id);
    }

    public FaqDashboardResponse seeOne(int id){
        if (repo.findById(id) == null) throw new GenericNotFoundException("FAQ not found with ID, Make sure you added a valid field.");
        FaqSeeResponse response = repo.seeOne(id);
        if (response == null) {
            List<FaqTranslationModel> translations = repo.getAllTWithId(id);
            if (translations.isEmpty()) throw new UnexpectedErrorException("Hanging Empty translations at ID: " + id + ".");
            return new FaqDashboardResponse(id, null, null, translations);
        }
        List<FaqTranslationModel> translations = repo.getAllTWithId(id);
        return new FaqDashboardResponse(response.getFaqId(), response.getTitle(), response.getBody(), translations);
    }

    /// This method takes all the entries in the FAQ translations table,
    /// Groups them using the faqId in a map so they all belong to the same FAQ.
    ///
    /// Then it takes all the ones in the primary language (english currently)
    /// and if one doesn't exist replaces them with '---' to send a complete list of
    /// all the available FAQ IDs in the database.
    public List<FaqSeeResponse> seeAll(){
        List<FaqTranslationModel> allTranslations = repo.getAllT();

        Map<Integer, List<FaqTranslationModel>> grouped = allTranslations.stream()
                .collect(Collectors.groupingBy(FaqTranslationModel::getFaqId));

        List<FaqSeeResponse> response = new ArrayList<>();

        for (Map.Entry<Integer, List<FaqTranslationModel>> entry : grouped.entrySet()) {
            int faqId = entry.getKey();
            List<FaqTranslationModel> translations = entry.getValue();

            String title = translations.stream()
                    .filter(t -> t.getLang() == Language.en)
                    .map(FaqTranslationModel::getTitle)
                    .findFirst()
                    .orElse("---");

            String body = translations.stream()
                    .filter(t -> t.getLang() == Language.en)
                    .map(FaqTranslationModel::getBody)
                    .findFirst()
                    .orElse("---");

            response.add(new FaqSeeResponse(faqId, title, body));
        }
        return response;

//        return repo.seeAll();
    }
}
